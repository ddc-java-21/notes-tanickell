package edu.cnm.deepdive.notes.model.entity;

import java.time.Instant;

public class Note {

    /*
  C - Create a new note instance when the app user adds/starts a new note.
  R - Retrieve all note instances for a user and display them in some order (e.g. descending by last modified).
  R - Retrieve a single note instance and display its details (title, contents, etc.) in an editor window.
  U - Update a note instance when author chooses to edit a note's contents or change its title.
  D - Delete a note when a user chooses to remove a note from their inventory.
  D - Bulk remove note instances when group selected from list screen by author.
   */

  private long id;
  private String title;
  private String description;
  private Instant created;
  private Instant modified;
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
