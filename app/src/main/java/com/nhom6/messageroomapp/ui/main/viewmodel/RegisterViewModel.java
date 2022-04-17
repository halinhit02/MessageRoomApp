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

import com.nhom6.messageroomapp.MRApplication;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.data.model.user.UserRegisterRequest;
import com.nhom6.messageroomapp.data.repository.APINetwork;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;
import com.nhom6.messageroomapp.utils.DialogUtils;
import com.theartofdev.edmodo.cropper.CropImage;

public class RegisterViewModel extends ViewModel implements View.OnClickListener {
    public final ObservableField<UserRegisterRequest> userRegisterField = new ObservableField<>();
    public final ObservableField<String> avatarField = new ObservableField<>();
    public final ObservableField<String> phoneField = new ObservableField<>("");

    private FragmentActivity context;
    private ActivityResultLauncher<Intent> intentResultLauncher;
    private Uri avatarUri = null;
    private AppUser appUser;
    private NavController controller;
    private UserRegisterRequest registerRequest;

    public void setIntentResultLauncher(ActivityResultLauncher<Intent> intentResultLauncher) {
        this.intentResultLauncher = intentResultLauncher;
    }

    public void init(FragmentActivity context) {
        this.context = context;
        userRegisterField.set(new UserRegisterRequest());
    }

    public void setAvatarUri(Uri avatarUri) {
        this.avatarUri = avatarUri;
        avatarField.set(avatarUri.toString());
    }

    @Override
    public void onClick(View view) {
        controller = Navigation.findNavController(view);
        if (view.getId() == R.id.iv_Avatar) {
            if (AppUtils.checkPermission(context)) {
                Intent intent = CropImage.getPickImageChooserIntent(context, "Chọn hình ảnh bằng", false, false);
                intentResultLauncher.launch(intent);
            }
        } else if (view.getId() == R.id.btn_register) {
            registerRequest = userRegisterField.get();
            if (registerRequest == null) return;
            if (checkDataInput(registerRequest)) {
                DialogUtils.showLoadingDialog(context);
                register(registerRequest);
            }
        } else if (view.getId() == R.id.btn_auth) {
            if (controller != null) {
                controller.popBackStack();
            }
        }
    }

    private boolean checkDataInput(UserRegisterRequest request) {
        String name = request.getName();
        String dob = request.getDob();
        String phoneNumber = request.getPhone();
        String password = request.getPassword();
        if (name == null || name.isEmpty()) {
            MRApplication.showMessage("Tên hiển thị không hợp lệ.");
            return false;
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            MRApplication.showMessage("Nhập Số điện thoại.");
            return false;
        }
        if (!phoneNumber.startsWith("0") || phoneNumber.length() != 10 || !phoneNumber.matches(Constant.phonePattern)) {
            MRApplication.showMessage("Số điện thoại không hợp lệ.");
            return false;
        }
        if (dob == null || dob.isEmpty()
                || (dob.split("-").length != 3 && dob.split("/").length != 3)) {
            MRApplication.showMessage("Ngày sinh không hợp lệ.");
            return false;
        }
        if (password == null || password.isEmpty() || password.length() < 6) {
            MRApplication.showMessage("Mật khẩu phải lớn hơn 6 ký tự");
            return false;
        }
        return true;
    }

    public void register(UserRegisterRequest request) {
        APINetwork.register(request, response -> {
            if (response.isSucceeded()) {
                appUser = response.getResult().getData();
                if (appUser != null) {
                    AppUtils.saveAppUser(appUser);
                    AppUtils.saveStringSharedRef(Constant.Token_Key, response.getResult().getToken());
                    goToMain();
                }
            } else {
                showMessageResult(response.getMessage());
            }
        });
    }

    private void goToMain() {
        showMessageResult("Tài khoản đã được tạo.");
        if (controller != null) {
            controller.navigate(R.id.action_registerFragment_to_mainActivity);
            context.finish();
        }
    }
}
