package com.nhom6.messageroomapp.ui.base;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.utils.Constant;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    public abstract int getLayoutId();

    public abstract void initData();

    public abstract void initWidget();

    public abstract void deInit();

    private T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initData();
        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deInit();
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    public T getBinding() {
        return binding;
    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(@StringRes int msgRes) {
        Toast.makeText(this, msgRes, Toast.LENGTH_SHORT).show();
    }

    public boolean checkPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.WRITE_STORAGE_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.WRITE_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PERMISSION_GRANTED) {
                showMessage("Cần cho phép quyền truy cập bộ nhớ!");
            }
        }
    }

    public void saveSharedRef(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSharedPref (String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void nextScreenWithIntent(Intent intent) {
        startActivity(intent);
    }

    public void nextScreen(Class<?> nextScreen, boolean isFinish) {
        startActivity(new Intent(this, nextScreen));
        if (isFinish) {
            finish();
        }
    }
}
