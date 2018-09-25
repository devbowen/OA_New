package com.even.newoa.model.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.even.newoa.R;
import com.even.newoa.app.App;
import com.even.newoa.base.contract.login.LoginContract;
import com.even.newoa.model.http.HttpCallback;
import com.even.newoa.model.http.HttpHelper;
import com.even.newoa.util.NetworkUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<ResponseBody> call = HttpHelper.getInstence().getLoginApis().login(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "Response: " + response);
                try {
                    token = response.body().string();
                    Log.d(TAG, "Response: body " + token);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = context.getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("token", token);
                    editor.putString("account", userAccount);
                    editor.apply();
                    httpCallback.onSuccess();
                } else {
                    Log.d(TAG, "onResponse: error " + response.errorBody());
                    httpCallback.onFailure(context.getString(R.string.incorrect_account_or_password));
                }
                httpCallback.onComplete();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                httpCallback.onError(context.getString(R.string.network_not_connected));
                httpCallback.onComplete();
            }
        });

    }

    @Override
    public boolean isNetWorkConnected() {
        Log.d(TAG, "isNetWorkConnected: " + NetworkUtils.isConnected());
        return NetworkUtils.isConnected();
    }
}
