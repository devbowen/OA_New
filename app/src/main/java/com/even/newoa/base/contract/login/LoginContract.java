package com.even.newoa.base.contract.login;

import com.even.newoa.base.BasePresenter;
import com.even.newoa.base.BaseView;
import com.even.newoa.model.http.HttpCallback;

/**
 * @author Even
 * @date 2018-08-24 20:54
 */

public interface LoginContract {

    interface LoginModel {

        void login(String userAccount, String password, HttpCallback httpCallback);

        boolean isNetWorkConnected();

    }

    interface LoginView extends BaseView {

        void loginSuccess();

        void loginFail(String msg);
    }

    abstract class LoginPresenter extends BasePresenter {
        public abstract boolean isNetWorkConnected();

        public abstract void loginVerification(String userAccount, String password);
    }

}
