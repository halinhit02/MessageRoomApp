package com.nhom6.messageroomapp.ui.main.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    public ObservableField<String> nameField = new ObservableField<>("");
    public ObservableField<String> avatarUrlField = new ObservableField<>("");

    public void init(String name, String avatarUrl) {
        nameField.set(name);
        avatarUrlField.set(avatarUrl);
    }
}
