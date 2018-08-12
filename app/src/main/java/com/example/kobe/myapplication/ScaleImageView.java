//package com.example.kobe.myapplication;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.provider.MediaStore;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Author by kobe, Email 995270893@qq.com, Date on 2018/8/8.
// */
//public class ScaleImageView {
//    private static final byte FILES = 1;
//    private byte status;//用来表示当前大图查看器的状态
//    private Activity activity;
//    private List <File>  files;//本地查看状态中传入的要查看的图片对应的file对象的List
//    private Dialog dialog;          //用于承载整个大图查看器的Dialog
//    private ImageView delete;//删除图片的按钮
//    private TextView imageCount;//用于显示当前正在查看第几张图片的TextView
//    private List<View> views;//ViewPager适配器的数据源
//    private OnDeleteItemListener listener;
//    public ScaleImageView(Activity activity) {
//        this.activity = activity;
//        init();
//    }
//    public void setFiles(List<File> files) {
//        if (this.files == null) {
//            this.files = new LinkedList<>();
//        } else {
//            this.files.clear();
//        }
//        this.files.addAll(files);
//        status = FILES;
//    }
//
//    public void setOnDeleteItemListener(OnDeleteItemListener listener) {
//        this.listener = listener;
//    }
//    private void init() {
//        RelativeLayout relativeLayout = (RelativeLayout) activity.getLayoutInflater().inflate(R.layout.dialog_scale_image, null);
//        ImageView close =  relativeLayout.findViewById(R.id.scale_image_close);
//        delete =  relativeLayout.findViewById(R.id.scale_image_delete);
//        imageCount = relativeLayout.findViewById(R.id.scale_image_count);
//        dialog = new Dialog(activity, R.style.Dialog_Fullscreen);
//        dialog.setContentView(relativeLayout);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
//
//    public interface OnDeleteItemListener {
//        void onDelete(int position);
//    }
//
//}
//
