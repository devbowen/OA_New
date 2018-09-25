package com.even.newoa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.even.newoa.R;
import com.even.newoa.base.BaseActivity;
import com.even.newoa.base.contract.login.LoginContract;
import com.even.newoa.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView, View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private Button loginBtn;
    private TextInputEditText accountEt;
    private TextInputEditText passwordEt;
    private String account = "";
    private String password = "";
    private ProgressBar progressBar;
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (this.getSharedPreferences("login", MODE_PRIVATE).getBoolean("isLogin", false)) {
            loginSuccess();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initViews();
        initEvents();

        loginPresenter = new LoginPresenter(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }


    private void initViews() {
        loginBtn = findViewById(R.id.btn_login);
        accountEt = findViewById(R.id.et_account);
        passwordEt = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progress_bar_login);
        usernameWrapper = findViewById(R.id.username_wrapper);
        passwordWrapper = findViewById(R.id.password_wrapper);
    }

    private void initEvents() {
        loginBtn.setOnClickListener(this);

        accountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                usernameWrapper.setError(null);
            }
        });

        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordWrapper.setError(null);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (loginPresenter.isNetWorkConnected()) {
            account = accountEt.getText().toString();
            password = passwordEt.getText().toString();

            if ("".equals(account)) {
                usernameWrapper.setError(getString(R.string.account_empty));
            } else if ("".equals(password)) {
                passwordWrapper.setError(getString(R.string.password_empty));
            } else {
                loginPresenter.loginVerification(account, password);
            }
        }
    }

    @Override
    public void showProgress() {
        //TODO: 改成仿知乎登陆的效果，自定义button
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(String msg) {
        showToast(msg);
    }

}
