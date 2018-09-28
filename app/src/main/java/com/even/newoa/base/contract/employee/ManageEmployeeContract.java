package com.even.newoa.base.contract.employee;

import com.even.newoa.base.BasePresenter;
import com.even.newoa.base.BaseView;

public interface ManageEmployeeContract {

    interface ManageEmployeeView extends BaseView {
        void showProgress();

        void hideProgress();

    }

    interface ManageEmployeeModle {
        void getAll();
    }

    abstract class ManageEmployeePresenter extends BasePresenter {

    }
}
