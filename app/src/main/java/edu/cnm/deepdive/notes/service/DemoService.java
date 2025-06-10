package edu.cnm.deepdive.notes.service;

import dagger.hilt.android.scopes.ViewModelScoped;
import jakarta.inject.Inject;
import java.util.Random;

@ViewModelScoped
public class DemoService {

  @Inject
  DemoService(Random rng) {

  }

}
