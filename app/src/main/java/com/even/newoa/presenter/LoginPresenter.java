package com.even.newoa.presenter;

import com.even.newoa.R;
import com.even.newoa.app.App;
import com.even.newoa.base.contract.login.LoginContract;
import com.even.newoa.model.http.HttpCallback;
import com.even.newoa.model.login.LoginModel;


/**
 * @author Even
 * @date 2018-08-24 21:34
 */

public class LoginPresenter extends LoginContract.LoginPresenter {
    private LoginContract.LoginView loginView;
    private LoginContract.LoginModel loginModel;

    public LoginPresenter(LoginContract.LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModel();
        attachView(loginView);
    }

    @Override
    public boolean isNetWorkConnected() {
        if (!loginModel.isNetWorkConnected()) {
            if (isViewAttached()) {
                loginView.loginFail(App.getContext().getString(R.string.network_not_connected));
            }
            return false;
        }
        return true;
    }

    @Override
    public void loginVerification(String userAccount, String password) {

        if (!isViewAttached()) {
            //如果没有View引用就不加载数据
            return;
        }


        loginView.showProgress();
        loginModel.login(userAccount, password, new HttpCallback<String>() {
            @Override
            public void onSuccess() {
                if (isViewAttached()) {
                    loginView.loginSuccess();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (isViewAttached()) {
                    loginView.loginFail(msg);
                }
            }

            @Override
            public void onError(String msg) {
                if (isViewAttached()) {
                    loginView.loginFail(msg);
                }
            }

            @Override
            public void onComplete() {
                if (isViewAttached()) {
                    loginView.hideProgress();
                }
            }
        });
    }


}
