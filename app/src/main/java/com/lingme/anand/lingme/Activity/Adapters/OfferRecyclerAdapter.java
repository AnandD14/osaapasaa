package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.Fragments.DisplayingFragment;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelectedListener;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.HomePopular;
import com.lingme.anand.lingme.Activity.Pojo.OfferList;
import com.lingme.anand.lingme.R;

import java.util.List;

/**
 * Created by nepal on 10/11/2015.
 */
public class OfferRecyclerAdapter  extends RecyclerView.Adapter<OfferRecyclerAdapter.ListHolderView> {
    private List<OfferList> list;
    private ImageLoader mImageLoader;
    private Context context;
    FragmentManager fragmentManager;
    DisplayingFragment fragment;
    Bundle bundle;
    OnItemSelectedListener listener;
    FragmentTransaction fragmentTransaction;
    public OfferRecyclerAdapter(Context context, List<OfferList> list,OnItemSelectedListener listener) {
        this.context=context;
        this.list = list;
        this.listener=listener;
    }



    @Override
    public ListHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_recyc, null);
        ListHolderView holder = new ListHolderView(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListHolderView holder, int position) {
        OfferList details = list.get(position);
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.networkImageView.setImageUrl(details.getImage_path(), mImageLoader);
        holder.networkImageView.setDefaultImageResId(R.drawable.logo);
        holder.name_price_offer.setText(details.getName() + " " + details.getPrice());

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
        protected TextView name_price_offer;
        public ListHolderView(View itemView) {
            super(itemView);
            this.networkImageView = (NetworkImageView) itemView.findViewById(R.id.offer);
            this.name_price_offer = (TextView) itemView.findViewById(R.id.name_price_offer);
            networkImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
           listener.onItemSelected(getAdapterPosition());
        }
    }

}
