package com.nhom6.messageroomapp.ui.main.adapter;

import android.content.Context;
import android.util.ArrayMap;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiTypeAdapter<T> extends BaseViewAdapter<T> {

    private final ArrayList<Integer> mCollectionViewType;
    private final ArrayMap<Integer, Integer> mItemLayoutMap = new ArrayMap<>();

    public MultiTypeAdapter(Context context) {
        super(context);
        mCollection = new ArrayList<>();
        mCollectionViewType = new ArrayList<>();
    }

    public MultiTypeAdapter(Context context, Map<Integer, Integer> itemLayoutMap) {
        super(context);
        mCollection = new ArrayList<>();
        mCollectionViewType = new ArrayList<>();
        if (itemLayoutMap != null && !itemLayoutMap.isEmpty()) {
            mItemLayoutMap.putAll(itemLayoutMap);
        }
    }

    @NonNull
    @Override
    public BaseViewAdapter.BindingViewHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindingViewHolder<>(DataBindingUtil.inflate(inflater, getLayoutRes(viewType), parent, false));
    }

    @LayoutRes
    private Integer getLayoutRes(Integer viewType) {
        return mItemLayoutMap.get(viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return mCollectionViewType.get(position);
    }

    public void addItemLayoutMap(Integer viewType, @LayoutRes Integer layoutRes) {
        mItemLayoutMap.put(viewType, layoutRes);
    }

    public void set(List<T> viewModels, int viewType) {
        mCollection.clear();
        mCollectionViewType.clear();
        if (viewModels == null) {
            add(null, viewType);
        } else {
            addAll(viewModels, viewType);
        }
    }

    public void add(T viewModel, int viewType) {
        mCollection.add(viewModel);
        mCollectionViewType.add(viewType);
        notifyItemInserted(mCollection.size() - 1);
        //notifyItemRangeChanged(mCollection.size() - 1, 1);
    }

    public void add(int position, T viewModel, int viewType) {
        mCollection.add(position, viewModel);
        mCollectionViewType.add(position, viewType);
        notifyItemInserted(position);
    }

    public void addAll(List<T> viewModels, int viewType) {
        mCollection.addAll(viewModels);
        for (int i = 0; i < viewModels.size(); i++) {
            mCollectionViewType.add(viewType);
        }
        notifyItemRangeChanged(0, mCollection.size()-1);
    }

    public void addAll(int position, List<T> viewModels, int viewType) {
        mCollection.addAll(position, viewModels);
        for (int i = 0; i < viewModels.size(); i++) {
            mCollectionViewType.add(position + i, viewType);
        }
        notifyItemRangeChanged(position, viewModels.size() - position);
    }

    public void remove(int position) {
        mCollectionViewType.remove(position);
        super.remove(position);
    }

    public void clear() {
        mCollectionViewType.clear();
        super.clear();
    }
}
