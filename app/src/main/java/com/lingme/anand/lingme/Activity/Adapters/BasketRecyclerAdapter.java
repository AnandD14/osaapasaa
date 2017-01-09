package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Listeners.Delete;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.BasList;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 23/02/2016.
 */
public class BasketRecyclerAdapter extends RecyclerView.Adapter<BasketRecyclerAdapter.HolderView>
{
    private ArrayList<BasList> list;
    private ImageLoader mImageLoader;
    private Context context;
    private Delete delete;
    public BasketRecyclerAdapter(Context context, ArrayList<BasList> list,Delete delete)
    {
        this.context = context;
        this.list = list;
        this.delete = delete;
        notifyDataSetChanged();
    }


    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_recyc, null);
        HolderView holderView = new HolderView(view);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, final int position)
    {
        final BasList details = list.get(position);
        holder.getLayoutPosition();
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.networkImageView.setImageUrl(details.getImg1(), mImageLoader);
        holder.networkImageView.setDefaultImageResId(R.drawable.logo);
        holder.name_product.setText("Name: " + details.getName());
        holder.brand.setText("Brand: " + details.getBrand());
        holder.stock.setText("Stock: " + details.getStock());
        holder.fav_price.setText("Rs. " + details.getPrice() + "");
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaText-Regular.ttf");
        holder.name_product.setTypeface(tf);
        Typeface tf1 = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaAmpersand-Bold.ttf");
        holder.fav_price.setTypeface(tf1);
        holder.brand.setTypeface(tf);
        holder.stock.setTypeface(tf);
        holder.size.setTypeface(tf);
        if(details.getSize().isEmpty() == true)
        {
            holder.size.setVisibility(View.GONE);
        }
        else {
            holder.size.setText("Size: "+details.getSize());
            holder.size.setTypeface(tf);
        }
        holder.remove.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(context);
                SQLiteDatabase del = db.getWritableDatabase();
                del.delete("basket", "id=?", new String[]{String.valueOf(details.getId())});
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                ((AppCompatActivity) context).getSupportActionBar().invalidateOptionsMenu();
                HomeActivity.bas_badge--;
                delete.delete();
            }
        });
    }

    public void clearAdapter()
    {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }



    public class HolderView extends RecyclerView.ViewHolder
    {
        protected NetworkImageView networkImageView;
        protected TextView name_product, fav_price, brand, stock, size;
        protected ImageButton remove;

        public HolderView(View itemView) {
            super(itemView);
            this.networkImageView = (NetworkImageView) itemView.findViewById(R.id.bas_image);
            this.name_product = (TextView) itemView.findViewById(R.id.bas_name);
            this.fav_price = (TextView) itemView.findViewById(R.id.price_bas);
            this.brand = (TextView) itemView.findViewById(R.id.bas_brand);
            this.stock = (TextView) itemView.findViewById(R.id.bas_stock);
            this.remove = (ImageButton) itemView.findViewById(R.id.bas_list);
            this.size = (TextView) itemView.findViewById(R.id.bas_size);
        }
    }
}
