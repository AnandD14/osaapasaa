package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.DetailsActivity;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.Activity.Pojo.User;
import com.lingme.anand.lingme.Activity.UserLocalStore;
import com.lingme.anand.lingme.R;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectOutput;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 29/10/2015.
 */
public class SignUpFragment extends Fragment {
    EditText username_input,password_input,name,address,phoneNumber,email,password_confirm;
    FancyButton submit, logout;
    String inserUrl= "http://www.osaapasaa.com.np/insert.php";
    String wholeUrl = "http://www.osaapasaa.com.np/login.php";
    RequestQueue requestQueue ,getRequestQueue;
    ProgressDialog progressDialog;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    ImageView imageView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private byte[] password = null;
    private String base64;
    CheckBox checkBox;
    boolean aBoolean = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        View view;
        if (networkInfo != null && networkInfo.isConnected()) {
            view = inflater.inflate(R.layout.fragment_signup, container, false);
            requestQueue = Volley.newRequestQueue(getActivity());
            username_input = (EditText) view.findViewById(R.id.username_input);
            password_input = (EditText) view.findViewById(R.id.password_input);
            password_confirm = (EditText) view.findViewById(R.id.confirm_password);
            phoneNumber = (EditText) view.findViewById(R.id.contact_input);
            name = (EditText) view.findViewById(R.id.name_input);
            address = (EditText) view.findViewById(R.id.address_input);
            email = (EditText) view.findViewById(R.id.email_input);
            checkBox = (CheckBox) view.findViewById(R.id.terms_conditions);
            submit = (FancyButton) view.findViewById(R.id.submit);
            logout = (FancyButton) view.findViewById(R.id.logout);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        aBoolean = true;
                    }
                }
            });
            check();
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(aBoolean == false){
                        checkBox.requestFocus();
                        checkBox.setError("please accept terms and conditions");
                    } else if (TextUtils.isEmpty(username_input.getText().toString())) {
                        username_input.requestFocus();
                        username_input.setError("Username cannot be empty");
                    } else if (TextUtils.isEmpty(password_input.getText().toString())) {
                        password_input.requestFocus();
                        password_input.setError("Password cannot be empty");

                    } else if (TextUtils.isEmpty(address.getText().toString())) {
                        address.requestFocus();
                        address.setError("Address cannot be empty");

                    } else if (TextUtils.isEmpty(email.getText().toString())) {
                        email.requestFocus();
                        email.setError("Email cannot be empty");
                    }else if (!password_input.getText().toString().equals(password_confirm.getText().toString())) {
                        password_input.requestFocus();
                        password_input.setError("Password didn't match");
                        password_confirm.setText("");
                    }
                    else if(isValidPassword(password_input.getText().toString().trim()) == false){
                        password_input.requestFocus();
                        password_input.setError("Password must contain [a-zA-Z0-9!@#$%^&*~+=]");
                    }
                    else if(isValidEmail(email.getText().toString()) == false){
                        email.requestFocus();
                        email.setError("email is not valid");
                    }
                    else if(isValidPhoneNumber(phoneNumber.getText().toString()) == false){
                        phoneNumber.requestFocus();
                        phoneNumber.setError("Mobile Number is not valid");
                    }else{
                        showPd();
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, wholeUrl, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                hidePD();
                                try {
                                    if (response.getString("message").equals("No data found")) {
                                        sendToServer();
                                        Log.i("message","message");
                                    } else {
                                        JSONArray results = response.getJSONArray("home");
                                        Log.i("nomsg","nomsg");
                                        for (int i = 0; i < results.length(); i++) {

                                            JSONObject post = results.getJSONObject(i);
                                            if(post.getString("username").equals(username_input.getText().toString())) {
                                                username_input.requestFocus();
                                                username_input.setError("Username already used");
                                                Log.i("username",post.getString("username"));
                                                break;
                                            } else if(post.getString("phoneNumber").equals(phoneNumber.getText()+"")) {
                                                phoneNumber.requestFocus();
                                                phoneNumber.setError("Phone Number already used");
                                                break;
                                            } else if(post.getString("email").equals(email.getText()+"")) {
                                                email.requestFocus();
                                                email.setError("email already used");
                                                break;
                                            } else {
                                                sendToServer();
                                                break;
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //  VolleyLog.d(TAG, "Error" + error.getMessage());
                                hidePD();
                            }

                        });
                        requestQueue.add(jsonObjectRequest);
                        }
                    }
            });
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userLogout();
                }
            });
        }else {
            view = inflater.inflate(R.layout.fragment_connection, container, false);
            imageView = (ImageView) view.findViewById(R.id.refresh);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    SignUpFragment fragment = new SignUpFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                    fragmentTransaction.commit();
                }
            });
        }
        return view;
    }

    private void sendToServer()
    {
        try {
            password = password_input.getText().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        base64 = Base64.encodeToString(password, Base64.NO_WRAP);
        try {
            password = base64.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        base64 = Base64.encodeToString(password, Base64.NO_WRAP);
        final StringRequest request = new StringRequest(Request.Method.POST, inserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                                getActivity());

                        // Setting Dialog Title
                        alertDialog.setTitle("SUCCESSFULL");

                        // Setting Dialog Message
                        alertDialog.setMessage("Registration is successfull");

                        // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.logo);

                        // Setting OK Button
                        alertDialog.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserLocalStore userLocalStore = new UserLocalStore(getActivity());
                                User loggedUser = new User();
                                loggedUser = userLocalStore.getLoggedUser();
                                loggedUser.setUsername(username_input.getText().toString());
                                loggedUser.setPassword(password_input.getText().toString());
                                loggedUser.setName(name.getText().toString());
                                loggedUser.setAddress(address.getText().toString());
                                loggedUser.setPhoneNumber(Long.parseLong(phoneNumber.getText().toString()));
                                loggedUser.setEmail(email.getText().toString());
                                userLocalStore.storeUserLocalData(loggedUser);
                                userLocalStore.setUserLogIn(true);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                HomeFragment fragment = new HomeFragment();
                                fragmentTransaction.replace(R.id.fragments, fragment, HomeFragment.class.getName());
                                fragmentTransaction.commit();
                            }
                        });
                        // Showing Alert Message
                        alertDialog.show();

                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
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

                username_input.setText("");
                password_input.setText("");
                address.setText("");
                email.setText("");
                phoneNumber.setText("");
                name.setText("");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("username", username_input.getText().toString());
                parameters.put("password", base64);
                parameters.put("name",name.getText().toString());
                parameters.put("address",address.getText().toString());
                parameters.put("phoneNumber",phoneNumber.getText().toString());
                parameters.put("email",email.getText().toString());
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    private void check()
    {
        UserLocalStore userLocalStore = new UserLocalStore(getActivity());
        User loggedUser = new User();
        if(userLocalStore.getUserLogIn() == true) {
            loggedUser = userLocalStore.getLoggedUser();
            username_input.setText(loggedUser.getUsername());
            password_input.setText(loggedUser.getPassword());
            address.setText(loggedUser.getAddress());
            email.setText(loggedUser.getEmail());
            phoneNumber.setText(loggedUser.getPhoneNumber()+"");
            name.setText(loggedUser.getName());
            logout.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
            password_confirm.setVisibility(View.GONE);
        }else {
            logout.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
            checkBox.setText("Terms and Conditions");
            checkBox.setVisibility(View.VISIBLE);
        }
    }

    private void userLogout()
    {
        UserLocalStore userLocalStore = new UserLocalStore(getActivity());
        userLocalStore.clearUserData();
        userLocalStore.setUserLogIn(false);
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    public void showPd() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("Processing");
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
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!~*@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean isValidPhoneNumber(final String phoneNumber) {

        Pattern pattern;
        Matcher matcher;

        final String PHONENUMBER_PATTERN = "^(98)\\d{8,8}$";

        pattern = Pattern.compile(PHONENUMBER_PATTERN);
        matcher = pattern.matcher(phoneNumber);

        return matcher.matches();

    }

    public boolean isValidEmail(String email)
    {
        boolean isValidEmail = false;

        String emailExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
        {
            isValidEmail = true;
        }
        return isValidEmail;
    }

}
