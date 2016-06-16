package com.adu.main.mytimertask.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.adu.main.mytimertask.fragment.PageFragment;

/**
 * Created by dell on 2016/5/27.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter
{

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"tab1","tab2","tab3","tab4"};
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm,Context context)
    {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position)
    {
        return PageFragment.newInstance(position+1);
    }

    @Override
    public int getCount()
    {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
