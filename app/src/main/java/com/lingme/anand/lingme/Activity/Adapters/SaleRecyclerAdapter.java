package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.Listeners.Select;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.HomePopular;
import com.lingme.anand.lingme.Activity.Pojo.SaleList;
import com.lingme.anand.lingme.R;

import java.util.List;

/**
 * Created by nepal on 10/11/2015.
 */
public class SaleRecyclerAdapter extends RecyclerView.Adapter<SaleRecyclerAdapter.ListHolderView> {
    private List<SaleList> list;
    private ImageLoader mImageLoader;
    private Context context;
    Select select;
    public SaleRecyclerAdapter(Context context, List<SaleList> list, Select select) {
        this.context=context;
        this.list = list;
        this.select = select;
    }



    @Override
    public ListHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_recyc, null);
        ListHolderView holder = new ListHolderView(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListHolderView holder, int position) {
        SaleList details = list.get(position);
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.networkImageView.setImageUrl(details.getImage_path(), mImageLoader);
        holder.networkImageView.setDefaultImageResId(R.drawable.logo);
        holder.name_price_offer.setText(details.getName()+" "+details.getPrice());

    }
    public void clearAdapter() {
        list.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public class ListHolderView extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected NetworkImageView networkImageView;
        protected TextView name_price_offer;
        public ListHolderView(View itemView) {
            super(itemView);
            this.networkImageView = (NetworkImageView) itemView.findViewById(R.id.offer);
            this.name_price_offer=(TextView)itemView.findViewById(R.id.name_price_offer);
            networkImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            select.select();
        }
    }
}
