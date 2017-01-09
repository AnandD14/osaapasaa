package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.Adapters.DetailsRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.NecklaceRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.OfferRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.PopularRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.SaleRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.ViewPagerAdapter;
import com.lingme.anand.lingme.Activity.DetailsActivity;
import com.lingme.anand.lingme.Activity.Listeners.EndlessRecyclerOnScrollListener;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelect;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelectedListener;
import com.lingme.anand.lingme.Activity.Listeners.Select;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.Home;
import com.lingme.anand.lingme.Activity.Pojo.HomePopular;
import com.lingme.anand.lingme.Activity.Pojo.OfferList;
import com.lingme.anand.lingme.Activity.Pojo.SaleList;
import com.lingme.anand.lingme.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by nepal on 29/10/2015.
 */
public class HomeFragment extends Fragment implements OnItemSelect {
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private AutoScrollViewPager viewPager;
    private List<Home> listDetails = new ArrayList<Home>();
    private List<HomePopular> homePopulars = new ArrayList<HomePopular>();
    private List<OfferList> offerLists = new ArrayList<OfferList>();
    private List<SaleList> saleLists = new ArrayList<SaleList>();
    private ViewPagerAdapter adapter;
    private CircleIndicator circlePageIndicator;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    private PopularRecyclerAdapter detailsRecyclerAdapter;
    private ProgressBar mProgressBar;
    private OfferRecyclerAdapter offerRecyclerAdapter;
    private SaleRecyclerAdapter saleRecyclerAdapter;
    private ImageView imageView;
    private FragmentManager fragmentManager;
    private NetworkImageView offer, sale;
    private ImageLoader mImageLoader;
    private TextView new_in_store, popular, offer_home, sale_home,popular_view_all;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        View view;
        if (networkInfo != null && networkInfo.isConnected()) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            mImageLoader = MySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader();
            offer = (NetworkImageView) view.findViewById(R.id.offer);
            sale = (NetworkImageView) view.findViewById(R.id.sale);
            viewPager = (AutoScrollViewPager) view.findViewById(R.id.new_pager);
            viewPager.setInterval(5000);
            viewPager.startAutoScroll();
            viewPager.setBorderAnimation(true);
            circlePageIndicator = (CircleIndicator) view.findViewById(R.id.new_indicator);
            recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler_view_popular);
            final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
            layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView1.setLayoutManager(layoutManager1);
            final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
            layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            new_in_store = (TextView) view.findViewById(R.id.new_in_store);
            popular = (TextView) view.findViewById(R.id.popular_textview);
            offer_home = (TextView) view.findViewById(R.id.offer_textview);
            sale_home = (TextView) view.findViewById(R.id.sale_textview);
            popular_view_all = (TextView) view.findViewById(R.id.popular_view_all);
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                    "OsaapasaaText-Regular.ttf");
            new_in_store.setTypeface(tf);
            popular.setTypeface(tf);
            offer_home.setTypeface(tf);
            sale_home.setTypeface(tf);
            recyclerView1.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
                @Override
                public void onLoadMore(int current_page) {
                    int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView1.getLayoutManager()).findFirstVisibleItemPosition();
                    ((LinearLayoutManager) recyclerView1.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                    loadMore1(current_page);

                }
            });
            updateList();
            detailsPopular();
            detailsOffer();
            detailsSale();
        } else {
            view = inflater.inflate(R.layout.fragment_connection, container, false);
            imageView = (ImageView) view.findViewById(R.id.refresh);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HomeFragment fragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                    fragmentTransaction.commit();
                }
            });
        }
        return view;
    }

    public void updateList() {

        String url = "http://www.osaapasaa.com.np/home_info.php";
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                hidePD();
                try {

                    JSONArray home = response.getJSONArray("home");


                    for (int i = 0; i < home.length(); i++) {

                        JSONObject post = home.getJSONObject(i);

                        Home h = new Home();
                        String img = post.getString("new");
                        h.setNewThumbnail("http://www.osaapasaa.com.np/img/home" + img);
                        new_in_store.setText("New In Store");
                        listDetails.add(h);

                    }

                    adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), listDetails);
                    viewPager.setAdapter(adapter);
                    circlePageIndicator.setViewPager(viewPager);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

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

    public void detailsPopular()
    {
        String url = "http://www.osaapasaa.com.np/list.php?table=popular";
        detailsRecyclerAdapter = new PopularRecyclerAdapter(getActivity(), homePopulars, this);
        recyclerView1.setAdapter(detailsRecyclerAdapter);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        detailsRecyclerAdapter.clearAdapter();

        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

                hidePD();
                try {
                    JSONArray home = response.getJSONArray("home");

                    for (int i = 0; i < home.length(); i++) {

                        JSONObject post = home.getJSONObject(i);
                        popular.setText("Popular");
                        popular_view_all.setText("View All");
                        HomePopular h = new HomePopular();
                        String img = post.getString("img1");
                        h.setImage_path("http://www.osaapasaa.com.np/img/popular" + img);
                        h.setName(post.getString("name"));
                        h.setProduct_id(post.getString("product_id"));
                        h.setPrice(Integer.parseInt(post.getString("price")));
                        homePopulars.add(h);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                detailsRecyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePD();
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void loadMore1(int count) {
        Log.e("error", String.valueOf(count));
        String url = "http://www.osaapasaa.com.np/list.php?" + "page=" + count + "&table=popular";
        detailsRecyclerAdapter = new PopularRecyclerAdapter(getActivity(), homePopulars, this);
        recyclerView1.setAdapter(detailsRecyclerAdapter);

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

                        HomePopular h = new HomePopular();
                        String img = post.getString("img1");
                        h.setImage_path("http://www.osaapasaa.com.np/img/popular" + img);
                        h.setName(post.getString("name"));
                        h.setProduct_id(post.getString("product_id"));
                        h.setPrice(Integer.parseInt(post.getString("price")));
                        homePopulars.add(h);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                detailsRecyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePD();
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void detailsOffer() {
        String url = "http://www.osaapasaa.com.np/list.php?table=offer";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                hidePD();

                try {
                    JSONArray home = response.getJSONArray("home");
                    offer_home.setText("Offer");
                    JSONObject post = home.getJSONObject(0);
                    offer.setImageUrl(post.getString("img1"), mImageLoader);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePD();
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void detailsSale() {
        String url = "http://www.osaapasaa.com.np/list.php?table=sale";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                hidePD();

                try {
                    JSONArray home = response.getJSONArray("home");
                    sale_home.setText("Sale");
                    JSONObject post = home.getJSONObject(0);
                    sale.setImageUrl(post.getString("img1"), mImageLoader);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
    public void onItemSelect(int product_id) {
        Intent in = new Intent(getActivity(), DetailsActivity.class);
        in.putExtra("id", homePopulars.get(product_id).getProduct_id());
        Log.e("error", homePopulars.get(product_id).getProduct_id());
        in.putExtra("table", "popular");
        startActivity(in);
    }
}
