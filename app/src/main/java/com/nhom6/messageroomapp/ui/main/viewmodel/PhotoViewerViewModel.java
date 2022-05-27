package com.nhom6.messageroomapp.ui.main.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class PhotoViewerViewModel  extends ViewModel {

    public ObservableField<String> urlField = new ObservableField<>();

    public void setImageUrl(String url) {
        urlField.set(url);
    }
}
