package com.example.kobe.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.json.JSONObject;

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
    private TextView commit;
    private LinearLayout imgLinearLayout;
    private boolean img1IsShow;
    private boolean img2IsShow;
    private boolean img3IsShow;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private Bitmap bitmap1;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private int position;
    public static final int PHOTO = 1;
    public static final int IMG = 2;
    public int type = 1;
    private List<String> localImgPaths = new ArrayList<>();
    private String resultPic = "";
    private int aliPosition;
    private int maxSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.big_activity_comments_and_feedback);
        cloudComments = (TextView) findViewById(R.id.cloudComments);
        teacherComments = (TextView) findViewById(R.id.teacherComments);
        courseComments = (TextView) findViewById(R.id.courseComments);
        otherComments = (TextView) findViewById(R.id.otherComments);
        contents = (EditText) findViewById(R.id.contents);
        loadPic = (Button) findViewById(R.id.loadPic);
        imageView1 = (ImageView) findViewById(R.id.imgShow1);
        imageView2 = (ImageView) findViewById(R.id.imgShow2);
        imageView3 = (ImageView) findViewById(R.id.imgShow3);
        imgLinearLayout = (LinearLayout) findViewById(R.id.imgLinearlayout);
        commit = (TextView) findViewById(R.id.commit);
        String textCloud = "<font color=\"#222222\">" + "#给云学习app提建议#" + "</font>您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。";
        contents.setHint(Html.fromHtml(textCloud));
        cloudComments.setOnClickListener(this);
        teacherComments.setOnClickListener(this);
        courseComments.setOnClickListener(this);
        otherComments.setOnClickListener(this);
        loadPic.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        commit.setOnClickListener(this);
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
                type = 1;
                if (!contents.getText().toString().equals("null")) {
                    contents.getText().clear();
                }
                String textCloud = "<font color=\"#222222\">" + "#给云学习app提建议#" + "</font>您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。";
                contents.setHint(Html.fromHtml(textCloud));
                break;
            case R.id.teacherComments:
                type = 2;
                if (!contents.getText().toString().equals("null")) {
                    contents.getText().clear();
                }
                String textTeacher = "<font color=\"#222222\">" + "#给老师提建议#" + "</font>您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。";
                contents.setHint(Html.fromHtml(textTeacher));
                break;
            case R.id.courseComments:
                type = 3;
                if (!contents.getText().toString().equals("null")) {
                    contents.getText().clear();
                }
                String textCourse = "<font color=\"#222222\">" + "#给教学内容提建议#" + "</font>您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。";
                contents.setHint(Html.fromHtml(textCourse));
                break;
            case R.id.otherComments:
                type = 4;
                if (!contents.getText().toString().equals("null")) {
                    contents.getText().clear();
                }
                String textOther = "<font color=\"#222222\">" + "#其他建议#" + "</font>您可以在这里留下您的问题或者建议，也可以通过右侧“上传截图”方便我们快速了解问题。";
                contents.setHint(Html.fromHtml(textOther));
                break;
            case R.id.loadPic:
                if (bitmap1 != null && bitmap2 != null && bitmap3 != null) {
                    Toast.makeText(CommentsAndFeedbackActivity.this, "只允许加载三张截图", Toast.LENGTH_SHORT).show();
                } else {
                    showPopupWindow(PHOTO);
                }
                break;
            case R.id.select_from_phone:
                choosePhone();
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
                break;
            case R.id.cancel:
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
                if (imageView1 == null) {
                    imgLinearLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.commit:
                if (contents.getText().toString().equals("")) {
                    Toast.makeText(CommentsAndFeedbackActivity.this, "提交失败,请填写问题与建议", Toast.LENGTH_SHORT).show();
                } else {
//                    aliPosition = 0;
//                    maxSize = localImgPaths.size();
//                    resultPic = "";
//                    localPic2Ali();
                    Toast.makeText(CommentsAndFeedbackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.imgShow1:
                if (bitmap1 != null) {
                    showPopupWindow(IMG);
                    position = 1;
                }
                break;
            case R.id.imgShow2:
                if (bitmap2 != null) {
                    showPopupWindow(IMG);
                    position = 2;
                }
                break;
            case R.id.imgShow3:
                if (bitmap3 != null) {
                    showPopupWindow(IMG);
                    position = 3;
                }
                break;
            case R.id.see:
                if (bitmap1 != null && position == 1) {
                    final MyImageDialog myImageDialog1 = new MyImageDialog(CommentsAndFeedbackActivity.this, bitmap1);
                    myImageDialog1.show();
                    myImageDialog1.getWindow().findViewById(R.id.myDialog).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myImageDialog1.dismiss();
                        }
                    });
                } else if (bitmap2 != null && position == 2) {
                    final MyImageDialog myImageDialog2 = new MyImageDialog(CommentsAndFeedbackActivity.this, bitmap2);
                    myImageDialog2.show();
                    myImageDialog2.getWindow().findViewById(R.id.myDialog).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myImageDialog2.dismiss();
                        }
                    });
                } else if (bitmap3 != null && position == 3) {
                    final MyImageDialog myImageDialog3 = new MyImageDialog(CommentsAndFeedbackActivity.this, bitmap3);
                    myImageDialog3.show();
                    myImageDialog3.getWindow().findViewById(R.id.myDialog).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myImageDialog3.dismiss();
                        }
                    });
                }
                mPopupWindow.dismiss();
                break;
            case R.id.del:
                delPic(position);
                mPopupWindow.dismiss();
                break;


        }
    }

    private void delPic(int position) {
        if (position == 1) {//点击第一张图片 删除
            if (bitmap1 != null && bitmap2 == null && bitmap3 == null) { //只有第一张
                bitmap1=null;
                imageView1.setVisibility(View.INVISIBLE);
                img1IsShow=false;
            } else {  //两张和三张
                if (bitmap1 != null && bitmap2 != null && bitmap3 == null) {//第一张，第二张;为第二张，第三张。
                    bitmap1=bitmap2;
                    imageView1.setImageBitmap(roundBitmapByShader(bitmap1, dip2px(73), dip2px(73), 30));
                    bitmap2=null;
                    imageView2.setVisibility(View.INVISIBLE);
                    img2IsShow=false;

                } else {  //三张全有   两种情况：删除后剩下两张；删除后只剩下一张
                    bitmap1=bitmap2;
                    imageView1.setImageBitmap(roundBitmapByShader(bitmap1, dip2px(73), dip2px(73), 30));
                    bitmap2=null;
                    bitmap2=bitmap3;
                    imageView2.setImageBitmap(roundBitmapByShader(bitmap2, dip2px(73), dip2px(73), 30));
                    bitmap3=null;
                    imageView3.setVisibility(View.INVISIBLE);
                    img3IsShow=false;
                }
            }

        }else if(position==2){//点击第二张图片 删除
            //有第一张和第二张；有第一张和第二张和第三张
            if(bitmap1!=null&&bitmap2!=null&&bitmap3==null){
                bitmap2=null;
                img2IsShow=false;
                imageView2.setVisibility(View.INVISIBLE);
            }else {
                bitmap2=bitmap3;
                imageView2.setImageBitmap(roundBitmapByShader(bitmap2, dip2px(73), dip2px(73), 30));
                bitmap3=null;
                imageView3.setVisibility(View.INVISIBLE);
                img3IsShow=false;
            }
        }else if(position==3){
            bitmap3=null;
            imageView3.setVisibility(View.INVISIBLE);
            img3IsShow=false;
        }

    }

