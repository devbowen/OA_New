package com.even.newoa.ui.fragment.employee;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.even.newoa.R;
import com.even.newoa.base.BaseFragment;


public class ManageEmployeeFragment extends BaseFragment {

    public ManageEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_fragment_manage, container, false);
        return view;
    }


}
