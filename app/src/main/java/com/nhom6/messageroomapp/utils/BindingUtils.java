package com.nhom6.messageroomapp.utils;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;
import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;

public class BindingUtils {

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView view, RecyclerView.LayoutManager manager) {
        view.setLayoutManager(manager);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView view, BaseViewAdapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("selectedItemId")
    public static void setSelectedItemId(BottomNavigationView view, int itemId) {
        view.setSelectedItemId(itemId);
    }

    @BindingAdapter("itemSelectedChanged")
    public static void setSelectedChanged(BottomNavigationView view, NavigationBarView.OnItemSelectedListener listener) {
        view.setOnItemSelectedListener(listener);
    }

    @BindingAdapter("setupWithNavController")
    public static void setupWithNavController(BottomNavigationView view, NavController navController) {
        NavigationUI.setupWithNavController(view, navController);
    }

    @BindingAdapter({"setTextOverFlow", "setMaxLength"})
    public static void setTextOverFlow(TextView textView, String text, int maxLength) {
        if (text == null || text.isEmpty()) return;
        if (text.length() > maxLength) {
            text = text.substring(0, maxLength - 4) + "...";
        }
        textView.setText(text);
    }

    @BindingAdapter("setBackgroundGlide")
    public static void setBackgroundGlide(ImageView imageView, String backgroundUrl) {
        if (backgroundUrl == null || backgroundUrl.isEmpty()) return;
        Glide.with(imageView.getRootView())
                .load(backgroundUrl)
                .into(imageView);
    }

    @BindingAdapter("setImageGlide")
    public static void setImageGlide(ImageView imageView, String imgUrl) {
        if (imgUrl == null || imgUrl.isEmpty()) {
            Glide.with(imageView.getRootView())
                    .load(R.drawable.no_photo)
                    .into(imageView);
            return;
        }
        Glide.with(imageView.getRootView())
                .load(Uri.parse(imgUrl))
                .placeholder(R.drawable.no_photo)
                .into(imageView);
    }

    @BindingAdapter("setLatestMessage")
    public static void setLatestMessage(TextView txtView, MessageResponse message) {
        if (message != null) {
            txtView.setText(message.getContent());
        }
    }

    @BindingAdapter("setDate")
    public static void setDate(TextView txtDate, String dateString) {
        String[] dates = dateString.split("/");
        txtDate.setText(dates[0] + " tháng " + dates[1]);
    }

    @BindingAdapter("setImageResource")
    public static void setImageResource(ImageView imageView, int imageResource) {
        imageView.setImageResource(imageResource);
    }

    @BindingAdapter("setTintColor")
    public static void setTintColor(ImageView imageView, int imageResource) {
        Log.d("halinhit123", imageResource + "");
        imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), imageResource));
    }

    @BindingAdapter(value = {"selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner pAppCompatSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<String>) pAppCompatSpinner.getAdapter()).getPosition(newSelectedValue);
            pAppCompatSpinner.setSelection(pos, true);
        }
    }
    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static String captureSelectedValue(AppCompatSpinner pAppCompatSpinner) {
        return (String) pAppCompatSpinner.getSelectedItem();
    }

}
