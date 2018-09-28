package com.even.newoa.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.even.newoa.R;
import com.even.newoa.util.FragmentUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private static final String TAG = "BaseFragment";
    protected FragmentUtils fragmentUtils;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;


    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BaseFragment.
     */
    public static BaseFragment newInstance(String param1, String param2) {
        BaseFragment fragment = new BaseFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        fragmentUtils = FragmentUtils.getInstance();
        fragmentManager = getActivity().getSupportFragmentManager();
        toolbar = getActivity().findViewById(R.id.toolbar);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
        Log.d(TAG, "onSaveInstanceState: 被销毁");
    }

    protected void changeFragment(Fragment currentFragment, Fragment nextFragment) {
        fragmentUtils.changeFragment(fragmentManager, currentFragment, nextFragment);
    }

    protected void setToolbarTitle(int resId) {
        toolbar.setTitle(resId);
    }

}
