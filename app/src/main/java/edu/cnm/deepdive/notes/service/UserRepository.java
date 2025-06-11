package edu.cnm.deepdive.notes.service;

import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.notes.model.entity.User;
import edu.cnm.deepdive.notes.service.dao.UserDao;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

  private final UserDao userDao;
  private final Scheduler scheduler;

  @Inject
  UserRepository(UserDao userDao) { // package-private: don't want it to be part of API, won't be included in java docs (no documentation for package-private)
    this.userDao = userDao;
    scheduler = Schedulers.io(); // utility class that returns a scheduler
  }

  public Single<User> save(User user) {
    return (
        (user.getId() == 0)
        ? userDao.insertAndGet(user)
        : userDao.update(user)
            .andThen(Single.just(user))
    )
        .subscribeOn(scheduler);
  }

  public LiveData<User> get(long userId) { // delegate this to the dao
    userDao.select(userId);
  }

  public Completable remove(User user) {
    return userDao
        .delete(user)
        .subscribeOn(scheduler); // attach to thread
  }

}
