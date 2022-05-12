package com.nhom6.messageroomapp.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SingleTypeAdapter<T> extends BaseViewAdapter<T> {

    protected int mLayoutRes;

    public SingleTypeAdapter(Context context) {
        super(context);
    }

    public SingleTypeAdapter(Context context, int layoutRes) {
        super(context);
        mCollection = new ArrayList<>();
        mLayoutRes = layoutRes;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindingViewHolder(DataBindingUtil.inflate(inflater, mLayoutRes, parent, false));
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public void add(T viewModel) {
        mCollection.add(viewModel);
        notifyDataSetChanged();
    }

    public void set(List<T> viewModels) {
        mCollection.clear();
        addAll(viewModels);
    }

    public void addAll(List<T> viewModels) {
        mCollection.addAll(viewModels);
        notifyDataSetChanged();
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }
}

