package com.example.kobe.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CommentsAndFeedbackActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView cloudComments;
    private TextView teacherComments;
    private TextView courseComments;
    private TextView otherComments;
    private EditText contents;
    private Button loadPic;
    private PopupWindow mPopupWindow;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private LinearLayout imgLinearLayout;
    private boolean img1IsShow;
    private boolean img2IsShow;
    private boolean img3IsShow;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.big_activity_comments_and_feedback);
        cloudComments = findViewById(R.id.cloudComments);
        teacherComments = findViewById(R.id.teacherComments);
        courseComments = findViewById(R.id.courseComments);
        otherComments = findViewById(R.id.otherComments);
        contents = findViewById(R.id.contents);
        loadPic = findViewById(R.id.loadPic);
        imageView1 = findViewById(R.id.imgShow1);
        imageView2 = findViewById(R.id.imgShow2);
        imageView3 = findViewById(R.id.imgShow3);
        imgLinearLayout=findViewById(R.id.imgLinearlayout);
        contents.setHint("#" + cloudComments.getText() + "#" + "您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。");
        cloudComments.setOnClickListener(this);
        teacherComments.setOnClickListener(this);
        courseComments.setOnClickListener(this);
        otherComments.setOnClickListener(this);
        loadPic.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        img1IsShow = false;
        img2IsShow = false;
        img3IsShow = false;


    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cloudComments:
                if (contents.getText() != null && contents.getText().length() > 0) {
                    contents.getText().clear();
                }
                contents.setHint("#" + cloudComments.getText() + "#" + "您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。");
                //contents.setHint(Html.fromHtml("<span style='color:#222222'>#给云学习app提建议#<span> "));
                break;
            case R.id.teacherComments:
                if (contents.getText() != null && contents.getText().length() > 0) {
                    contents.getText().clear();
                }
                contents.setHint("#" + teacherComments.getText() + "#" + "您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。");
                break;
            case R.id.courseComments:
                if (contents.getText() != null && contents.getText().length() > 0) {
                    contents.getText().clear();
                }
                contents.setHint("#" + courseComments.getText() + "#" + "您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。");
                break;
            case R.id.otherComments:
                if (contents.getText() != null && contents.getText().length() > 0) {
                    contents.getText().clear();
                }
                contents.setHint("#" + otherComments.getText() + "#" + "您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。");
                break;
            case R.id.loadPic:
                showPopupWindow();
                break;
            case R.id.select_from_phone:
                choosePhone();
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
                imgLinearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.cancel:
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
                if(imageView1==null) {
                    imgLinearLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.imgShow1:
                if(imageView1!=null){
                    imageView1.setImageResource(R.drawable.load_layout_shape);
                    img1IsShow=false;
                }
                break;
            case R.id.imgShow2:
                if(imageView2!=null){
                   imageView2.setImageResource(R.drawable.load_layout_shape);
                    img2IsShow=false;
                }
                break;
            case R.id.imgShow3:
                if(imageView3!=null){
                    imageView3.setImageResource(R.drawable.load_layout_shape);
                    img3IsShow=false;
                }
                break;
        }

    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(CommentsAndFeedbackActivity.this).inflate(R.layout.tempcomments_item_layout, null);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setContentView(contentView);
        //显示mPopupWindow
        View rootview = LayoutInflater.from(CommentsAndFeedbackActivity.this).inflate(R.layout.activity_comments_and_feedback, null);
        // backgroundAlpha(1f);
        mPopupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);
        //设置各控件的点击响应
        TextView select = contentView.findViewById(R.id.select_from_phone);
        TextView cancel = contentView.findViewById(R.id.cancel);
        select.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
                if(imageView1==null) {
                    imgLinearLayout.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    public void choosePhone() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        } else {
            choosePhoto();
        }
    }

    /**
     * 从相册选取图片
     */
    void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    private Bitmap roundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if (bitmap == null) {
            throw new NullPointerException("Bitmap can't be null");
        }
        //初始化缩放比
        float widthScale = (outWidth * 1.0f) / bitmap.getWidth();
        float heightScale = (outHeight * 1.0f) / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        //初始化绘制纹理图
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //根据控件大小对纹理图进行拉伸缩放处理
        bitmapShader.setLocalMatrix(matrix);
        //初始化目标bitmap
        Bitmap targetBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        //初始化目标画布
        Canvas targetCanvas = new Canvas(targetBitmap);
        //初始化画笔
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        //利用画笔将纹理图绘制到画布上面
        targetCanvas.drawRoundRect(new RectF(0, 0, outWidth, outHeight), radius, radius, paint);
        return targetBitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    /**
                     * 该uri是上一个Activity返回的
                     */
                    Uri uri = data.getData();
                    Bitmap bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    if (!img1IsShow) {
                        imageView1.setImageBitmap(roundBitmapByShader(bitmap1, dip2px(73), dip2px(73), 30));
                        img1IsShow = true;
                    } else if (!img2IsShow) {
                        imageView2.setImageBitmap(roundBitmapByShader(bitmap1, dip2px(73), dip2px(73), 30));
                        img2IsShow = true;
                    } else if (!img3IsShow) {
                        imageView3.setImageBitmap(roundBitmapByShader(bitmap1, dip2px(73), dip2px(73), 30));
                        img3IsShow = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag", e.getMessage());
                    Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                // Permission Denied
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
