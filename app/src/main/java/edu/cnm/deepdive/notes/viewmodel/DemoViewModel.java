package edu.cnm.deepdive.notes.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.notes.service.DemoService;
import jakarta.inject.Inject;

@HiltViewModel
public class DemoViewModel extends ViewModel {

  private final MutableLiveData<DemoService> demoService;
  @Inject
  DemoViewModel(@ApplicationContext Context context, DemoService demoService) {
    this.demoService = new MutableLiveData<>(demoService);
  }

  public LiveData<DemoService> getDemoService() {
    return demoService;
  }
}
