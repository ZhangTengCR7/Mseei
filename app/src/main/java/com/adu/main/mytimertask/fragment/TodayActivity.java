package com.adu.main.mytimertask.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.adu.main.mytimertask.R;
import com.adu.main.mytimertask.adapter.SimpleFragmentPagerAdapter;


/**
 * Created by dell on 2016/5/27.
 * 今日头条
 */
public class TodayActivity extends FragmentActivity
{
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout);

        initView();
    }

    private void initView()
    {


        viewPager= (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter=new SimpleFragmentPagerAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(pagerAdapter);

        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
