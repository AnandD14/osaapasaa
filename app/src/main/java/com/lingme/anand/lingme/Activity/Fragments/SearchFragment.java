package com.lingme.anand.lingme.Activity.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lingme.anand.lingme.Activity.Adapters.CustomAdapter;
import com.lingme.anand.lingme.R;

/**
 * Created by nepal on 3/04/2016.
 */
public class SearchFragment extends Fragment {
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String[] list = {"Women", "Men", "New In", "Sales", "Offer"};
    String[] listAll = {"Men Accessories", "Women Accessories", "Coats and Jackets", "Jumpers", "Men Hoodies and Sweatshirts"
            , "Women Hoodies and Sweatshirts", "Men Jeans", "Women Jeans", "T-shirts and Polos", "Trousers and Chinos", "Shirts", "Men Shoes",
            "Women Shoes", "Underwear and Shocks", "Bags and Purses", "Dresses", "Lingerie", "Tops and T-shirts", "Trousers and Leggings"
            , "Shorts", "Skirts", "Workwear"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_main, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        final CustomAdapter customAdapter = new CustomAdapter(getActivity(), list);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listAll);
        String value = getArguments().getString("value");
        if (value.equals("isEmpty")) {
            listView.setAdapter(customAdapter);
        } else {
            listView.setAdapter(arrayAdapter);
            arrayAdapter.getFilter().filter(value);

        }
        return view;
    }
}
