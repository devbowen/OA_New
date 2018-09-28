package com.even.newoa.ui.customView;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class ProgressButton extends AppCompatButton {

    private static final String TAG = "ProgressButton";
    private ProgressDrawable drawable;
    private float textSize;
    private int textSizeInt;

    public ProgressButton(Context context) {
        super(context);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textSize = getTextSize();
        drawable = new ProgressDrawable(textSize);
        //textSizeInt = Math.round(textSize);
    }

    public void startLoad() {

//        drawable.setBounds(80,textSizeInt,textSizeInt,textSizeInt);
//        setCompoundDrawables(drawable, null, null, null);
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setCompoundDrawablePadding(15);
        drawable.startRotate();
        setClickable(false);
    }

    public void loadSuccess() {
        drawable.showSuccess();
    }

    public void loadFail() {
        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        setCompoundDrawablePadding(15);
        drawable.showFail();
    }

    public void stopLoad() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        setClickable(true);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        drawable.stopRotate();
    }

}
