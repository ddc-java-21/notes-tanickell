package edu.cnm.deepdive.notes.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "note",
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class Note {

    /*
  C - Create a new note instance when the app user adds/starts a new note.
  R - Retrieve all note instances for a user and display them in some order (e.g. descending by last modified).
  R - Retrieve a single note instance and display its details (title, contents, etc.) in an editor window.
  U - Update a note instance when author chooses to edit a note's contents or change its title.
  D - Delete a note when a user chooses to remove a note from their inventory.
  D - Bulk remove note instances when group selected from list screen by author.
   */

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "note_id")
  private long id;

  @ColumnInfo(collate = ColumnInfo.NOCASE, index = true)
  private String title;

  private String description;

  @ColumnInfo(index = true)
  private Instant created;

  @ColumnInfo(index = true)
  private Instant modified;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }

  public Instant getModified() {
    return modified;
  }

  public void setModified(Instant modified) {
    this.modified = modified;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
}
