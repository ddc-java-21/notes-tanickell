package edu.cnm.deepdive.notes.viewmodel;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.notes.model.entity.Note;
import edu.cnm.deepdive.notes.model.pojo.NoteWithImages;
import edu.cnm.deepdive.notes.service.NoteRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;
import kotlin.jvm.functions.Function1;

@HiltViewModel
public class NoteViewModel extends ViewModel implements DefaultLifecycleObserver {

  private final Context context;
  private final NoteRepository repository;
  private final MutableLiveData<Long> noteId;
  private final LiveData<NoteWithImages> note;
  private final MutableLiveData<Uri> captureUri;
  private final MutableLiveData<Boolean> editing;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending; // composite disposable = zero or more disposables collected in one bucket

  private Uri pendingCaptureUri;

  @Inject
  NoteViewModel(@ApplicationContext Context context, NoteRepository repository) {

    this.context = context;
    this.repository = repository;
    noteId = new MutableLiveData<>();
    note = Transformations.switchMap(noteId, repository::get);
    captureUri = new MutableLiveData<>();
    editing = new MutableLiveData<>(false);
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

  public void confirmCapture(boolean success) {
    captureUri.setValue(success ? pendingCaptureUri : null);
    pendingCaptureUri = null;
  }

  public void save(NoteWithImages note) {
    throwable.setValue(null);
    repository
        .save(note)
//        .ignoreElement()
        .subscribe(
            (n) -> {}, // remove n here if you uncomment two lines above
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

  private void postThrowable(Throwable throwable) {
    Log.e(NoteViewModel.class.getSimpleName(), throwable.getMessage(), throwable); // throwable itself puts the stack trace in
    this.throwable.postValue(throwable);
  }

}
