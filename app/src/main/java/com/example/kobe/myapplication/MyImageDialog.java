package com.example.kobe.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Author by kobe, Email 995270893@qq.com, Date on 2018/8/8.
 */

public class MyImageDialog extends Dialog {
    private ImageView iv;
    private Bitmap bms;
    public MyImageDialog(Context context, Bitmap bm) {
        super(context,R.style.dialog);
        bms = bm;
    }
    protected void onCreate(Bundle savedInstanceState) {
        //初始化布局
        View loadingview= LayoutInflater.from(getContext()).inflate(R.layout.imagedialogview,null);
        iv=loadingview.findViewById(R.id.imageview_head_big);
        iv.setImageBitmap(bms);
        //设置dialog的布局
        setContentView(loadingview);
        //如果需要放大或者缩小时的动画，可以直接在此出对loadingview或iv操作，在下面SHOW或者dismiss中操作
        super.onCreate(savedInstanceState);
    }
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }
    public void dismiss() {
        super.dismiss();
    }
}

