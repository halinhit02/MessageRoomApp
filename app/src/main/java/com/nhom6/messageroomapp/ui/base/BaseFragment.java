package com.nhom6.messageroomapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initWidget();

    public abstract void deInit();

    private T dataBinding;

    public T getDataBinding() {
        return dataBinding;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initWidget();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deInit();
    }

    public void showMessage(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(@StringRes int msgRes) {
        Toast.makeText(requireContext(), msgRes, Toast.LENGTH_SHORT).show();
    }

    public void onBackFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
