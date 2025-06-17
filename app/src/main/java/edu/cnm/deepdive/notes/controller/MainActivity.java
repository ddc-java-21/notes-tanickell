package edu.cnm.deepdive.notes.controller;

import android.os.Bundle;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.notes.R;
import edu.cnm.deepdive.notes.databinding.ActivityMainBinding;
import edu.cnm.deepdive.notes.service.dao.UserDao;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private ActivityMainBinding binding;
  private AppBarConfiguration appBarConfig;
  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI();
    setupNavigation();
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfig);
  }

  private void setupUI() {
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
  }

  private void setupNavigation() {
    appBarConfig = new AppBarConfiguration.Builder(R.id.list_fragment)
        .build();
    NavHostFragment host = binding.navHostFragment.getFragment();
    navController = host.getNavController();
//    navController =
//        ((NavHostFragment) binding.navHostFragment.getFragment()).getNavController();     // reference to the piece of machinery that will swap in one fragment for another
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
  }



}