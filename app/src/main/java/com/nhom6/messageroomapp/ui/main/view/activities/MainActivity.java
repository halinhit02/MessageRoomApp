package com.nhom6.messageroomapp.ui.main.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.databinding.ActivityMainBinding;
import com.nhom6.messageroomapp.ui.base.BaseActivity;
import com.nhom6.messageroomapp.ui.main.viewmodel.ApplicationViewModel;
import com.nhom6.messageroomapp.ui.main.viewmodel.MainViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public static MainViewModel mainViewModel;
    private ApplicationViewModel appViewModel;
    private AppUser appUser;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        appViewModel = ViewModelProviders.of(this).get(ApplicationViewModel.class);
        appUser = AppUtils.getAppUser();
        appViewModel.setAppUser(appUser);
    }

    @Override
    public void initWidget() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getBinding().setMain(mainViewModel);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        mainViewModel.setNavControllerField(navController);
    }

    @Override
    public void deInit() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        appUser = AppUtils.getAppUser();
        if (appUser != null) {
            /*mainViewModel.init(appUser.getId());
            mainViewModel.appUserLiveData.observe(this, appUserResponse -> {
               if (appUserResponse.getCode() == 401) {
                    goToAuthenticateActivity();
                } else if (appUserResponse.isSucceeded && appUserResponse.getResult() != null) {
                    AppUser appUser = appUserResponse.getResult();
                   appViewModel.setAppUser(appUser);
                    AppUtils.saveAppUser(appUser);
                }
            });*/
        } else {
            goToAuthenticateActivity();
        }
    }

    @Override
    public void onBackPressed() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment currentFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        super.onBackPressed();
    }

    public void setSelectedItem(int pos) {
        int itemId = R.id.navigation_home;
        if (pos == 1) {
            itemId = R.id.navigation_conversation;
            mainViewModel.setSelectedId(itemId);
        }
    }

    private void goToAuthenticateActivity() {
        nextScreen(AuthActivity.class, true);
    }

}