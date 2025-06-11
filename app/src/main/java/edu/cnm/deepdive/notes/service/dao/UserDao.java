package edu.cnm.deepdive.notes.service.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import edu.cnm.deepdive.notes.model.entity.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

@Dao
public interface UserDao {

  @Insert
  Single<Long> insert(User user); // When should we use user.setId(...) to update the object in memory // single

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



}
