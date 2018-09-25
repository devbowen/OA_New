package com.even.newoa.ui.fragment.employee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.even.newoa.R;
import com.even.newoa.base.BaseFragment;
import com.even.newoa.ui.activity.MainActivity;


public class EmployeeFragment extends BaseFragment implements View.OnClickListener {


    private static final String TAG = "EmployeeFragment";
    private CardView eeManageCard;
    private CardView eeAddCard;
    private Toolbar toolbar;

    public EmployeeFragment() {
        // Required empty public constructor
    }


    /**
     * 进行布局的初始化
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.employee_fragment, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        eeManageCard = view.findViewById(R.id.card_ee_manage);
        eeAddCard = view.findViewById(R.id.card_ee_add);

        eeManageCard.setOnClickListener(this);
        eeAddCard.setOnClickListener(this);
        return view;
    }

    //当 Activity 执行完 onCreate() 方法后调用
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_ee_manage:
                changeFragment(this, fragmentUtils.getManageEmployeeFragment());
                setToolbarTitle(R.string.employee_manage);
                break;
            case R.id.card_ee_add:
                changeFragment(this, fragmentUtils.getAddEmployeeFragment());
                setToolbarTitle(R.string.employee_add);
                break;
            default:
                break;
        }
        //设置ToolBar
        ((MainActivity) getActivity()).setupToolbar(false);
        //锁定侧滑
        ((MainActivity) getActivity()).lockDrawer(true);
    }


}
