package com.lingme.anand.lingme.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.lingme.anand.lingme.Activity.Pojo.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by nepal on 13/03/2016.
 */
public class ServerRequest {
    ProgressDialog progressDialog;
    private byte[] password = null;
    private String base64;
    private static final String REQUEST_URL = "http://wwwgyaampe.com/osaapasaa/user_authenticate.php";
    private static final int CONNECTION_TIMEOUT = 1000 * 15;
    public ServerRequest(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Processing");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }


    public void fetchUserDataInBackground(User user, GetUserCallBack callBack)
    {
        progressDialog.show();
        new FetchUserDataAsyncTask(user, callBack).execute();
    }

    public class FetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User user;
        GetUserCallBack userCallBack;

        public FetchUserDataAsyncTask(User user, GetUserCallBack callBack) {
            this.user = user;
            this.userCallBack = callBack;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            try {
                password = user.getPassword().getBytes("UTF-8");
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
            Log.i("base64",base64);
            dataToSend.add(new BasicNameValuePair("username", user.getUsername()));
            dataToSend.add(new BasicNameValuePair("password", base64));

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);

            HttpClient httpClient = new DefaultHttpClient(httpParams);
            HttpPost post = new HttpPost(REQUEST_URL);

            User returnedUser = null;
            try{
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = httpClient.execute(post);

                HttpEntity httpEntity = httpResponse.getEntity();
                String result = EntityUtils.toString(httpEntity);
                JSONObject jsonObject = new JSONObject(result);

                if(jsonObject.length() == 0)
                {
                    returnedUser = null;
                }
                else {
                    String username = jsonObject.getString("username");
                    String password = jsonObject.getString("password");
                    String address = jsonObject.getString("address");
                    String phoneNumber = jsonObject.getString("phoneNumber");
                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    returnedUser = new User();
                    returnedUser.setName(name);
                    returnedUser.setUsername(username);
                    returnedUser.setPhoneNumber(Long.parseLong(phoneNumber));
                    returnedUser.setEmail(email);
                    returnedUser.setAddress(address);
                    returnedUser.setPassword(password);
                }
            }catch (Exception e)
            {
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User user) {
            progressDialog.dismiss();
            userCallBack.done(user);
            super.onPostExecute(user);
        }
    }

}
