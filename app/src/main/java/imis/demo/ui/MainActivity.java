package imis.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import imis.demo.R;
import imis.demo.config.Const;
import imis.demo.service.LocService;
import imis.demo.ui.about.AboutActivity;
import imis.demo.util.DialogUtil;
import imis.demo.util.PreferencesUtils;
import imis.demo.util.ShareUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //侧滑
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //启动后台定位服务
        startService(new Intent(this,LocService.class));//启动后台服务
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
//                    mDrawer.closeDrawers();
//                } else {
//                    mDrawer.openDrawer(GravityCompat.START);
//                }
                break;
            case R.id.action_share:
                ShareUtils.share(MainActivity.this, getString(R.string.share_app));
                break;
        }
        return true;
    }

    //主菜单页面跳转
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_alarmclock) {

        } else if (id == R.id.nav_note) {
//      跳转到什么位置
        } else if (id == R.id.nav_wheather) {

        } else if (id == R.id.nav_manage) {

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
}
