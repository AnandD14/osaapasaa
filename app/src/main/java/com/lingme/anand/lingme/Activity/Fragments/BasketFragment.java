package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.Adapters.BasketRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.FavouriteRecyclerAdapter;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.DetailsActivity;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Listeners.Delete;
import com.lingme.anand.lingme.Activity.Listeners.EndlessRecyclerOnScrollListener;
import com.lingme.anand.lingme.Activity.Pojo.BasList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.Activity.UserLocalStore;
import com.lingme.anand.lingme.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 23/02/2016.
 */
public class BasketFragment extends Fragment implements Delete {
    private ProgressDialog getProgressDialog;
    private RecyclerView recyclerView;
    private BasketRecyclerAdapter basketRecyclerAdapter;
    DatabaseHelper db, getDb, deldata;
    TextView buy_msg, basket_empty;
    FancyButton btn_buy;
    RequestQueue requestQueue;
    ArrayList<BasList> lists = new ArrayList<>();
    String inserUrl = "http://www.osaapasaa.com.np/order.php";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.basket, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.basket_list_product);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);
        db = new DatabaseHelper(getActivity());
        buy_msg = (TextView) view.findViewById(R.id.buy_msg);
        btn_buy = (FancyButton) view.findViewById(R.id.btn_buy);
        basket_empty = (TextView) view.findViewById(R.id.basket_msg);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "OsaapasaaText-Regular.ttf");
        basket_empty.setTypeface(tf);
        buy_msg.setTypeface(tf);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                loadMore(current_page);

            }
        });
        updateList();
        total();
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
            }
        });
        return view;

    }

    public void updateList() {
        basketRecyclerAdapter = new BasketRecyclerAdapter(getActivity(), db.getProductsBas(1), this);
        recyclerView.setAdapter(basketRecyclerAdapter);
    }


    public void loadMore(int page) {
        basketRecyclerAdapter = new BasketRecyclerAdapter(getActivity(), db.getProductsBas(page), this);
        recyclerView.setAdapter(basketRecyclerAdapter);
    }

    public void total() {
        getDb = new DatabaseHelper(getActivity());
        lists = getDb.getAllProducts();
        if (lists.isEmpty()) {
            buy_msg.setVisibility(View.GONE);
            btn_buy.setVisibility(View.GONE);
            basket_empty.setVisibility(View.VISIBLE);
        } else {
            int sum = 0;
            for (int i = 0; i < lists.size(); i++) {
                sum = sum + lists.get(i).getPrice();
            }
            buy_msg.setText("Total: Rs." + sum);
            buy_msg.setVisibility(View.VISIBLE);
            btn_buy.setVisibility(View.VISIBLE);
            basket_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void delete() {
        deldata = new DatabaseHelper(getActivity());
        lists = deldata.getAllProducts();
        if (lists.isEmpty()) {
            buy_msg.setVisibility(View.GONE);
            btn_buy.setVisibility(View.GONE);
            basket_empty.setVisibility(View.VISIBLE);
        } else {
            int sum = 0;
            for (int i = 0; i < lists.size(); i++) {
                sum = sum + lists.get(i).getPrice();
            }
            buy_msg.setText("Total: Rs." + sum);
            buy_msg.setVisibility(View.VISIBLE);
            btn_buy.setVisibility(View.VISIBLE);
            basket_empty.setVisibility(View.GONE);
        }
    }

    private void sendToServer() {
        DatabaseHelper getData = new DatabaseHelper(getActivity());
        ArrayList<String> sizeProduct = new ArrayList<>();
        ArrayList<String> product = new ArrayList<>();
        ArrayList<String> tableName = new ArrayList<>();
        ArrayList<BasList> basLists = new ArrayList<>();
        basLists = getData.getAllProducts();
        int price = 0;
        if (!(basLists.size() == 0)) {
            for (int i = 0; i < basLists.size(); i++) {
                product.add(basLists.get(i).getName());
                tableName.add(basLists.get(i).getTable_name());
                sizeProduct.add(basLists.get(i).getSize());
                price = price + basLists.get(i).getPrice();
                }
            }

        final UserLocalStore userLocalStore = new UserLocalStore(getActivity());
        final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        final String time = new SimpleDateFormat("hh:mm").format(new Date());
        final StringBuilder finalProduct = new StringBuilder();
        final StringBuilder finalSizeProduct = new StringBuilder();
        final StringBuilder finalTableName =  new StringBuilder();
        final int finalPrice = price;
        if(product.size() == 1 ){
            finalProduct.append(product.get(0));
            finalSizeProduct.append(sizeProduct.get(0));
            finalTableName.append(tableName.get(0));
        }
        else{
            for(int i = 0;i<product.size();i++){
                finalProduct.append(product.get(i));
                if(!(i == product.size()-1)) {
                    finalProduct.append(",");
                }
            }
            for(int i = 0;i<sizeProduct.size();i++){
                finalSizeProduct.append(sizeProduct.get(i));
                if(!(i == sizeProduct.size() -1)) {
                    finalSizeProduct.append(",");
                }
            }
            for(int i = 0;i<tableName.size();i++){
                finalTableName.append(tableName.get(i));
                if(!(i == tableName.size() -1)) {
                    finalTableName.append(",");
                }
            }
        }
        requestQueue = Volley.newRequestQueue(getActivity());
        showProgressDialog();
        final StringRequest request = new StringRequest(Request.Method.POST, inserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                hideProgressDialog();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        getActivity());

                // Setting Dialog Title
                alertDialog.setTitle("THANK YOU");

                // Setting Dialog Message
                alertDialog.setMessage("you will be contacted soon");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.logo);

                // Setting OK Button
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), HomeActivity.class));
                        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                        databaseHelper.deleteAll();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgressDialog();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        getActivity());

                // Setting Dialog Title
                alertDialog.setTitle("FAILED");

                // Setting Dialog Message
                alertDialog.setMessage("Oops something went wrong");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.logo);

                // Setting OK Button
                alertDialog.setPositiveButton("OK", null);

                // Showing Alert Message
                alertDialog.show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", userLocalStore.getLoggedUser().getName());
                parameters.put("email", userLocalStore.getLoggedUser().getEmail());
                parameters.put("phoneNumber", userLocalStore.getLoggedUser().getPhoneNumber().toString());
                parameters.put("product", String.valueOf(finalProduct));
                parameters.put("size", String.valueOf(finalSizeProduct));
                parameters.put("tableName", String.valueOf(finalTableName));
                parameters.put("price", String.valueOf(finalPrice));
                parameters.put("date", date);
                parameters.put("time", time);
                Log.i("value", userLocalStore.getLoggedUser().getUsername() + userLocalStore.getLoggedUser().getEmail() + userLocalStore.getLoggedUser().getPhoneNumber().toString() + finalProduct + finalPrice + date + time + finalSizeProduct + finalTableName);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public void showProgressDialog() {
        if (getProgressDialog == null) {
            getProgressDialog = new ProgressDialog(getActivity());
            getProgressDialog.setMessage("Please Wait...");
            getProgressDialog.setTitle("Processing");
            getProgressDialog.setCancelable(false);
            getProgressDialog.setCanceledOnTouchOutside(false);
            getProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (getProgressDialog != null) {
            getProgressDialog.dismiss();
            getProgressDialog = null;
        }
    }
}
