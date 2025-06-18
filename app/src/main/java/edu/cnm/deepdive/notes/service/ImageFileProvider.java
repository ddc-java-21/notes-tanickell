package edu.cnm.deepdive.notes.service;

import android.content.Context;
import androidx.core.content.FileProvider;
import edu.cnm.deepdive.notes.R;

public class ImageFileProvider extends FileProvider {

  public ImageFileProvider() {
    super(R.xml.provider_paths);
  }

}
