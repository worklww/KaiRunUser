package cn.kairun.kairunuser.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import cn.kairun.kairunuser.R;
import cn.kairun.kairunuser.adapter.FragAdapter;
import cn.kairun.kairunuser.fragment.SleepFragment;
import cn.kairun.kairunuser.fragment.SportFragment;

public class IndexActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener ,ViewPager.OnPageChangeListener{
    private DrawerLayout drawer;
    private NavigationView navigationViewLeft;
    private NavigationView navigationViewRight;
    private ImageView iv_bracelet;
    private ImageView iv_own;
    List fragmentList;
    SportFragment sportFragment;
    SleepFragment sleepFragment;
    ViewPager viewPager;
    private ImageView[] points;   //底部小点的图片
    private int currentIndex;     //记录当前选中位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }

    public void initView(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_bracelet = (ImageView)findViewById(R.id.iv_bracelet);
        iv_own = (ImageView)findViewById(R.id.iv_own);
        navigationViewLeft = (NavigationView) findViewById(R.id.nav_left);
        navigationViewRight = (NavigationView) findViewById(R.id.nav_right);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        iv_bracelet.setOnClickListener(this);
        iv_own.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            //菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
            // 菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        fragmentList = new ArrayList<Fragment>();
        sportFragment = new SportFragment();
        sleepFragment = new SleepFragment();
        fragmentList.add(sportFragment);
        fragmentList.add(sleepFragment);
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        initPoint();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //左边菜单开关事件
    public void openLeftLayout() {
        if (drawer.isDrawerOpen(navigationViewLeft)) {
            drawer.closeDrawer(navigationViewLeft);
        } else {
            drawer.openDrawer(navigationViewLeft);
        }
    }

    // 右边菜单开关事件

    public void openRightLayout() {
        if (drawer.isDrawerOpen(navigationViewRight)) {
            drawer.closeDrawer(navigationViewRight);
        } else {
            drawer.openDrawer(navigationViewRight);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bracelet:
                openLeftLayout();
                break;
            case R.id.iv_own:
                openRightLayout();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        setCurDot(position);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 初始化底部小点
     */
    private void initPoint(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llayout_dot);
        points = new ImageView[2];
        //循环取得小点图片
        for (int i = 0; i < points.length; i++) {
            //得到一个LinearLayout下面的每一个子元素
            points[i] = (ImageView) linearLayout.getChildAt(i);
            //默认都设为灰色
            points[i].setEnabled(true);
            //给每个小点设置监听
            points[i].setOnClickListener(this);
            //设置位置tag，方便取出与当前位置对应
            points[i].setTag(i);
        }

        //设置当面默认的位置
        currentIndex = 0;
        //设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
    }

    /**
     * 设置当前页面的位置
     */
    private void setCurView(int position){
        if (position < 0 || position >= points.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon){
        if (positon < 0 || positon > points.length - 1 || currentIndex == positon) {
            return;
        }
        points[positon].setEnabled(false);
        points[currentIndex].setEnabled(true);

        currentIndex = positon;
    }
}
