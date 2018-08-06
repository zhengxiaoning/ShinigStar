package com.example.kobe.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import java.util.ArrayList;
import java.util.List;


public class ShiningStar extends View {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int tmp=(width-height)/2;
    //星星的坐标及大小
    private  final int[][] starPosition = new int[][] {
            {tmp,80,80}, {tmp+300,600,66},{tmp+180,200,77},{tmp,400,100},{tmp+180,688,105},
            {width/2,height/4,88},{width/2,700,90},{width/2,60,66},
            {width-tmp,80,110}, {width-(tmp+100),500,66},{width-(tmp+180),300,77},{width-tmp,660,80}

    };
    //星星存储器
    private List<Star> stars = new ArrayList<Star>();
    //星星资源
    private Bitmap bitmap = null;
    //画笔
    private Paint paint = null;

    public ShiningStar(Context context) {
        super(context);
        init();
    }

    public ShiningStar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShiningStar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            for (int i = 0; i < stars.size(); i++) {
                canvas.save();//这样使每一个星星状态独立
                Rect dst = new Rect(stars.get(i).x, stars.get(i).y, stars.get(i).x + stars.get(i).size, stars.get(i).y + stars.get(i).size);
                canvas.scale(stars.get(i).scale, stars.get(i).scale, stars.get(i).x + stars.get(i).size / 2, stars.get(i).y + stars.get(i).size / 2);
                paint.setAlpha((int) stars.get(i).alpha);
                canvas.drawBitmap(bitmap, null, dst, paint);
                canvas.restore();
            }
        }
    }

    //初始化
    private void init() {
        initStars();
        initAnimation();
    }

    //初始化星星对象
    private void initStars() {
        for (int i = 0; i < starPosition.length; i++) {
            final Star star = new Star();
            star.x = starPosition[i][0];
            star.y = starPosition[i][1];
            star.size = starPosition[i][2];
            star.scale = 1;
            star.alpha = 255;
            stars.add(star);
        }
    }

    //初始化动画及绘制元素的对象
    private void initAnimation() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.star);
        paint = new Paint();
        paint.setAlpha(255);
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(0, 250, 0);
        scaleAnim.setInterpolator(new LinearInterpolator());
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                boolean flag = false;
                for (int i = 0; i < stars.size(); i++) {
                    if (flag) {
                        stars.get(i).scale = ((float) animation.getAnimatedValue()) / 250;
                        stars.get(i).alpha = (float) animation.getAnimatedValue();
                    } else {
                        stars.get(i).scale = 1 - ((float) animation.getAnimatedValue()) / 250;
                        stars.get(i).alpha = 255 - (float) animation.getAnimatedValue();
                    }
                    flag = !flag;
                }
                postInvalidate();
            }
        });
        scaleAnim.start();
    }

    class Star {
        int x;
        int y;
        int size;
        float scale;
        float alpha;
    }
}
