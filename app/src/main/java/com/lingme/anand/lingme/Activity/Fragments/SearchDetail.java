package com.lingme.anand.lingme.Activity.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lingme.anand.lingme.Activity.Listeners.SelectSize;
import com.lingme.anand.lingme.R;

/**
 * Created by nepal on 4/04/2016.
 */
public class SearchDetail extends Fragment {
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String[] listMen = {"Accessories", "Coats and Jackets", "Jumpers", "Hoodies and Sweatshirts", "Jeans",
            "T-shirts and Polos", "Trousers and Chinos", "Shirts", "Shoes", "Underwear and Shocks"};
    String[] listWomen = {"Accessories", "Bags and Purses", "Dresses", "Hoodies and Sweatshirts", "Jeans",
            "Lingerie", "Tops and T-shirts", "Shorts", "Trousers and Leggings", "Skirts", "Shoes", "Workwear"};
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_main, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        String value = getArguments().getString("value");
        if (value.equals("listMen")) {
            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listMen);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String selected = listMen[position];
                    if (selected.equals("Accessories")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "access");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Coats and Jackets")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "coats_jackets");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Jumpers")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "jumpers");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Hoodies and Sweatshirts")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "hoodies_sweatshirt");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Jeans")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "jeans");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("T-shirts and Polos")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "tshirts_polos");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Shirts")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "shirts");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Trousers and Chinos")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "trousers_chinos");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Shoes")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "shoes");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Underwear and Shocks")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "underwear_shocks");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                }
            });
        } else if (value.equals("listWomen")) {
            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listWomen);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String selected = listWomen[position];
                    if (selected.equals("Accessories")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "accessw");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Coats and Jackets")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "coats_jacketsw");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Jumpers")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "jumpers");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Hoodies and Sweatshirts")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "hoodies_sweatshirtsw");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Jeans")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "jeansw");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Tops and T-shirts")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "top_tshirts");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Skirts")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "skirts");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Bags and Purses")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "bags_purses");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Shoes")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "shoesw");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    } else if (selected.equals("Shorts")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "shorts");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Lingerie")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "lingerie");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Dresses")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "dresses");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                    else if (selected.equals("Trousers and Leggings")) {
                        fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle bundle = new Bundle();
                        bundle.putString("dbname", "trousers_leggings");
                        DisplayingFragment fragment = new DisplayingFragment();
                        fragment.setArguments(bundle);
                        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragmentTransaction.replace(R.id.fragments, fragment, DisplayingFragment.class.getName());
                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commit();
                    }
                }
            });
        }
        return view;
    }
}
