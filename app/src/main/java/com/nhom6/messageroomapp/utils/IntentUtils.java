package com.nhom6.messageroomapp.utils;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

import com.nhom6.messageroomapp.ui.base.BaseActivity;
import com.nhom6.messageroomapp.ui.main.presenter.ObjectCallback;
import com.theartofdev.edmodo.cropper.CropImage;

public class IntentUtils {
    private static ActivityResultLauncher<String> permissionResultLauncher;
    private ActivityResultLauncher<Intent> intentResultLauncher;

    public IntentUtils(FragmentActivity context, ObjectCallback<Intent> intentObjectCallback) {
        permissionResultLauncher = context.registerForActivityResult(new ActivityResultContracts.RequestPermission()
                , isGranted -> {
                    if (!isGranted) {
                        ((BaseActivity) context).showMessage("Ban can cho phep quyen truy cap.");
                    }
                });
        intentResultLauncher = context.registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        intentObjectCallback.onVoid(result.getData());
                        //((BaseActivity) context).showMessage(result.getData().toString()); //getExtras().get("data")
                    }
                });
    }

    private static boolean checkPermission(Context context, String permission) {
        if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionResultLauncher.launch(permission);
            return false;
        }
        return true;
    }

    public static void cameraIntent(Context context, ActivityResultLauncher<Intent> intentResultLauncher) {
        Intent intent = CropImage.getCameraIntent(context, null);
        intentResultLauncher.launch(intent);
    }

    public static void galleryIntent(Context context, ActivityResultLauncher<Intent> intentResultLauncher) {
        Intent intent = CropImage.getPickImageChooserIntent(context, "Chọn hình ảnh bằng", true, false);
        intentResultLauncher.launch(intent);
    }

    public static void contentIntent(Context context, ActivityResultLauncher<Intent> intentResultLauncher) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*");
        intentResultLauncher.launch(intent);
    }

    public static Intent openFileOnWeb(String url) {
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url += "http://";
        }
        Intent viewIntent = new Intent(Intent.ACTION_VIEW);
        viewIntent.setData(Uri.parse(url));
        return viewIntent;
    }
}
