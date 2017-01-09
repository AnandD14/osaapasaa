package com.lingme.anand.lingme.Activity.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lingme.anand.lingme.R;

/**
 * Created by nepal on 12/01/2016.
 */
public class UnderConstructionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.under_construction, container, false);
        return view;
    }
}
