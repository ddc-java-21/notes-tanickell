package edu.cnm.deepdive.notes.model.entity;

import java.time.Instant;

public class User {

    /* CRUD Operations:
  C - Create a new user (instance) when a user logs in for the first time.
  R - Retrieve information for an existing user and load into new instance when they log back in.
  U - Modify user information, e.g. when user changes their Display Name, or if oauthKey is updated.
  D - Not Applicable (but we may make an account dormant, rather than allow its removal).
   */

  private long id;
  private String oauthKey;
  private String displayName;
  private Instant created;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOauthKey() {
    return oauthKey;
  }

  public void setOauthKey(String oauthKey) {
    this.oauthKey = oauthKey;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Instant getCreated() {
    return created;
  }

  public void setCreated(Instant created) {
    this.created = created;
  }
}
