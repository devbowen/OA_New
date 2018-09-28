package com.even.newoa.model.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.even.newoa.R;
import com.even.newoa.app.App;
import com.even.newoa.base.contract.login.LoginContract;
import com.even.newoa.model.bean.LoginResponse;
import com.even.newoa.model.http.HttpCallback;
import com.even.newoa.model.http.HttpHelper;
import com.even.newoa.util.NetworkUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;


public class LoginModel implements LoginContract.LoginModel {

    private static final String TAG = "LoginModel";
    private Context context;
    private String token;

    public LoginModel() {
        context = App.getContext();
    }

    @Override
    public void login(final String userAccount, String password, final HttpCallback httpCallback) {

        String json = "{\"userAccount\" :\"" + userAccount + "\" ,\"password\" : \"" + password + "\" }";
        Log.d(TAG, json);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        HttpHelper.getInstence().getLoginApis().login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        SharedPreferences.Editor editor = context.getSharedPreferences("login", MODE_PRIVATE).edit();
                        editor.putBoolean("isLogin", true);
                        editor.putString("token", token);
                        editor.putString("account", userAccount);
                        editor.apply();
                        httpCallback.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if ("HTTP 403 ".equals(e.getMessage())) {
                            httpCallback.onFailure(context.getString(R.string.incorrect_account_or_password));
                        } else if ("HTTP 500 ".equals(e.getMessage())) {
                            httpCallback.onError("服务器错误");
                        } else if ("connect timed out".equals(e.getMessage())) {
                            httpCallback.onError("连接超时");
                        } else {
                            Log.d(TAG, "onError: " + e.getMessage());
                            httpCallback.onError("登陆失败，原因" + e.getMessage());
                        }
                        httpCallback.onComplete();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });

    }

    @Override
    public boolean isNetWorkConnected() {
        Log.d(TAG, "isNetWorkConnected: " + NetworkUtils.isConnected());
        return NetworkUtils.isConnected();
    }
}
