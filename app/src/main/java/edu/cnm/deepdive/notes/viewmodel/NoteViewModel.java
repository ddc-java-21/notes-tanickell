package edu.cnm.deepdive.notes.viewmodel;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.notes.model.entity.Image;
import edu.cnm.deepdive.notes.model.entity.Note;
import edu.cnm.deepdive.notes.model.pojo.NoteWithImages;
import edu.cnm.deepdive.notes.service.NoteRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import kotlin.jvm.functions.Function1;

@HiltViewModel
public class NoteViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final Context context;
  private final NoteRepository repository;
  private final MutableLiveData<Long> noteId;
  private final LiveData<NoteWithImages> note;
  private final MutableLiveData<List<Image>> images;
  private final MutableLiveData<Uri> captureUri;
  private final MutableLiveData<Boolean> editing;
  private final MutableLiveData<Boolean> cameraPermission;
  private final MediatorLiveData<VisibilityFlags> visibilityFlags;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending; // composite disposable = zero or more disposables collected in one bucket

  private Uri pendingCaptureUri;
  private Instant noteModified;

  /** @noinspection DataFlowIssue*/
  @Inject
  NoteViewModel(@ApplicationContext Context context, NoteRepository repository) {

    this.context = context;
    this.repository = repository;
    noteId = new MutableLiveData<>();
    note = Transformations.switchMap(noteId, repository::get);
    images = new MutableLiveData<>(new ArrayList<>());
    note.observeForever((note) -> {
      List<Image> images = this.images.getValue(); // get a reference to the images, not a copy
      images.clear();
      images.addAll(note.getImages());
      this.images.setValue(images); // when we put the exact same object back into live data, we trigger the observer
    });
    captureUri = new MutableLiveData<>();
    editing = new MutableLiveData<>(false);
    cameraPermission = new MutableLiveData<>(false);
    visibilityFlags = new MediatorLiveData<>();
    visibilityFlags.addSource(editing, (editing) ->
        visibilityFlags.setValue(new VisibilityFlags(editing, cameraPermission.getValue())));
    visibilityFlags.addSource(cameraPermission, (permission) ->
        visibilityFlags.setValue(new VisibilityFlags(editing.getValue(), permission)));
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
  }

  public LiveData<Long> getNoteId() {
    return noteId;
  }

  public void setNoteId(long noteId) {
    this.noteId.setValue(noteId); // connect one piece of live data to another
  }

  public LiveData<List<NoteWithImages>> getNotes() {
    return repository.getAll();
  }

  public LiveData<NoteWithImages> getNote() {
    return note;
  }

  public LiveData<List<Image>> getImages() {
    return images;
  }

  public void addImage(Image image) {
    List<Image> images = this.images.getValue();
    //noinspection DataFlowIssue
    images.add(image); // since we put an empty arraylist inside, it is not null
    this.images.setValue(images);
  }

  public void removeImage(Image image) {
    List<Image> images = this.images.getValue();
    //noinspection DataFlowIssue
    images.remove(image); // since we put an empty arraylist inside, it is not null
    this.images.setValue(images);
  }

  public LiveData<Uri> getCaptureUri() {
    return captureUri;
  }

  public void setPendingCaptureUri(Uri pendingCaptureUri) {
    this.pendingCaptureUri = pendingCaptureUri;
  }

  public LiveData<Boolean> getEditing() {
    return editing;
  }

  public void setEditing(boolean editing) {
    this.editing.setValue(editing);
  }

  public LiveData<Boolean> getCameraPermission() {
    return cameraPermission;
  }

  public void setCameraPermission(boolean cameraPermission) {
    this.cameraPermission.setValue(cameraPermission);
  }

  public LiveData<VisibilityFlags> getVisibilityFlags() {
    return visibilityFlags;
  }

  public void confirmCapture(boolean success) {
    if (success) {
      captureUri.setValue(pendingCaptureUri);
      Image image = new Image();
      image.setUri(pendingCaptureUri);
      addImage(image);
    } else {
      captureUri.setValue(null);
    }
    pendingCaptureUri = null;
  }

  public void save(NoteWithImages note) {
    throwable.setValue(null);
    //noinspection DataFlowIssue
    Single.just(note)
        .doOnSuccess((n) -> n.getImages().clear())
        .doOnSuccess((n) -> n.getImages().addAll(images.getValue()))
        .flatMap(repository::save)
        .subscribe(
            (n) -> noteId.postValue(n.getId()),
            this::postThrowable,
            pending
        );
    repository
        .save(note)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            (n) -> noteId.setValue(n.getId()), // use postValue bc it works on any thread; setValue only works on UI thread, and data is currently in a background thread
            this::postThrowable,
            pending
        );
  }

  public void remove(Note note) {
    throwable.setValue(null);
    repository
        .remove(note)
        .subscribe(
            () -> {},
            this::postThrowable,
            pending
        );
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    pending.clear(); // anything that hasn't finished on reactivex chains, don't bother
    DefaultLifecycleObserver.super.onStop(owner);
  }

  @NonNull
  private LiveData<NoteWithImages> setupNoteWithImages() {
    LiveData<NoteWithImages> note = Transformations.switchMap(noteId, repository::get);
    note.observeForever((n) -> {
      if (n != null && !n.getModified().equals(noteModified)) {
        List<Image> images = this.images.getValue();
        images.clear();
        images.addAll(n.getImages());
        noteModified = n.getModified();
        this.images.setValue(images);
      }
    });
    return note;
  }

  private void postThrowable(Throwable throwable) {
    Log.e(NoteViewModel.class.getSimpleName(), throwable.getMessage(), throwable); // throwable itself puts the stack trace in
    this.throwable.postValue(throwable);
  }

  public record VisibilityFlags(boolean editing, boolean cameraPermission) {

  }

}
