package edu.cnm.deepdive.notes.service.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import edu.cnm.deepdive.notes.model.entity.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

@Dao
public interface UserDao {

  @Insert
  Single<Long> insert(User user); // When should we use user.setId(...) to update the object in memory // single

  default Single<User> insertAndGet(User user) {
    return insert(user)
        .map((id) -> {
          user.setId(id);
          return user;
        });
  }

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(List<User> users); // item in an iterable (could be Collection or Iterable)

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  Single<List<Long>> insert(User... users); // item in an array (could be User[])

  @Update
  Completable update(User user); // when Single<Integer>, returns number of records overall that were affected (modified) by this update // no update is performed if info is the same

  @Update(onConflict = OnConflictStrategy.IGNORE)
  Single<Integer> update(List<User> users); // Completable update(List<User> users);

  @Update(onConflict = OnConflictStrategy.IGNORE)
  Single<Integer> update(User... users);

  @Delete
  Completable delete(User user);

  @Delete
  Completable delete(List<User> users);

  @Delete
  Completable delete(User... users);

  @Query("SELECT * FROM user WHERE user_id = :userId")
  LiveData<User> select(long userId);

  @Query("SELECT * FROM user WHERE oauth_key = :oauthKey")
  Maybe<User> select(String oauthKey);

  @Query("SELECT * FROM user ORDER BY display_name ASC") // ASC or DESC; if blank, ASC assumed (default)
  LiveData<List<User>> select();

}
