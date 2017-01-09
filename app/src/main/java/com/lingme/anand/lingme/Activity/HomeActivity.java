package com.lingme.anand.lingme.Activity;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.MatrixCursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.lingme.anand.lingme.Activity.Fragments.BasketFragment;
import com.lingme.anand.lingme.Activity.Fragments.DisplayingFragment;
import com.lingme.anand.lingme.Activity.Fragments.FavouriteFragment;
import com.lingme.anand.lingme.Activity.Fragments.HomeFragment;
import com.lingme.anand.lingme.Activity.Fragments.LoginFragment;
import com.lingme.anand.lingme.Activity.Fragments.SearchFragment;
import com.lingme.anand.lingme.Activity.Fragments.SignUpFragment;
import com.lingme.anand.lingme.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;

/**
 * Created by nepal on 20/10/2015.
 */
public class HomeActivity extends AppCompatActivity
{
    private BroadcastReceiver broadcastReceiver;
    TextView menu, fav, bas, ac;
    DatabaseHelper db;
    boolean doubleBackToExitPressedOnce = false;
    private PopupMenu popupMenu;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private SearchManager searchManager;
    SearchView searchView;
    LayoutInflater mInflater;
    PopupWindow mDropdown;
    Drawable fav_drawable, bas_drawable;
    DisplayingFragment fragment;
    Bundle bundle, input;
    FragmentTransaction fragmentTransaction;
    String fragmentName;
    public static int bas_badge = 0;
    public static int fav_badge = 0;
    Menu menu_item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        db = new DatabaseHelper(getApplicationContext());
        UserLocalStore userLocalStore = new UserLocalStore(this);
        input = getIntent().getExtras();
        instanciate();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                }
                else if(intent.getAction().endsWith(GCMRegistrationIntentService.REGISTRATION_ERROR)) {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }
            }

        };
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(ConnectionResult.SUCCESS != resultCode)
        {
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                Toast.makeText(getApplicationContext(), "google play services is not enabled/installed in this device", Toast.LENGTH_LONG).show();
                GooglePlayServicesUtil.showErrorNotification(resultCode ,getApplicationContext());
            } else {
                Toast.makeText(getApplicationContext(), "this device doesnot support google play services ", Toast.LENGTH_LONG).show();
            }

        } else {
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }
        if (input == null) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment fragment = new HomeFragment();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.fragments, fragment, HomeFragment.class.getName());
            fragmentTransaction.commit();
        } else {
            fragmentName = input.getString("fragment");
            if (fragmentName.equals("LoginFragment")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                LoginFragment fragment = new LoginFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, LoginFragment.class.getName());
                fragmentTransaction.addToBackStack("");
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportActionBar().setTitle("User Login");
                fragmentTransaction.commit();
            } else if (fragmentName.equals("access")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "access");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("coats_jackets")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "coats_jackets");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("jumpers")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "jumpers");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("hoodies_sweatshirt")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "hoodies_sweatshirt");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("jeans")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "jeans");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());;
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("tshirts_polos")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "tshirts_polos");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("shirts")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "shirts");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("trousers_chinos")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "trousers_chinos");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("shoes")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "shoes");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("underwear_shocks")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "underwear_shocks");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else   if (fragmentName.equals("accessw")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "accessw");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("coats_jacketsw")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "coats_jacketsw");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("jumpers")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "jumpers");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("hoodies_sweatshirtsw")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "hoodies_sweatshirtsw");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("jeansw")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "jeansw");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("top_tshirts")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "top_tshirts");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("skirts")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "skirts");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("bags_purses")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "bags_purses");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("shoesw")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "shoesw");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            } else if (fragmentName.equals("shorts")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "shorts");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("lingerie")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "lingerie");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("dresses")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "dresses");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }
            else if (fragmentName.equals("trousers_leggings")) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("dbname", "trousers_leggings");
                DisplayingFragment fragment = new DisplayingFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                fragmentTransaction.commit();
            }else {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SignUpFragment fragment = new SignUpFragment();
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                getSupportActionBar().setTitle("Account Details");
                fragmentTransaction.commit();
            }
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        navigationView = (NavigationView) findViewById(R.id.navView);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    //mDrawerToggle.setDrawerIndicatorEnabled(false); // show back button
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();

                        }
                    });
                } else {
                    //show hamburger
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    // mDrawerToggle.setDrawerIndicatorEnabled(true);
                    mDrawerToggle.syncState();
                    invalidateOptionsMenu();
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                                mDrawerLayout.closeDrawer(Gravity.LEFT); //CLOSE Nav Drawer!
                            }else{
                                mDrawerLayout.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
                            }
                        }
                    });
                }
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.sales:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "sales");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.new_in:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "new_in");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.cnj:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "coats_jackets");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.hns:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "hoodies_sweatshirt");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.jeans:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "jeans");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.jumpers:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "jumpers");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.shirts:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "shirts");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.tnp:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "tshirts_polos");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.tnc:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "trousers_chinos");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.shoes:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "shoes");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.uns:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "underwear_shocks");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.access:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "access");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.accesw:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "accessw");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.bnp:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "bags_purses");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.cnjw:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "coats_jacketsw");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.dresses:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "dresses");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.hnsw:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "hoodies_sweatshirtsw");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.jeansw:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "jeansw");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.lingerie:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "lingerie");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.tnt:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "top_tshirts");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.shorts:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "shorts");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.tnl:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "trousers_leggings");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.skirt:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "skirts");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.shoesw:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "shoes");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.workwear:
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        bundle = new Bundle();
                        bundle.putString("dbname", "workwear");
                        fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });
    }

    public void instanciate() {
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "OsaapasaaAmpersand-Regular.ttf");
       /* menu = (TextView) findViewById(R.id.menu);
        fav = (TextView) findViewById(R.id.fav);
        bas = (TextView) findViewById(R.id.basket);
        ac = (TextView) findViewById(R.id.ac);
        menu.setTypeface(tf);
        fav.setTypeface(tf);
        bas.setTypeface(tf);
        ac.setTypeface(tf);*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fav_drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_white_24dp);
        bas_drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_shop_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       /* menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(HomeActivity.this, v);
                popupMenu.setOnDismissListener(new OnDismissListener());
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener());
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                bas.setSelected(false);
                fav.setSelected(false);
                ac.setSelected(false);
                menu.setSelected(true);

            }
        });
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
                if(userLocalStore.getUserLogIn() == true){
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SignUpFragment fragment = new SignUpFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, SignUpFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("Account Details");
                    fragmentTransaction.commit();
                }else {
                    fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LoginFragment fragment = new LoginFragment();
                    fragmentTransaction.replace(R.id.fragments, fragment, LoginFragment.class.getName());
                    fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                    getSupportActionBar().setTitle("User Login");
                    fragmentTransaction.commit();
                }
                ac.setSelected(true);
                bas.setSelected(false);
                fav.setSelected(false);
                menu.setSelected(false);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FavouriteFragment fragment = new FavouriteFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, FavouriteFragment.class.getName());
                fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                getSupportActionBar().setTitle("Favourites");
                fragmentTransaction.commit();
                ac.setSelected(false);
                bas.setSelected(false);
                fav.setSelected(true);
                menu.setSelected(false);
                fav_badge = 0;
                getSupportActionBar().invalidateOptionsMenu();
            }
        });
        bas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                BasketFragment fragment = new BasketFragment();
                fragmentTransaction.replace(R.id.fragments, fragment, BasketFragment.class.getName());
                fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                getSupportActionBar().setTitle("Basket");
                fragmentTransaction.commit();
                bas.setSelected(true);
                fav.setSelected(false);
                ac.setSelected(false);
                menu.setSelected(false);
                bas_badge = 0;
                getSupportActionBar().invalidateOptionsMenu();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.action_menu, menu);
        if (fav_badge > 0)
            ActionItemBadge.update(this, menu.findItem(R.id.fav_badge), fav_drawable, ActionItemBadge.BadgeStyles.YELLOW, fav_badge);
        if (bas_badge > 0)
            ActionItemBadge.update(this, menu.findItem(R.id.bas_badge), bas_drawable, ActionItemBadge.BadgeStyles.YELLOW, bas_badge);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("search");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SearchFragment searchFragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putString("value", "isEmpty");
                searchFragment.setArguments(args);
                fragmentTransaction.replace(R.id.fragments, searchFragment, SearchFragment.class.getName());
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SearchFragment fragment = new SearchFragment();
                    Bundle args = new Bundle();
                    args.putString("value", "isEmpty");
                    fragment.setArguments(args);
                    fragmentTransaction.replace(R.id.fragments, fragment, SearchFragment.class.getName());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    SearchFragment fragment = new SearchFragment();
                    Bundle args = new Bundle();
                    args.putString("value", newText);
                    fragment.setArguments(args);
                    fragmentTransaction.replace(R.id.fragments, fragment, SearchFragment.class.getName());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_badge:
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FavouriteFragment fragment = new FavouriteFragment();
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransaction.replace(R.id.fragments, fragment, FavouriteFragment.class.getName());
                fragmentTransaction.addToBackStack("");
              /*  fragmentTransaction.addToBackStack(getSupportActionBar().getTitle().toString());
                getSupportActionBar().setTitle("Favourites");*/
                fragmentTransaction.commit();
                break;
            case R.id.bas_badge:
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransactionBas = fragmentManager.beginTransaction();
                BasketFragment fragmentBas = new BasketFragment();
                fragmentTransactionBas.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                fragmentTransactionBas.replace(R.id.fragments, fragmentBas, BasketFragment.class.getName());
                fragmentTransactionBas.addToBackStack("");
                fragmentTransactionBas.commit();
                break;
            case R.id.action_search:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            doubleBackToExitPressedOnce = false;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(GCMRegistrationIntentService.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
