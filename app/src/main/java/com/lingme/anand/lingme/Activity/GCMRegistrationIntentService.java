package com.lingme.anand.lingme.Activity;

import android.app.IntentService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.lingme.anand.lingme.Activity.Fragments.HomeFragment;
import com.lingme.anand.lingme.Activity.Pojo.User;
import com.lingme.anand.lingme.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adarshan on 4/29/16.
 */
public class GCMRegistrationIntentService extends IntentService
{
    public static final String REGISTRATION_SUCCESS = "success";
    public static final String REGISTRATION_ERROR = "error";
    private static final String TAG = "GCMTOKEN";
    private String inserUrl = "http://www.osaapasaa.com.np/gcm.php";
    public GCMRegistrationIntentService() {
        super("");
    }
    private RequestQueue requestQueue;
    @Override
    protected void onHandleIntent(Intent intent) {
     registerGCM();
    }

    private void registerGCM()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("GCM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent registrationComplete = null;
        String token = null;
        try
        {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),  GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra("token",token);
            String oldToken = sharedPreferences.getString(TAG,"");

            if(!"".equals(token) && !oldToken.equals(token)) {
                saveToken(token);
                editor.putString(TAG,token);
                editor.commit();
            }
            saveToken(token);
        }catch(Exception e)
        {
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void saveToken(final String token)
    {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        final StringRequest request = new StringRequest(Request.Method.POST, inserUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {


                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("action","add");
                parameters.put("tokenid",token);
                return parameters;
            }
        };
        requestQueue.add(request);

    }
}
