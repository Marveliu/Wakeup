package imis.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import imis.demo.R;
import imis.demo.config.Const;
import imis.demo.ui.login.LoginActivity;
import imis.demo.util.PreferencesUtils;
import imis.demo.util.SysUtils;
import imis.demo.util.SystemBarTintManager;
import imis.demo.util.ToastUtil;


/**
 * Created by Administrator on 2016/12/27.
 */
public class WelcomeActivity extends Activity {

    String versioncode = "";//当前版本号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //防止点击home键，再点击APP图标时应用重新启动
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        versioncode = SysUtils.getVersionCode(this);//获得当前APP版本号
        initFile();
        initData(1);
    }


    /**
     * 初始化文件夹
     */
    public void initFile() {
        if (SysUtils.extraUse()) {
            SysUtils.initFiles();
        } else {
            ToastUtil.showToast(this, "请安装存储卡");
        }
    }

    private void initData(int delay_s) {
        //如果版本更新时更新了引导图，则将Const.VERSION_CODE名改变即可,没有更新则不改变
        String versionCode = PreferencesUtils.getSharePreStr(WelcomeActivity.this, Const.VERSION_CODE);
        //两种情况需要显示引导页
        //1.如果保存的版本号为空（新安装的）；
        //2.当前版本号与保存的版本号不同（升级）；
        if (TextUtils.isEmpty(versionCode)) {
            Intent intent = new Intent(WelcomeActivity.this, GuidActivity.class);
            startActivity(intent);
            finish();
        } else {
           //显示欢迎页，3秒后进入主页
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (SysUtils.isLogin(WelcomeActivity.this)) {
                        SysUtils.startActivity(WelcomeActivity.this, MainActivity.class);//已登录进入首页
                    } else {
                        SysUtils.startActivity(WelcomeActivity.this, LoginActivity.class);//未登录则进入登录
                    }
                    finish();
                }
            }, delay_s * 1000);
        }

    }


    /**
     * 返回键监听,禁止返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}