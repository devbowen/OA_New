package com.even.newoa.base;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Even
 * @date 2018-08-24 17:26
 */

public class BaseActivity extends AppCompatActivity implements BaseView {

    private static final String TAG = "BaseActivity";
    private static final int REQUEST_CODE = 1;
    private List<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
            Log.d(TAG, "onCreate: requestPermissions");
        }
    }

    private void requestPermissions() {
        permissionList.clear();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            Log.d(TAG, "requestPermissions: 有未同意权限" + permissionList.toArray(new String[permissionList.size()]).toString());
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0) {
                    //通过循环将申请的每个权限进行判断，如果有任何一个权限被拒绝，则调用finish()方法关闭当前程序
                    for (int i = 0; i < grantResults.length; ++i) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                            // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {

                                //解释原因，并且引导用户至设置页手动授权
                                new AlertDialog.Builder(this)
                                        .setMessage("【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。" +
                                                "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】\r\n" +
                                                "获取权限失败,将导致部分功能无法正常使用，需要到设置页面手动授权")
                                        .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //引导用户至设置页手动授权
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                                intent.setData(uri);

                                                startActivityForResult(intent, 7);
                                                //startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                showToast("必须同意所有权限才能使用本程序");
                                                finish();
                                                return;
                                            }
                                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        showToast("onCancel");
                                    }
                                }).setCancelable(false)
                                        .show();

                            } else {
                                //权限请求失败，但未选中“不再提示”选项
                                showToast("必须同意所有权限才能使用本程序");
                                finish();
                                return;
                            }
                        }
                    }
                    //当所有权限都被用户同意
                } else {
                    showToast("发生未知错误");
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7) {
            requestPermissions();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        //showToast(getResources().getString(R.string.api_error_msg));
    }
}
