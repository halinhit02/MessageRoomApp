package com.nhom6.messageroomapp.ui.main.view.fragments;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.databinding.FragmentPhotoViewerBinding;
import com.nhom6.messageroomapp.ui.base.BaseFragment;
import com.nhom6.messageroomapp.ui.main.viewmodel.PhotoViewerViewModel;
import com.nhom6.messageroomapp.utils.IntentUtils;

public class PhotoViewerFragment extends BaseFragment<FragmentPhotoViewerBinding> implements View.OnClickListener {

    public static final String TAG = "PhotoViewerFragment";

    private PhotoViewerViewModel viewModel;
    private String imageUrl = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo_viewer;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            imageUrl = getArguments().getString(TAG);
        } else {
            NavHostFragment.findNavController(this).popBackStack();
        }
        viewModel = ViewModelProviders.of(this).get(PhotoViewerViewModel.class);
    }

    @Override
    public void initWidget() {
        getDataBinding().setViewModel(viewModel);
        getDataBinding().setOnClicked(this);
        viewModel.setImageUrl(imageUrl);
    }

    @Override
    public void deInit() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            Navigation.findNavController(view).popBackStack();
        } else if (view.getId() == R.id.iv_download) {
            startActivity(IntentUtils.openFileOnWeb(imageUrl));
        }
    }
}