//    private void commit2Server() {
//        RequestParams requestParams = new RequestParams();
//        String url = Urls.online_feedback;
//        int netState = HttpUtils.getNetworkState();
//        String nettype = "";
//        switch (netState) {
//            case HttpUtils.NETWORN_NONE:
//                nettype = "not connect";
//                break;
//            case HttpUtils.NETWORN_WIFI:
//                nettype = "wifi";
//                break;
//            case HttpUtils.NETWORN_MOBILE:
//                nettype = "4g";
//                break;
//            default:
//                nettype = "unkown";
//                break;
//        }
//        requestParams.put("name", AccountManager.getInstance().getCurrentChild().getEn_name());
//        requestParams.put("tele", AccountManager.getInstance().getParentLoginInfo().getTele());
//        requestParams.put("stage", firstleap.library.application.BaseApplication.stage);
//        requestParams.put("type", type);
//        requestParams.put("device", BaseApplication.phoneName);
//        requestParams.put("network", nettype);
//        requestParams.put("version", BuildConfig.VERSION_NAME);
//        if (contents.getText() != null && contents.getText().length() > 0) {
//            requestParams.put("content", contents.getText().toString());
//        } else {
//            requestParams.put("content", "");
//        }
//        requestParams.put("images", resultPic);
//        HttpUtils.getInstance().serverHttpCallBack(this, Constants.POST_REQUEST, url, requestParams, new HttpCallBack() {
//            @Override
//            public void onHttpCallBack(int result, JSONObject data) {
//                Toast.makeText(CommentsAndFeedbackStudentActivity.this, "您已提交成功", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//
//    }
//
    private void showPopupWindow(int showType) {
        if (showType == PHOTO) {
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
            TextView select = (TextView) contentView.findViewById(R.id.select_from_phone);
            TextView cancel = (TextView) contentView.findViewById(R.id.cancel);
            select.setOnClickListener(this);
            cancel.setOnClickListener(this);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                    if (imageView1 == null) {
                        imgLinearLayout.setVisibility(View.GONE);
                    }
                }
            });
        } else if (showType == IMG) {
            //设置contentView
            View contentView = LayoutInflater.from(CommentsAndFeedbackActivity.this).inflate(R.layout.image_item_layout, null);
            mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setContentView(contentView);
            //显示mPopupWindow
            View rootview = LayoutInflater.from(CommentsAndFeedbackActivity.this).inflate(R.layout.activity_comments_and_feedback, null);
            mPopupWindow.setFocusable(true);
            ColorDrawable dw = new ColorDrawable(0x00);
            mPopupWindow.setBackgroundDrawable(dw);
            mPopupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.5f);
            //设置各控件的点击响应
            TextView see = (TextView) contentView.findViewById(R.id.see);
            TextView del = (TextView) contentView.findViewById(R.id.del);
            TextView cancel = (TextView) contentView.findViewById(R.id.cancel);
            see.setOnClickListener(this);
            del.setOnClickListener(this);
            cancel.setOnClickListener(this);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                    if (imageView1 == null) {
                        imgLinearLayout.setVisibility(View.GONE);
                    }
                }
            });
        }

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
//        初始化缩放比
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
                    if (!img1IsShow) {
                        Uri uri = data.getData();
                        localImgPaths.add(uri.getPath());
                        bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        imageView1.setImageBitmap(roundBitmapByShader(bitmap1, dip2px(73), dip2px(73), 30));
                        img1IsShow = true;
                        imageView1.setVisibility(View.VISIBLE);
                    } else if (!img2IsShow) {
                        Uri uri = data.getData();
                        localImgPaths.add(uri.getPath());
                        bitmap2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        imageView2.setImageBitmap(roundBitmapByShader(bitmap2, dip2px(73), dip2px(73), 30));
                        img2IsShow = true;
                        imageView2.setVisibility(View.VISIBLE);
                    } else if (!img3IsShow) {
                        Uri uri = data.getData();
                        localImgPaths.add(uri.getPath());
                        bitmap3 = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        imageView3.setImageBitmap(roundBitmapByShader(bitmap3, dip2px(73), dip2px(73), 30));
                        img3IsShow = true;
                        imageView3.setVisibility(View.VISIBLE);
                    }
                    imgLinearLayout.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag", e.getMessage());
                    Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


//    private void localPic2Ali() {
//        if (maxSize > 0 && localImgPaths != null && localImgPaths.size() > 0) {
//            AliYun.getInstance().startMediaUp(AliYun.IMG, localImgPaths.get(0), new UpCompleteListener() {
//                @Override
//                public void onComplete(boolean isSuccess, String result) {
//                    localImgPaths.remove(0);
//                    if (aliPosition == maxSize - 1) {
//                        resultPic = resultPic + result;
//                    } else {
//                        resultPic = resultPic + result + ",";
//                    }
//                    ++aliPosition;
//                    localPic2Ali();
//                }
//            }, new UpProgressListener() {
//                @Override
//                public void onRequestProgress(long bytesWrite, long contentLength) {
//
//                }
//            });
//        } else {
//            commit2Server();
//        }
//    }

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

    public void onBack(View view) {


        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
