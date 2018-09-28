package com.even.newoa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;

import com.even.newoa.R;
import com.even.newoa.base.BaseActivity;
import com.even.newoa.base.contract.login.LoginContract;
import com.even.newoa.presenter.LoginPresenter;
import com.even.newoa.ui.customView.ProgressButton;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView, View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private ProgressButton loginBtn;
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
            startMainActivity();
        }

        setContentView(R.layout.login_activity);
        super.onCreate(savedInstanceState);

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
                loginBtn.startLoad();
                loginPresenter.loginVerification(account, password);
            }
        }
    }

    @Override
    public void loginSuccess() {
        loginBtn.stopLoad();
        loginBtn.loadSuccess();
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(String msg) {
        loginBtn.stopLoad();
        loginBtn.loadFail();
        showToast(msg);
    }

}
