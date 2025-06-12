package edu.cnm.deepdive.notes.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "user",
    indices = {
        @Index(value = "oauth_key", unique = true),
        @Index(value = "display_name", unique = true)
    }
)
public class User {

    /* CRUD Operations:
  C - Create a new user (instance) when a user logs in for the first time.
  R - Retrieve information for an existing user and load into new instance when they log back in.
  U - Modify user information, e.g. when user changes their Display Name, or if oauthKey is updated.
  D - Not Applicable (but we may make an account dormant, rather than allow its removal).
   */

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "user_id")
  private long id;

  @NonNull
  @ColumnInfo(name = "oauth_key")
  private String oauthKey = "";

  @NonNull
  @ColumnInfo(name = "display_name", collate = ColumnInfo.NOCASE)
  private String displayName = "";

  @NonNull
  private Instant created = Instant.now();

  @NonNull
  private Instant modified = Instant.now();

  public long getId() {
    return id;
  }

  public User setId(long id) {
    this.id = id;
    return this;
  }

  @NonNull
  public String getOauthKey() {
    return oauthKey;
  }

  public User setOauthKey(@NonNull String oauthKey) {
    this.oauthKey = oauthKey;
    return this;
  }

  @NonNull
  public String getDisplayName() {
    return displayName;
  }

  public User setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
    return this;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public User setCreated(@NonNull Instant created) {
    this.created = created;
    return this;
  }

  @NonNull
  public Instant getModified() {
    return modified;
  }

  public User setModified(@NonNull Instant modified) {
    this.modified = modified;
    return this;
  }
}
