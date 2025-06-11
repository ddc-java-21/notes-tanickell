package edu.cnm.deepdive.notes.model.entity;

import android.net.Uri;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "image",
    foreignKeys = {
        @ForeignKey(
            entity = Note.class,
            parentColumns = "note_id",
            childColumns = "note_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Image {

  /*
  C - Attach an image to a note
  R - Retrieve all images for a note
  U - Maybe update caption
  D - Remove an image from a note
   */

    /*
  C - Create a new note instance when a user first attaches an image to a note.
  R - Retrieve all images for a note and display them in a grid view or gallery (a la reddit?).
  R - Retrieve a single image for display when a user clicks the image (e.g., take them to the gallery app, or have a dedicated viewer?).
  U - Update the caption for the image instance when the user chooses to edit the caption (from the note editor view).
  D - Delete the image instance when the user removes the image from a note.
  D - Delete multiple images (instances) from a note when a user mass selects and removes from a note.
  D - Delete multiple collections when a user deletes multiple notes (taken care of in the note class?).
   */

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "image_id")
  private long id;

  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String caption;

  @ColumnInfo(name = "mime_type", index = true)
  private String mimeType;

  private Uri uri;

  @ColumnInfo(index = true)
  private Instant created;

  @ColumnInfo(name = "note_id", index = true)
  private long noteId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public Uri getUri() {
    return uri;
  }

  public void setUri(Uri uri) {
    this.uri = uri;
  }

  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }

  public long getNoteId() {
    return noteId;
  }

  public void setNoteId(long noteId) {
    this.noteId = noteId;
  }
}
