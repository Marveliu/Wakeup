package imis.demo.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;

import imis.demo.R;
import imis.demo.config.Const;
import imis.demo.service.LocationService;
import imis.demo.ui.about.AboutActivity;
import imis.demo.ui.alarmclock.AlarmClock;
import imis.demo.ui.location.LocMainActivity;
import imis.demo.ui.weather.WeatherActivity;
import imis.demo.util.DialogUtil;
import imis.demo.util.PreferencesUtils;
import imis.demo.util.ShareUtils;

public class MainActivity extends TabActivity
        implements NavigationView.OnNavigationItemSelectedListener,CompoundButton.OnCheckedChangeListener {

    private TabHost mTabHost,mainTabHost;
    private RadioButton rb_tab1, rb_tab2,rb_tab3;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //set(toolbar);

        //侧滑
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //启动后台定位服务
        startService(new Intent(this,LocationService.class));//启动后台服务

        setupIntent();
    }


    //侧滑
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //分享设置
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                ShareUtils.share(MainActivity.this, getString(R.string.share_app));
                break;
        }
        return true;
    }

    //设置页面跳转
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.rb_tab1) {
            mTabHost.setCurrentTabByTag("tab1");
        }
        else if (id == R.id.rb_tab2) {
            mTabHost.setCurrentTabByTag("tab2");
        }
        else  if (id == R.id.rb_tab3) {
            mTabHost.setCurrentTabByTag("tab3");
        }
        else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about) {
            Intent insertIntent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(insertIntent);
        } else if (id == R.id.nav_quit) {
            DialogUtil.showChooseDialog(this, "", "您确定退出吗？", null, null, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PreferencesUtils.putSharePre(MainActivity.this, Const.LOGIN_PWD,"");
                    getParent().finish();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
//    主要功能的切换
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_tab1:
                    mTabHost.setCurrentTabByTag("tab1");
                    break;
                case R.id.rb_tab2:
                    mTabHost.setCurrentTabByTag("tab2");
                    break;
                case R.id.rb_tab3:
                    mTabHost.setCurrentTabByTag("tab3");
                    break;
            }
        }
    }

    //初始化选项卡
    private void setupIntent() {
        mTabHost = getTabHost();
        mainTabHost = this.mTabHost;
        intent = new Intent().setClass(this, AlarmClock.class);
        mainTabHost.addTab(buildTabSpec("tab1", null, intent));

        intent = new Intent().setClass(this, LocMainActivity.class);
        mainTabHost.addTab(buildTabSpec("tab2", null, intent));

        intent = new Intent().setClass(this, WeatherActivity.class);
        mainTabHost.addTab(buildTabSpec("tab3", null, intent));

    }

    private TabHost.TabSpec buildTabSpec(String tag, String label, final Intent content) {
        return this.mTabHost.newTabSpec(tag).setIndicator(label).setContent(content);
    }
}
