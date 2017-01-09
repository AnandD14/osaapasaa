package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lingme.anand.lingme.Activity.Fragments.SearchDetail;
import com.lingme.anand.lingme.Activity.Fragments.SignUpFragment;
import com.lingme.anand.lingme.R;

/**
 * Created by nepal on 3/04/2016.
 */
public class CustomAdapter extends ArrayAdapter<String>
{
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Context context;
    String[] list;
    public CustomAdapter(Context context,String[] list)
    {
        super(context, R.layout.listview_content, list);
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_content, parent, false);
        TextView list_item = (TextView) row.findViewById(R.id.list_item);
        ImageView list_image = (ImageView) row.findViewById(R.id.list_image);
        String item = list[position];
        if(item.equals("Women"))
        {
            list_item.setText(item);
            list_image.setImageResource(R.drawable.ic_dress);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    SearchDetail fragment = new SearchDetail();
                    Bundle args = new Bundle();
                    args.putString("value", "listWomen");
                    fragment.setArguments(args);
                    fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    fragmentTransaction.replace(R.id.fragments, fragment, SearchDetail.class.getName());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
            });
        }
        else if(item.equals("Men"))
        {
            list_item.setText(item);
            list_image.setImageResource(R.drawable.ic_suit_vest_coat_jacket_fashion_cloth_512);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    SearchDetail fragment = new SearchDetail();
                    Bundle args = new Bundle();
                    args.putString("value", "listMen");
                    fragment.setArguments(args);
                    fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    fragmentTransaction.replace(R.id.fragments, fragment, SearchDetail.class.getName());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                }
            });
        }
        else if(item.equals("New In"))
        {
            list_item.setText(item);
            list_image.setImageResource(R.drawable.ic_1459698549_new);
        }
        else if(item.equals("Sales"))
        {
            list_item.setText(item);
            list_image.setImageResource(R.drawable.ic_business_sale_icon);
        }
        else if(item.equals("Offer"))
        {
            list_item.setText(item);
            list_image.setImageResource(R.drawable.ic_launcher);
        }
        return row;
    }
}
