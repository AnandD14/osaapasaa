package com.lingme.anand.lingme.Activity.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.lingme.anand.lingme.Activity.GetUserCallBack;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Pojo.User;
import com.lingme.anand.lingme.Activity.ServerRequest;
import com.lingme.anand.lingme.Activity.UserLocalStore;
import com.lingme.anand.lingme.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 3/11/2015.
 */
public class LoginFragment extends Fragment {
    EditText login_user_input,login_password_input;
    FancyButton login,register;
    FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    User user;
    UserLocalStore userLocalStore;
    CoordinatorLayout coordinatorLayout;
    ImageView imageView;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        View view;
        if (networkInfo != null && networkInfo.isConnected()) {
            view = inflater.inflate(R.layout.login, container, false);
            user = new User();
            coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
            login_user_input = (EditText) view.findViewById(R.id.login_username_input);
            login_password_input = (EditText) view.findViewById(R.id.login_password_input);
            login = (FancyButton) view.findViewById(R.id.login);
            userLocalStore = new UserLocalStore(getActivity());
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(login_user_input.getText().toString())) {
                        login_user_input.requestFocus();
                        login_user_input.setError("Username cannot be empty");
                        return;
                    } else if (TextUtils.isEmpty(login_password_input.getText().toString())) {
                        login_password_input.requestFocus();
                        login_password_input.setError("Password cannot be empty");
                        return;
                    } else {
                        String username = login_user_input.getText().toString();
                        String password = login_password_input.getText().toString();
                        user.setUsername(username);
                        user.setPassword(password);
                        authenticate(user);
                    }
                }
            });
            register = (FancyButton) view.findViewById(R.id.register);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SignUpFragment fragment = new SignUpFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                  /*  fragmentTransaction.addToBackStack(((AppCompatActivity) getActivity()).getSupportActionBar().getTitle().toString());
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("User Registration");*/
                    fragmentTransaction.commit();
                }
            });
        }
        else {
            view = inflater.inflate(R.layout.fragment_connection, container, false);
            imageView = (ImageView) view.findViewById(R.id.refresh);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    LoginFragment fragment = new LoginFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, LoginFragment.class.getName());
                    fragmentTransaction.commit();
                }
            });
        }
        return view;
    }

    public void authenticate(User user)
    {
        ServerRequest serverRequest = new ServerRequest(getActivity());
        serverRequest.fetchUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                if(returnedUser == null) {
                    showErrorMessage();
                }else {
                    logUserIn(returnedUser);
                }
            }
        });
    }

    private void showErrorMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Failed to Login");

        // Setting Dialog Message
        alertDialog.setMessage("Incorrect Username or Password");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);

        // Setting OK Button
        alertDialog.setPositiveButton("OK", null);

        // Showing Alert Message
        alertDialog.show();
    }

    private void logUserIn(User user)
    {
        userLocalStore.storeUserLocalData(user);
        userLocalStore.setUserLogIn(true);
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }
}
