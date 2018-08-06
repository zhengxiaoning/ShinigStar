package com.example.kobe.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * Created by Steven on 2016/5/13.
 */
public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            Activity activity = null;
            try {
                activity = activityStack.lastElement();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
            return activity;
        } else {
            return null;
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack != null) {
            Activity activity = activityStack.lastElement();
            if (activity != null) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除这个cls类activity之前的activity
     *
     * @param cls
     */
    public void removeActivityBefore(Class<?> cls) {
        int clsPosition = -1;
        if (activityStack != null) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    clsPosition = i;
                    break;
                }
            }
            if (clsPosition != -1) {
                for (int i = activityStack.size() - 1; i > clsPosition; i--) {
                    activityStack.get(i).finish();
                    activityStack.remove(i);
                }
            }
        }
    }

    /**
     * 移除这个cls类activity之后的activity
     *
     * @param cls
     */
    public void removeActivityAfter(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        int clsPosition = -1;
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                clsPosition = i;
                break;
            }
        }
        if (clsPosition != -1) {
            for (int i = 0; i < clsPosition; i++) {
                activityStack.get(0).finish();
                activityStack.remove(0);
            }
        }
    }

    /**
     * 在当前activity之前还有这样的activity则销毁他！！！
     *
     * @param cls 需要销毁
     */
    public void removeRepeatActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size() - 1; i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    finishActivity(activityStack.get(i));
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 查找指定Activity
     *
     * @param cls
     * @return
     */
    public boolean findActivity(Class<?> cls) {
        boolean has = false;
        if (activityStack != null && activityStack.size() > 0) {
            for (int i = activityStack.size() - 1; i >= 0; i--) {
                if (activityStack.get(i) != null && activityStack.get(i).getClass().equals(cls)) {
                    has = true;
                    break;
                }
            }
        }
        return has;
    }

}
