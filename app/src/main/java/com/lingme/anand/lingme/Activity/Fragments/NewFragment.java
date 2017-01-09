package com.lingme.anand.lingme.Activity.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.Home;
import com.lingme.anand.lingme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 30/10/2015.
 */
public class NewFragment extends Fragment {
NetworkImageView networkImageView;
    Home homeList;
    int position;

    public static NewFragment newInstance(Home homeList,int position){
        NewFragment newFragment=new NewFragment();
        newFragment.homeList=homeList;
        newFragment.position=position;
        return newFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_pager,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        networkImageView=(NetworkImageView)view.findViewById(R.id.imageView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
        networkImageView.setImageUrl(homeList.getNewThumbnail(),imageLoader);
}
}
