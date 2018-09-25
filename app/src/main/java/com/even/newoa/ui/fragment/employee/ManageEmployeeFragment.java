package com.even.newoa.ui.fragment.employee;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.even.newoa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManageEmployeeFragment extends Fragment {


    public ManageEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.employee_fragment_manage, container, false);
    }

}
