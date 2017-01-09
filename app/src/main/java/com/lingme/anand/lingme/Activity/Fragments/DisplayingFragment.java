package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.Adapters.NecklaceRecyclerAdapter;
import com.lingme.anand.lingme.Activity.DetailsActivity;
import com.lingme.anand.lingme.Activity.Listeners.EndlessRecyclerOnScrollListener;
import com.lingme.anand.lingme.Activity.Listeners.HidingScrollListener;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelectedListener;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nepal on 18/11/2015.
 */
public class DisplayingFragment extends Fragment implements OnItemSelectedListener {
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private RecyclerView recyclerView;
    private String dbname;
    private ImageView imageView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private NecklaceRecyclerAdapter necklaceRecyclerAdapter;
    private List<ListProduct> listProducts = new ArrayList<ListProduct>();
    private ProgressBar mProgressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        View view;
        if (networkInfo != null && networkInfo.isConnected()) {
            view = inflater.inflate(R.layout.list_product, container, false);
            dbname = getArguments().getString("dbname");
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_list_product);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            final LinearLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 2);
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager1);

            recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
                @Override
                public void onLoadMore(int current_page) {
                    int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                    loadMore(current_page);

                }
            });
            updateList();


        } else {
            view = inflater.inflate(R.layout.fragment_connection, container, false);
            imageView = (ImageView) view.findViewById(R.id.refresh);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    DisplayingFragment fragment = new DisplayingFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                    fragmentTransaction.commit();
                }
            });
        }

        return view;

    }

    public void updateList() {

        String wholeUrl = "http://www.osaapasaa.com.np/list.php?table="+dbname;

        necklaceRecyclerAdapter = new NecklaceRecyclerAdapter(getActivity(), listProducts, this);
        recyclerView.setAdapter(necklaceRecyclerAdapter);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        necklaceRecyclerAdapter.clearAdapter();
        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, wholeUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("hi", response.toString());
                hidePD();

                try {
                    JSONArray results = response.getJSONArray("home");

                    for (int i = 0; i < results.length(); i++) {

                        JSONObject post = results.getJSONObject(i);

                        ListProduct h = new ListProduct();
                        h.setImg1("http://www.osaapasaa.com.np/img/" + dbname + post.getString("img1"));
                        h.setName(post.getString("name"));
                        h.setProductId(post.getString("product_id"));
                        h.setPrice(Integer.parseInt(post.getString("price")));
                        listProducts.add(h);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                necklaceRecyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            hidePD();
            }
        });

        queue.add(jsonObjectRequest);
    }


    public void showPd() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }
    }

    public void hidePD() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }


    public void loadMore(int page)
    {
        String url = "http://www.osaapasaa.com.np/list.php?"+"page="+page+"&table="+dbname;
        Log.i("pop",dbname);
        necklaceRecyclerAdapter = new NecklaceRecyclerAdapter(getActivity(), listProducts, this);
        recyclerView.setAdapter(necklaceRecyclerAdapter);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                hidePD();

                try {

                    JSONArray home = response.getJSONArray("home");
                    for (int i = 0; i < home.length(); i++) {

                        JSONObject post = home.getJSONObject(i);

                        ListProduct h = new ListProduct();
                        h.setImg1("http://www.osaapasaa.com.np/" + dbname + post.getString("img1"));
                        h.setName(post.getString("name"));
                        h.setProductId(post.getString("product_id"));
                        h.setPrice(Integer.parseInt(post.getString("price")));
                        listProducts.add(h);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                necklaceRecyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePD();
            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(int itemId) {
        Intent in = new Intent(getActivity(), DetailsActivity.class);
        in.putExtra("id",listProducts.get(itemId).getProductId());
        Log.e("error",listProducts.get(itemId).getProductId());
        in.putExtra("table", dbname);
        startActivity(in);
    }
}
