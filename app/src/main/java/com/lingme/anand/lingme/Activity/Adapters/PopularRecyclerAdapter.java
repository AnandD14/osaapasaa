package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelect;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.HomePopular;
import com.lingme.anand.lingme.R;

import java.util.List;

/**
 * Created by nepal on 10/11/2015.
 */
public class PopularRecyclerAdapter extends RecyclerView.Adapter<PopularRecyclerAdapter.ListHolderView> {
    private List<HomePopular> list;
    private ImageLoader mImageLoader;
    private Context context;
    OnItemSelect select;
    public PopularRecyclerAdapter(Context context, List<HomePopular> list,OnItemSelect select) {
        this.context=context;
        this.list = list;
        this.select=select;
    }



    @Override
    public ListHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular, null);
        ListHolderView holder = new ListHolderView(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListHolderView holder, int position) {
        HomePopular details = list.get(position);
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaText-Regular.ttf");
        holder.networkImageView.setImageUrl(details.getImage_path(), mImageLoader);
        holder.networkImageView.setDefaultImageResId(R.drawable.logo);
        holder.name_price_popular.setText(details.getName() + "\n" + "Rs." + details.getPrice());
        holder.name_price_popular.setTypeface(tf);

    }
    public void clearAdapter() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public class ListHolderView extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected NetworkImageView networkImageView;
        protected TextView name_price_popular;
        public ListHolderView(View itemView) {
            super(itemView);
            this.networkImageView = (NetworkImageView) itemView.findViewById(R.id.popular_recyc);
            this.name_price_popular=(TextView)itemView.findViewById(R.id.name_price_popular);
            networkImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            select.onItemSelect(getAdapterPosition());
        }
    }
}
