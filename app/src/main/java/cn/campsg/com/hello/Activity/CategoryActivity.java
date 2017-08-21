package cn.campsg.com.hello.Activity;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import cn.campsg.com.hello.Fragment.ListFragment;
import cn.campsg.com.hello.R;
public class CategoryActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager mViewPager;
    ListFragment mFragment1;
    ListFragment mFragment2;
    PagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        //设置状态栏颜色
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        if (savedInstanceState == null) {
            mFragment1 = new ListFragment();
            mFragment1.initData('a', 'z');
            mFragment2 = new ListFragment();
            mFragment2.initData('A', 'Z');
        }

        //mPagerAdapter = new PagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(mViewPager);
    }



}
