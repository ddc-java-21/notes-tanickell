package edu.cnm.deepdive.notes.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import dagger.hilt.android.qualifiers.ActivityContext;
import dagger.hilt.android.scopes.FragmentScoped;
import edu.cnm.deepdive.notes.databinding.ItemNoteBinding;
import edu.cnm.deepdive.notes.model.entity.Note;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@FragmentScoped
public class NoteAdapter extends Adapter<ViewHolder> {

  private final List<Note> notes;
  private final LayoutInflater inflater;
  private final DateTimeFormatter formatter;

  @Inject
  NoteAdapter(@ActivityContext Context context) {
    notes = new ArrayList<>();
    inflater = LayoutInflater.from(context);
    formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup container, int itemType) {
    // DONE: 6/16/25 Inflate the itemLayout and pass it to the Holder constructor, then return the
    //  new Holder.
    ItemNoteBinding binding = ItemNoteBinding.inflate(inflater, container, false);
    return new Holder(binding, formatter);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    // DONE: 6/16/25 Get the Note at the specified position; pass that note and the formatter (and
    //  the position) to the Holder.bind method.
    ((Holder) viewHolder).bind(position, notes.get(position));
  }

  @Override
  public int getItemCount() {
    return notes.size();
  }

  @SuppressLint("NotifyDataSetChanged")
  public void setNotes(List<Note> notes) {
    this.notes.clear();
    this.notes.addAll(notes);
    notifyDataSetChanged(); // TODO: 6/16/25 Investigate retaining scroll position.
  }

  private static class Holder extends ViewHolder {

    private final ItemNoteBinding binding;
    private final DateTimeFormatter formatter;
    Holder (@NonNull ItemNoteBinding binding, @NonNull DateTimeFormatter formatter) {
      super(binding.getRoot());
      this.binding = binding;
      this.formatter = formatter;
    }

    // DONE: 6/16/25 Define bind method to take a specified Note and insert its contents into the
    //  widgets referenced by the binding object.

    void bind(int position, Note note) {
      // TODO: 6/16/25 Set contents of binding fields to hold contents of noted; attach event
      //  listeners to binding.getRoot().
    }

  }

}
