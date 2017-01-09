package com.lingme.anand.lingme.Activity;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nepal on 17/03/2016.
 */
public class AppLocalStore {
    public static final String SP_NAME = "appStore";
    SharedPreferences appLocalStore;
    public AppLocalStore(Context context)
    {
        appLocalStore = context.getSharedPreferences(SP_NAME, 0);
    }

    public void saveSize(String size)
    {
        SharedPreferences.Editor editor = appLocalStore.edit();
        editor.putString("size", size);
        editor.commit();
    }

    public String getSize()
    {
        return appLocalStore.getString("size","");
    }

    public void saveFavBadge(int fav_badge)
    {
        SharedPreferences.Editor editor = appLocalStore.edit();
        editor.putInt("fav_bagde", fav_badge);
        editor.commit();
    }

    public void saveBasBadge(int bas_badge)
    {
        SharedPreferences.Editor editor = appLocalStore.edit();
        editor.putInt("bas_bagde", bas_badge);
        editor.commit();
    }

    public int getFavBadge()
    {
        int fav_badge = appLocalStore.getInt("fav_badge", 0);
        return fav_badge;
    }

    public int getBasBadge()
    {
        int bas_badge = appLocalStore.getInt("bas_badge",0);
        return bas_badge;
    }
}
