package com.lingme.anand.lingme.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.Adapters.DetailsRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Adapters.NecklaceRecyclerAdapter;
import com.lingme.anand.lingme.Activity.Fragments.HomeFragment;
import com.lingme.anand.lingme.Activity.Fragments.SignUpFragment;
import com.lingme.anand.lingme.Activity.Listeners.Select;
import com.lingme.anand.lingme.Activity.Listeners.SelectSize;
import com.lingme.anand.lingme.Activity.Pojo.FavList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.Activity.Pojo.User;
import com.lingme.anand.lingme.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nepal on 6/11/2015.
 */
public class DetailsActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    String url = "http://www.osaapasaa.com.np/details.php";
    String inserUrl= "http://www.osaapasaa.com.np/order.php";
    private DetailsRecyclerAdapter detailsRecyclerAdapter;
    String id;
    String table;
    private List<ListProduct> listDetails = new ArrayList<ListProduct>();
    private ProgressDialog progressDialog , getProgressDialog;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private ImageView imageView;
    Toolbar toolbar;
    DatabaseHelper db;
    public static String size;
    AppLocalStore appLocalStore;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            setContentView(R.layout.list_details);
            db = new DatabaseHelper(getApplicationContext());
            appLocalStore = new AppLocalStore(this);
            toolbar = (Toolbar) findViewById(R.id.toolbar_details);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setLogo(R.drawable.logo);
            recyclerView = (RecyclerView) findViewById(R.id.recycler_list_details);
            Bundle did = getIntent().getExtras();
            id = did.getString("id");
            table = did.getString("table");
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            details();
        }
        else{
            setContentView(R.layout.fragment_connection);
            Bundle did = getIntent().getExtras();
            id = did.getString("id");
            table = did.getString("table");
            imageView=(ImageView)findViewById(R.id.refresh);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(getApplicationContext(),DetailsActivity.class);
                    in.putExtra("id",id);
                    in.putExtra("table",table);
                    startActivity(in);
                }
            });
        }
    }

    public void details() {

        String path = url + "?table=" + table + "&id=" + id;
        detailsRecyclerAdapter = new DetailsRecyclerAdapter(getApplicationContext(), listDetails);
        recyclerView.setAdapter(detailsRecyclerAdapter);
        requestQueue = Volley.newRequestQueue(this);
        showPd();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, path, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                hidePD();
                try {
                    JSONArray home = response.getJSONArray("home");
                    //  length = home.length();

                    for (int i = 0; i < home.length(); i++) {

                        JSONObject post = home.getJSONObject(i);

                        ListProduct h = new ListProduct();
                        String img1 = post.getString("img1");
                        String img2 = post.getString("img2");
                        String img3 = post.getString("img3");
                        h.setImg1("http://www.osaapasaa.com.np/img/" + table + img1);
                        h.setName(post.getString("name"));
                        h.setProductId(post.getString("product_id"));
                        h.setPrice(Integer.parseInt(post.getString("price")));
                        h.setImg2("http://www.osaapasaa.com.np/img/" + table + img2);
                        h.setImg3("http://www.osaapasaa.com.np/img/" + table + img3);
                        h.setBrand(post.getString("brand"));
                        h.setDescription(post.getString("description"));
                        h.setM(post.getString("m"));
                        h.setL(post.getString("l"));
                        h.setS(post.getString("s"));
                        h.setXl(post.getString("xl"));
                        h.setXxl(post.getString("xxl"));
                        h.setStock(post.getString("stock"));
                        h.setTable_name(table);
                        listDetails.add(h);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                 detailsRecyclerAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        DetailsActivity.this);

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
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void showPd() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public void hidePD() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_menu:
                ListProduct favList = listDetails.get(0);
                if(size == null)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Failed to Add");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please specify size");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.logo);

                    // Setting OK Button
                    alertDialog.setPositiveButton("OK", null);

                    // Showing Alert Message
                    alertDialog.show();


                }
                else {
                    Boolean isInserted = db.insert(favList.getProductId(), favList.getBrand(), favList.getName(), favList.getPrice(), favList.getDescription(), favList.getStock(), favList.getTable_name(), favList.getImg1(), size);
                    if (isInserted == true) {
                        Toast.makeText(getApplicationContext(), "Added to favourite", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(this, HomeActivity.class);
                        startActivity(in);
                        HomeActivity.fav_badge++;

                    } else
                        Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_SHORT).show();
                    size = null;
                }
                break;
            case R.id.bas_menu:
                ListProduct basList = listDetails.get(0);
                if(size == null)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Failed to Add");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please specify size");

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.logo);

                    // Setting OK Button
                    alertDialog.setPositiveButton("OK", null);

                    // Showing Alert Message
                    alertDialog.show();
                }else {
                    Boolean isInsertedBas = db.insertBas(basList.getProductId(), basList.getBrand(), basList.getName(), basList.getPrice(), basList.getDescription(), basList.getStock(), basList.getTable_name(), basList.getImg1(), size);
                    if (isInsertedBas == true) {
                        Toast.makeText(getApplicationContext(), "Added to Basket", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(this, HomeActivity.class);
                        startActivity(in);
                        HomeActivity.bas_badge++;
                    } else
                        Toast.makeText(getApplicationContext(), "Not Inserted", Toast.LENGTH_SHORT).show();
                    size = null;
                }
                break;
            case R.id.buy_menu:
                if(size == null)
                {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        this);

                // Setting Dialog Title
                alertDialog.setTitle("FAILED");

                // Setting Dialog Message
                alertDialog.setMessage("Please specify size");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.logo);

                // Setting OK Button
                alertDialog.setPositiveButton("OK", null);

                // Showing Alert Message
                alertDialog.show();
            }
                else {
                    UserLocalStore userLocalStore = new UserLocalStore(this);
                    if (userLocalStore.getUserLogIn() == true) {
                        sendToServer();
                    } else {
                        Intent in = new Intent(this, HomeActivity.class);
                        in.putExtra("fragment", "LoginFragment");
                        startActivity(in);
                        size = null;
                    }
                }
        }
        return false;
    }



    private void sendToServer()
    {
        final ListProduct favList = listDetails.get(0);
        final UserLocalStore userLocalStores = new UserLocalStore(this);
        final String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        final String time = new SimpleDateFormat("hh:mm").format(new Date());
        showProgressDialog();
        final StringRequest request = new StringRequest(Request.Method.POST, inserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                hideProgressDialog();
                Log.i("string", s);
                if (s.length() == 0) {
                    Log.i("hiiii", s);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            DetailsActivity.this);

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
                            startActivity(new Intent(DetailsActivity.this, HomeActivity.class));
                        }
                    });
                    // Showing Alert Message
                    alertDialog.show();
                    size = null;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideProgressDialog();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                       DetailsActivity.this);

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
                parameters.put("username", userLocalStores.getLoggedUser().getUsername());
                parameters.put("email", userLocalStores.getLoggedUser().getEmail());
                parameters.put("phoneNumber", userLocalStores.getLoggedUser().getPhoneNumber().toString());
                parameters.put("product", favList.getName());
                parameters.put("size", size);
                parameters.put("tableName", table);
                parameters.put("price", String.valueOf(favList.getPrice()));
                parameters.put("date", date);
                parameters.put("time", time);
                Log.i("value",userLocalStores.getLoggedUser().getUsername()+userLocalStores.getLoggedUser().getEmail()+userLocalStores.getLoggedUser().getPhoneNumber().toString()+favList.getName()+favList.getPrice()+date+time+size+table);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public void showProgressDialog() {
        if (getProgressDialog == null) {
            getProgressDialog = new ProgressDialog(this);
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
