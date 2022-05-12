package com.nhom6.messageroomapp.ui.main.viewmodel;

import static com.nhom6.messageroomapp.utils.AppUtils.showMessageResult;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.WorkInfo;

import com.google.gson.Gson;
import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.base.BaseAPIResponse;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.storage.FileUploadRequest;
import com.nhom6.messageroomapp.data.model.storage.FolderName;
import com.nhom6.messageroomapp.data.model.storage.SignedUrl;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetRequest;
import com.nhom6.messageroomapp.data.model.storage.SignedUrlGetResponse;
import com.nhom6.messageroomapp.data.model.user.UserUpdateRequest;
import com.nhom6.messageroomapp.data.model.user.UserUpdateResponse;
import com.nhom6.messageroomapp.data.workers.WorkerLiveData;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DialogUtils;
import com.nhom6.messageroomapp.utils.FileUtils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

public class ProfileViewModel extends ViewModel implements View.OnClickListener {
    public ObservableField<AppUser> appUserField = new ObservableField<>();
    public ObservableField<String> passwordField = new ObservableField<>("");
    public ObservableField<String> avatarUrlField = new ObservableField<>();

    private FragmentActivity context;
    private AppUser appUser;
    private UserUpdateRequest userUpdateRequest;
    private Uri newAvatarUri = null;
    private ActivityResultLauncher<Intent> resultLauncher;
    private NavController navController;

    public void init(FragmentActivity context, AppUser mUser) {
        this.context = context;
        appUser = mUser;
        appUserField.set(mUser);
        if (newAvatarUri == null) {
            avatarUrlField.set(appUser.getAvatar());
        }
    }

    public void setResultLauncher(ActivityResultLauncher<Intent> resultLauncher) {
        this.resultLauncher = resultLauncher;
    }

    public void setNewAvatarUri(Uri newAvatarUri) {
        this.newAvatarUri = newAvatarUri;
        avatarUrlField.set(newAvatarUri.toString());
    }

    @Override
    public void onClick(View view) {
        navController = Navigation.findNavController(view);
        if (view.getId() == R.id.btn_back) {
            navController.popBackStack();
        } else if (view.getId() == R.id.iv_Avatar) {
            if (AppUtils.checkPermission(context)) {
                Intent intent = CropImage.getPickImageChooserIntent(context, "Chọn hình ảnh bằng", true, false);;
                resultLauncher.launch(intent);
            }
        } else if (view.getId() == R.id.btn_update) {
            appUser = appUserField.get();
            if (appUser == null) return;
            if (checkDataInput(appUser)) {
                DialogUtils.showLoadingDialog(context);
                userUpdateRequest = new UserUpdateRequest(appUser.getId(), appUser.getName(), appUser.getEmail(), appUser.getPhone(),
                        appUser.getAboutMe(), appUser.getGender(), appUser.dob, passwordField.get());
                if (newAvatarUri != null) {
                    getUploadSignedUrl(newAvatarUri);
                } else {
                    updateProfile(userUpdateRequest);
                }
            }
        }
    }

    private boolean checkDataInput(AppUser appUser) {
        String name = appUser.getName();
        String phone = appUser.getPhone();
        String email = appUser.getEmail();
        String dob = appUser.getDob();
        String password = passwordField.get();
        if (name == null || name.isEmpty()) {
            MRApplication.showMessage("Tên hiển thị không hợp lệ.");
            return false;
        }
        if (phone != null && !phone.isEmpty() && (phone.length() != 10 || !phone.startsWith("0"))) {
            MRApplication.showMessage("Số điện thoại không hợp lệ.");
            return false;
        }
        if (email != null && !email.isEmpty() && !email.matches(Constant.emailPattern)) {
            MRApplication.showMessage("Email không hợp lệ.");
            return false;
        }
        if ((email == null || email.isEmpty()) && (phone == null || phone.isEmpty())) {
            MRApplication.showMessage("Số điện thoại hoặc email không hợp lệ.");
            return false;
        }
        if (dob == null || dob.isEmpty()
                || (dob.split("-").length != 3 && dob.split("/").length != 3)) {
            MRApplication.showMessage("Ngày sinh không hợp lệ.");
            return false;
        }
        if (password == null || password.isEmpty() || password.length() < 6) {
            MRApplication.showMessage("Mật khẩu không hợp lệ.");
            return false;
        }
        return true;
    }

    public void getUploadSignedUrl(Uri resultUri) {
        String fileName = FileUtils.getFileName(context, resultUri);
        File uploadFile = FileUtils.createCopyAndReturnFile(MRApplication.the(), resultUri);
        SignedUrlGetRequest request = new SignedUrlGetRequest(FolderName.users, appUser.getId(), fileName);
        WorkerLiveData.SignedUrlGet(context, request)
                .observe(context, workInfo -> {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        SignedUrlGetResponse response = new Gson().fromJson(workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG), SignedUrlGetResponse.class);
                        SignedUrl signedUrl = response.getResult();
                        if (response.isSucceeded() && response.getResult() != null) {
                            appUser.setAvatar(signedUrl.getFileUrl());
                            userUpdateRequest.setAvatar(signedUrl.getFileUrl());
                            FileUploadRequest uploadRequest = new FileUploadRequest(signedUrl.getSignedUrl(), uploadFile);
                            uploadFile(uploadRequest);
                        } else {
                            showMessageResult(response.getMessage());
                        }
                    }
                });
    }

    private void uploadFile(FileUploadRequest request) {
        WorkerLiveData.FileUpload(context, request).observe(context, workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                BaseAPIResponse response = new Gson().fromJson(workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG), BaseAPIResponse.class);
                if (response.isSucceeded) {
                    updateProfile(userUpdateRequest);
                } else {
                    showMessageResult(response.getMessage());
                }
            }
        });
    }

    private void updateProfile(UserUpdateRequest request) {
        WorkerLiveData.UserUpdate(context, request).observe(context, workInfo -> {
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                UserUpdateResponse response = new Gson().fromJson(workInfo.getOutputData().getString(Constant.OUTPUT_DATA_TAG), UserUpdateResponse.class);
                if (DialogUtils.alertDialog != null)
                    DialogUtils.alertDialog.dismiss();
                if (response.isSucceeded() && response.getResult()) {
                    AppUtils.saveAppUser(appUser);
                    MRApplication.showMessage("Hồ sơ cá nhân đã được cập nhật.");
                    navController.popBackStack();
                } else {
                    showMessageResult(response.getMessage());
                }
            }
        });
    }
}
