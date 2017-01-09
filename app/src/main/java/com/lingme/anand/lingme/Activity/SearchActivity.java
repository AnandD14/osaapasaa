package com.lingme.anand.lingme.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import com.lingme.anand.lingme.Activity.Adapters.CustomAdapter;
import com.lingme.anand.lingme.Activity.Fragments.SearchFragment;
import com.lingme.anand.lingme.R;

/**
 * Created by nepal on 31/03/2016.
 */
public class SearchActivity extends AppCompatActivity {
    SearchView mSearchView;
    Toolbar toolbar;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        toolbar = (Toolbar) findViewById(R.id.rl_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView imageView = (ImageView) findViewById(R.id.arrow);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("Search Here");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SearchFragment fragment = new SearchFragment();
                    Bundle args = new Bundle();
                    args.putString("value", "isEmpty");
                    fragment.setArguments(args);
                    fragmentTransaction.replace(R.id.search_fragment, fragment, SearchFragment.class.getName());
                    fragmentTransaction.commit();
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SearchFragment fragment = new SearchFragment();
                    Bundle args = new Bundle();
                    args.putString("value", newText);
                    fragment.setArguments(args);
                    fragmentTransaction.replace(R.id.search_fragment, fragment, SearchFragment.class.getName());
                    fragmentTransaction.commit();
                }
                return false;
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("value", "isEmpty");
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.search_fragment, fragment, SearchFragment.class.getName());
        fragmentTransaction.commit();
    }
}
