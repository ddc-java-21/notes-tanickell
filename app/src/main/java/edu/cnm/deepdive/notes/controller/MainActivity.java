package edu.cnm.deepdive.notes.controller;

import android.os.Bundle;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.notes.databinding.ActivityMainBinding;
import edu.cnm.deepdive.notes.service.dao.UserDao;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
  }

}