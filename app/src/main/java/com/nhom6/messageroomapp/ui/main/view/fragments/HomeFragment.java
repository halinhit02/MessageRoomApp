package com.nhom6.messageroomapp.ui.main.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.nhom6.messageroomapp.BR;
import com.nhom6.messageroomapp.R;
import com.nhom6.messageroomapp.data.model.common.AppUser;
import com.nhom6.messageroomapp.databinding.FragmentHomeBinding;
import com.nhom6.messageroomapp.ui.base.BaseMainFragment;
import com.nhom6.messageroomapp.ui.main.view.activities.MainActivity;
import com.nhom6.messageroomapp.ui.main.viewmodel.HomeViewModel;
import com.nhom6.messageroomapp.utils.AppUtils;
import com.nhom6.messageroomapp.utils.Constant;

public class HomeFragment extends BaseMainFragment<FragmentHomeBinding> {
    public static final String TAG = "HomeFragment";

    private AppUser mUser;
    private HomeViewModel homeViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initWidget() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        getDataBinding().setHome(homeViewModel);
        getDataBinding().setVariable(BR.clickListener, this);
    }

    @Override
    public void deInit() {
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = AppUtils.getAppUser();
        if (mUser == null) {
            return;
        }
        homeViewModel.init(mUser.getName(), mUser.getAvatar());
    }

    public void onClick(View view) {
        NavController navController = Navigation.findNavController(view);
        int idBtn = view.getId();
        if (idBtn == R.id.cv_chat) {
            ((MainActivity) requireActivity()).setSelectedItem(1);
        } else if (idBtn == R.id.cv_voice) {
        } else if (idBtn == R.id.cv_video) {
        } else if (idBtn == R.id.imgAvatar) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.Response_Key, mUser);
            navController.navigate(R.id.action_homeFragment_to_profileFragment, bundle, new NavOptions.Builder()
                    .setEnterAnim(android.R.animator.fade_in)
                    .setExitAnim(android.R.animator.fade_out)
                    .build());
            /*Intent intent = new Intent(requireActivity(), ProfileActivity.class);
            intent.putExtra(Constant.Response_Key, mUser);
            startActivity(intent);*/

        } else if (idBtn == R.id.logoutBtn) {
            requireActivity().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit().clear().apply();
            //((MainActivity)requireActivity()).nextScreen(AuthActivity.class, true);
            navController.navigate(R.id.action_homeFragment_to_authActivity, null, new NavOptions.Builder()
                    .setEnterAnim(android.R.animator.fade_in)
                    .setExitAnim(android.R.animator.fade_out)
                    .build());
            requireActivity().finish();
        }
    }
}
