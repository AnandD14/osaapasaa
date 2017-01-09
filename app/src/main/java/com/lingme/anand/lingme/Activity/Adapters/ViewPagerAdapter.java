package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.lingme.anand.lingme.Activity.Fragments.NewFragment;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.Home;
import com.lingme.anand.lingme.R;

import java.util.List;


/**
 * Created by nepal on 18/07/2015.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Home> listItems;

    public ViewPagerAdapter(FragmentManager fm,List<Home> listItems) {
        super(fm);
        this.listItems=listItems;
    }


    @Override
    public int getCount() {
       return 4;
    }

    @Override
    public Fragment getItem(int position) {
        return NewFragment.newInstance(listItems.get(position),position);
    }


}

