package com.even.newoa.base;

/**
 * View基类
 *
 * @author Even
 * @date 2018-08-24 17:04
 */

public interface BaseView {
    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 显示请求错误提示
     */
    void showErr();

}

