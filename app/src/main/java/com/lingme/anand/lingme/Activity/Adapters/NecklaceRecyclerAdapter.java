package com.lingme.anand.lingme.Activity.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelectedListener;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;

import java.util.List;

/*
 * Created by nepal on 1/11/2015.
 */
public class NecklaceRecyclerAdapter extends RecyclerView.Adapter<NecklaceRecyclerAdapter.HolderView>
{
    private List<ListProduct> list;
    private ImageLoader mImageLoader;
    private Context context;
    private OnItemSelectedListener listener;

    public NecklaceRecyclerAdapter(Context context, List<ListProduct> list,OnItemSelectedListener listener) {
        this.context=context;
        this.list = list;
        this.listener=listener;
    }


    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.necklace_recyc,null);
        HolderView holderView=new HolderView(view);
        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        ListProduct details = list.get(position);
        holder.getLayoutPosition();
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaText-Regular.ttf");
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.networkImageView.setImageUrl(details.getImg1(), mImageLoader);
        holder.networkImageView.setDefaultImageResId(R.drawable.logo);
        holder.name_price.setText(details.getName()+"\n"+"Rs."+details.getPrice());
        holder.name_price.setTypeface(tf);


    }
    public void clearAdapter() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(list.get(position).getProductId());
    }

    public class HolderView extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        protected NetworkImageView networkImageView;
        protected TextView name_price;

    public HolderView(View itemView) {
        super(itemView);
        this.networkImageView = (NetworkImageView) itemView.findViewById(R.id.necklace);
        this.name_price=(TextView)  itemView.findViewById(R.id.name_price);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
    }

        @Override
        public void onClick(View v)
        {
            listener.onItemSelected(getAdapterPosition());
        }

    }
}
