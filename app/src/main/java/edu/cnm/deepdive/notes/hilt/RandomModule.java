package edu.cnm.deepdive.notes.hilt;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import jakarta.inject.Singleton;
import java.security.SecureRandom;
import java.util.Random;

@Module
@InstallIn(SingletonComponent.class)
public class RandomModule {

  @Provides
  @Singleton
  Random provideRandomGenerator() {
    return new SecureRandom();
  }

}
