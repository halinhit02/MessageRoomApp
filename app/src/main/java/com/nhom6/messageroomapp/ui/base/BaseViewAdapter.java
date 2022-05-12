package com.nhom6.messageroomapp.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom6.messageroomapp.BR;

import java.util.List;

public abstract class BaseViewAdapter<T> extends RecyclerView.Adapter<BaseViewAdapter.BindingViewHolder<ViewDataBinding>> {

    protected final LayoutInflater inflater;
    protected List<T> mCollection;
    protected static Presenter<?> mPresenter;

    public BaseViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        final T item = mCollection.get(position);
        holder.getBinding().setVariable(BR.viewModel, item);
        holder.getBinding().setVariable(BR.listener, getPresenter());
        holder.getBinding().executePendingBindings();
    }

    public void remove(int position) {
        mCollection.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mCollection.clear();
        notifyDataSetChanged();
    }

    protected Presenter getPresenter() {
        return mPresenter;
    }

    public void setPresenter(Presenter<T> presenter) {
        mPresenter = presenter;
    }

    @Override
    public int getItemCount() {
        if (mCollection != null) {
            return mCollection.size();
        }
        return 0;
    }


    public static class BindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private final T mBinding;

        public BindingViewHolder( T binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public T getBinding() {
            return mBinding;
        }
    }

    public interface Presenter<T> {
        void onItemClick(View v, T data);
    }
}
