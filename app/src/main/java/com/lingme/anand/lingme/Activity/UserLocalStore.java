package com.lingme.anand.lingme.Activity;

import android.content.Context;
import android.content.SharedPreferences;

import com.lingme.anand.lingme.Activity.Pojo.User;

/**
 * Created by nepal on 13/03/2016.
 */
public class UserLocalStore
{
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalStore;
    public UserLocalStore(Context context)
    {
        userLocalStore = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserLocalData(User user)
    {
        SharedPreferences.Editor spEditor = userLocalStore.edit();
        spEditor.putString("username", user.getUsername());
        spEditor.putString("name", user.getName());
        spEditor.putString("address", user.getAddress());
        spEditor.putString("email", user.getEmail());
        spEditor.putString("password", user.getPassword());
        spEditor.putLong("phoneNumber", user.getPhoneNumber());
        spEditor.commit();
    }

    public User getLoggedUser()
    {
        User user = new User();
        user.setName(userLocalStore.getString("name", ""));
        user.setUsername(userLocalStore.getString("username", ""));
        user.setPassword(userLocalStore.getString("password", ""));
        user.setAddress(userLocalStore.getString("address", ""));
        user.setEmail(userLocalStore.getString("email", ""));
        user.setPhoneNumber(userLocalStore.getLong("phoneNumber", 0));
        return user;
    }

    public void setUserLogIn(Boolean logIn)
    {
        SharedPreferences.Editor editor = userLocalStore.edit();
        editor.putBoolean("logIn", logIn);
        editor.commit();
    }

    public Boolean getUserLogIn()
    {
        if(userLocalStore.getBoolean("logIn",false) == true)
            return true;
        else
            return false;
    }

    public void clearUserData()
    {
        SharedPreferences.Editor editor = userLocalStore.edit();
        editor.clear();
        editor.commit();
    }

}
