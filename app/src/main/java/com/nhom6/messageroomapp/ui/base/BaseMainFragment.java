package com.nhom6.messageroomapp.ui.base;

import androidx.databinding.ViewDataBinding;

import com.nhom6.messageroomapp.ui.main.view.activities.MainActivity;

public abstract class BaseMainFragment<T extends ViewDataBinding> extends BaseFragment<T>{

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)requireActivity()).mainViewModel.setVisibleNavigation(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)requireActivity()).mainViewModel.setVisibleNavigation(false);
    }
}
