package com.even.newoa.base;

/**
 * Presenter基类
 *
 * @author Even
 * @date 2018-08-24 17:05
 */

public class BasePresenter<V extends BaseView> {


    /**
     * 绑定的view
     */
    private V view;

    /**
     * 绑定view，一般在初始化中调用该方法
     */
    public void attachView(V view) {
        this.view = view;
    }

    /**
     * 断开view，一般在onDestroy中调用
     */
    public void detachView() {
        this.view = null;
    }

    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached() {
        return view != null;
    }

    /**
     * 获取连接的view
     */
    public V getView() {
        return view;
    }

}
