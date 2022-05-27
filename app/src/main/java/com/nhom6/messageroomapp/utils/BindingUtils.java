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
import com.nhom6.messageroomapp.data.model.message.MessageClient;
import com.nhom6.messageroomapp.data.model.message.MessageResponse;
import com.nhom6.messageroomapp.ui.base.BaseViewAdapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import io.agora.rtm.RtmFileMessage;
import io.agora.rtm.RtmImageMessage;
import io.agora.rtm.RtmMessage;
import io.agora.rtm.RtmMessageType;

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

    @BindingAdapter("setMessageImage")
    public static void setMessageImage(ImageView imageView, MessageClient message) {
        if (message.isClient() && message.getClientMessage().getMessageType() == RtmMessageType.IMAGE) {
            RtmImageMessage imageMessage = (RtmImageMessage) message.getClientMessage();
            Glide.with(imageView.getRootView())
                    .load(imageMessage.getThumbnail())
                    .override(imageMessage.getThumbnailWidth(), imageMessage.getThumbnailHeight())
                    .into(imageView);
        } else if (!message.isClient() && message.getServerMessage().getMessageType() == 1){
            Glide.with(imageView.getRootView())
                    .load(message.getServerMessage().getAttachments().get(0).getFileUrl())
                    .placeholder(R.drawable.no_photo)
                    .into(imageView);
        }
    }

    @BindingAdapter("setFileTypeImg")
    public static void setFileTypeImg(ImageView imageView, MessageClient messageClient) {
        if (messageClient.getServerMessage() == null) return;
        MessageResponse messageResponse = messageClient.getServerMessage();
        if (messageResponse.getMessageType() == 4){
            Glide.with(imageView.getRootView())
                    .load(messageResponse.getAttachments().get(0).getThumbUrl())
                    .placeholder(R.drawable.no_photo)
                    .into(imageView);
        }
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

    @BindingAdapter(("setFileName"))
    public static void setFileName(TextView textView, MessageClient message) {
        if (message.isClient() && message.getClientMessage().getMessageType() == RtmMessageType.FILE) {
            RtmFileMessage fileMessage = (RtmFileMessage) message.getClientMessage();
            textView.setText(fileMessage.getFileName());
        } else if (!message.isClient() && message.getServerMessage().getMessageType() == 4) {
            textView.setText(FileUtils.getFileName(message.getServerMessage().getAttachments().get(0).getFileUrl()));
        }
    }

    @BindingAdapter(("setFileSize"))
    public static void setFileSize(TextView textView, RtmMessage message) {
        if (message == null) return;
        if (message.getMessageType() == RtmMessageType.FILE) {
            RtmFileMessage fileMessage = (RtmFileMessage) message;
            long size = fileMessage.getSize();
            DecimalFormat format = new DecimalFormat("#.##");
            format.setRoundingMode(RoundingMode.CEILING);
            String fileSize = size + " B";
            if (size/Math.pow(10, 9) >= 1) {
                fileSize = format.format(size/Math.pow(10, 9)) + " GB";
            } else if (size/Math.pow(10, 6) >= 1) {
                fileSize = format.format(size/Math.pow(10, 6)) + " MB";
            } else if (size/Math.pow(10, 3) >= 1) {
                fileSize = format.format(size / Math.pow(10, 3)) + " KB";
            }
            textView.setText(fileSize);
        }
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
