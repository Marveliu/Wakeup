package imis.demo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import imis.demo.util.ACache;
import imis.demo.util.LogUtil;

import im.fir.sdk.FIR;

/**
 * Created by Administrator on 2016/12/30.
 */
public class MyApp extends Application {

    public static final String CACHE_NAME = "Wakeup_Cache";
    private static Handler mHandler;
    private static int mainId;
    private static MyApp mInstance;
    private static ACache mACache;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mainId = android.os.Process.myTid();
        mHandler = new Handler();
        mACache = ACache.get(this, CACHE_NAME);
        //release版本不打印log
        LogUtil.isDebug = true;
        //新版本检查更新。

//        FIR bughd_android_sdk貌似是查bug的东西
        FIR.init(this);
    }

    /**
     * 返回application对象
     */
    public static Context getApp() {
        return mInstance;
    }
    /**
     * 获取主线程的id
     */
    public static int getMainId() {
        return mainId;
    }

    /**
     * 获取handler对象
     */
    public static Handler getHandler() {
        return mHandler;
    }

    public static ACache getACache() {
        return mACache;
    }
}
