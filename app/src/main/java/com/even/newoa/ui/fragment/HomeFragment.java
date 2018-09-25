package com.even.newoa.ui.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.even.newoa.R;
import com.even.newoa.base.BaseFragment;
import com.even.newoa.ui.activity.LoginActivity;
import com.even.newoa.ui.activity.MainActivity;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private Button logoutBtn;
    private Button signInBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        logoutBtn = view.findViewById(R.id.btn_logout);
        signInBtn = view.findViewById(R.id.btn_sign_in_home);

        logoutBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                logout();
                break;
            case R.id.btn_sign_in_home:
                signIn();
                setToolbarTitle(R.string.fgt_home_sign_in);
                break;
            default:
                break;
        }
        //设置ToolBar
        ((MainActivity) getActivity()).setupToolbar(false);
        //锁定侧滑
        ((MainActivity) getActivity()).lockDrawer(true);
    }

    private void signIn() {
        changeFragment(this, fragmentUtils.getLocationFragment());
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("login", MODE_PRIVATE).edit();
        editor.putBoolean("isLogin", false);
        editor.apply();
        getActivity().finish();
    }
}
