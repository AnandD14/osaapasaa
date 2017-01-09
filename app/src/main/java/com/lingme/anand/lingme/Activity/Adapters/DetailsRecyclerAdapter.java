package com.lingme.anand.lingme.Activity.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.lingme.anand.lingme.Activity.AppLocalStore;
import com.lingme.anand.lingme.Activity.DatabaseHelper;
import com.lingme.anand.lingme.Activity.DetailsActivity;
import com.lingme.anand.lingme.Activity.HomeActivity;
import com.lingme.anand.lingme.Activity.Listeners.OnItemSelectedListener;
import com.lingme.anand.lingme.Activity.Listeners.Select;
import com.lingme.anand.lingme.Activity.Listeners.SelectSize;
import com.lingme.anand.lingme.Activity.MySingleton;
import com.lingme.anand.lingme.Activity.Pojo.ListProduct;
import com.lingme.anand.lingme.R;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by nepal on 6/11/2015.
 */
public class DetailsRecyclerAdapter extends RecyclerView.Adapter<DetailsRecyclerAdapter.ListHolderView> {
    int x=1,y=2,z=0;
    private List<ListProduct> list;
    private ImageLoader mImageLoader;
    private Context context;
    String checked;
    String img1,img2,img3;
    DatabaseHelper db;
    public DetailsRecyclerAdapter(Context context, List<ListProduct> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public ListHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details, null);
        ListHolderView holderView = new ListHolderView(view);
        return holderView;
    }

    @Override
    public void onBindViewHolder(final ListHolderView holder, int position) {
        db = new DatabaseHelper(context);
        final ListProduct details = list.get(position);
        mImageLoader = MySingleton.getInstance(context).getImageLoader();
        holder.networkImageView1.setImageUrl(details.getImg1(), mImageLoader);
        holder.networkImageView1.setDefaultImageResId(R.drawable.logo);
        holder.name_product.setText("Name: " + details.getName().toString());
        holder.price.setText("Price: Rs." + details.getPrice() + "");
        holder.brand.setText("Brand: " + details.getBrand());
        holder.stock.setText("Stock: " + details.getStock());
        holder.description_detail.setText(details.getDescription());
        holder.m.setText(details.getM());
        holder.s.setText(details.getS());
        holder.l.setText(details.getL());
        holder.xl.setText(details.getXl());
        holder.xxl.setText(details.getXxl());
        holder.product_code.setText("Product Code: " + details.getProductId());
        holder.networkImageView2.setImageUrl(details.getImg2(), mImageLoader);
        holder.networkImageView2.setDefaultImageResId(R.drawable.logo);
        holder.networkImageView3.setImageUrl(details.getImg3(), mImageLoader);
        holder.networkImageView3.setDefaultImageResId(R.drawable.logo);
        holder.networkImageView1.setTag(details.getImg1());
        holder.networkImageView2.setTag(details.getImg2());
        holder.networkImageView3.setTag(details.getImg3());
        img1 = holder.networkImageView1.getTag().toString();
        img2 = holder.networkImageView2.getTag().toString();
        img3 = holder.networkImageView3.getTag().toString();
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "OsaapasaaText-Regular.ttf");
        holder.name_product.setTypeface(tf);
        holder.product_code.setTypeface(tf);
        holder.price.setTypeface(tf);
        holder.brand.setTypeface(tf);
        holder.description_detail.setTypeface(tf);
        holder.m.setTypeface(tf);
        holder.s.setTypeface(tf);
        holder.l.setTypeface(tf);
        holder.xxl.setTypeface(tf);
        holder.xl.setTypeface(tf);
        holder.description.setText("Description:");
        holder.description.setTypeface(tf);
        holder.size.setText("Size:");
        holder.size.setTypeface(tf);
        holder.networkImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.networkImageView1.getTag().toString().equals(img2)){
                    if(holder.networkImageView2.getTag().toString().equals(img3)) {
                        holder.networkImageView1.setImageUrl(details.getImg3(), mImageLoader);
                        holder.networkImageView2.setImageUrl(details.getImg2(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg3());
                        holder.networkImageView2.setTag(details.getImg2());
                    }else {
                        holder.networkImageView1.setImageUrl(details.getImg1(), mImageLoader);
                        holder.networkImageView2.setImageUrl(details.getImg2(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg1());
                        holder.networkImageView2.setTag(details.getImg2());
                    }
                }
                else if(holder.networkImageView1.getTag().toString().equals(img3)){
                    if(holder.networkImageView2.getTag().toString().equals(img2)) {
                        holder.networkImageView1.setImageUrl(details.getImg2(), mImageLoader);
                        holder.networkImageView2.setImageUrl(details.getImg3(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg2());
                        holder.networkImageView2.setTag(details.getImg3());
                    }
                    else {
                        holder.networkImageView1.setImageUrl(details.getImg3(), mImageLoader);
                        holder.networkImageView3.setImageUrl(details.getImg1(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg3());
                        holder.networkImageView3.setTag(details.getImg1());
                    }
                }
                else
                {
                    holder.networkImageView1.setImageUrl(details.getImg2(), mImageLoader);
                    holder.networkImageView2.setImageUrl(details.getImg1(), mImageLoader);
                    holder.networkImageView1.setTag(details.getImg2());
                    holder.networkImageView2.setTag(details.getImg1());
                }
            }
        });
        holder.networkImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.networkImageView1.getTag().toString().equals(img2)){
                    if(holder.networkImageView3.getTag().toString().equals(img1)) {
                        holder.networkImageView1.setImageUrl(details.getImg1(), mImageLoader);
                        holder.networkImageView3.setImageUrl(details.getImg2(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg1());
                        holder.networkImageView3.setTag(details.getImg2());
                    }else {
                        holder.networkImageView1.setImageUrl(details.getImg3(), mImageLoader);
                        holder.networkImageView3.setImageUrl(details.getImg1(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg3());
                        holder.networkImageView3.setTag(details.getImg1());
                    }
                }
                else if(holder.networkImageView1.getTag().toString().equals(img3)){
                    if(holder.networkImageView3.getTag().toString().equals(img2)) {
                        holder.networkImageView1.setImageUrl(details.getImg2(), mImageLoader);
                        holder.networkImageView3.setImageUrl(details.getImg3(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg2());
                        holder.networkImageView3.setTag(details.getImg3());
                    }
                    else {
                        holder.networkImageView1.setImageUrl(details.getImg1(), mImageLoader);
                        holder.networkImageView2.setImageUrl(details.getImg2(), mImageLoader);
                        holder.networkImageView1.setTag(details.getImg1());
                        holder.networkImageView2.setTag(details.getImg2());
                    }
                }
                else
                {
                    holder.networkImageView1.setImageUrl(details.getImg3(), mImageLoader);
                    holder.networkImageView3.setImageUrl(details.getImg1(), mImageLoader);
                    holder.networkImageView1.setTag(details.getImg3());
                    holder.networkImageView3.setTag(details.getImg1());
                }
            }
        });
        if(details.getL().isEmpty() == true && details.getM().isEmpty() == true && details.getS().isEmpty() == true && details.getXl().isEmpty() == true && details.getXxl().isEmpty() == true){
            holder.l.setVisibility(View.GONE);
            holder.m.setVisibility(View.GONE);
            holder.s.setVisibility(View.GONE);
            holder.xl.setVisibility(View.GONE);
            holder.xxl.setVisibility(View.GONE);
            holder.size.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
            DetailsActivity.size = "";
        }
        if(details.getL().isEmpty() == true)
        {
            holder.l.setVisibility(View.GONE);
        }
        if(details.getS().isEmpty() == true)
        {
            holder.s.setVisibility(View.GONE);
        }
        if(details.getM().isEmpty() == true)
        {
            holder.m.setVisibility(View.GONE);
        }
        if(details.getXl().isEmpty() == true)
        {
            holder.xl.setVisibility(View.GONE);
        }
        if(details.getXxl().isEmpty() == true)
        {
            holder.xxl.setVisibility(View.GONE);
        }
        holder.l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    checked = details.getL();
                    DetailsActivity.size = checked;
                }

            }
        });
        holder.m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    checked = details.getM();
                    DetailsActivity.size = checked;
                }

            }
        });
        holder.s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    checked = details.getS();
                    DetailsActivity.size = checked;
                }

            }
        });
        holder.xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    checked = details.getXl();
                    DetailsActivity.size = checked;
                }

            }
        });
        holder.xxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    checked = details.getXxl();
                    DetailsActivity.size = checked;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }


    public class ListHolderView extends RecyclerView.ViewHolder {
        protected NetworkImageView networkImageView1, networkImageView2, networkImageView3;
        protected TextView name_product, price, brand, stock, description_detail, product_code, size , description;
        protected CheckBox m, l, s, xl, xxl;
        protected View view;
        public ListHolderView(View itemview) {
            super(itemview);
            this.networkImageView1 = (NetworkImageView) itemview.findViewById(R.id.details_image);
            this.networkImageView2 = (NetworkImageView) itemview.findViewById(R.id.back_image);
            this.networkImageView3 = (NetworkImageView) itemview.findViewById(R.id.middle_image);
            this.name_product = (TextView) itemview.findViewById(R.id.name_product);
            this.price = (TextView) itemview.findViewById(R.id.price);
            this.stock = (TextView) itemview.findViewById(R.id.stock);
            this.brand = (TextView) itemview.findViewById(R.id.brand);
            this.description_detail = (TextView) itemview.findViewById(R.id.description_detail);
            this.m = (CheckBox) itemview.findViewById(R.id.m);
            this.s = (CheckBox) itemview.findViewById(R.id.s);
            this.l = (CheckBox) itemview.findViewById(R.id.l);
            this.xl = (CheckBox) itemview.findViewById(R.id.xl);
            this.xxl = (CheckBox) itemview.findViewById(R.id.xxl);
            this.product_code = (TextView) itemview.findViewById(R.id.product_code);
            this.size = (TextView) itemview.findViewById(R.id.size);
            this.view = (View) itemview.findViewById(R.id.line3);
            this.description = (TextView) itemview.findViewById(R.id.description);
        }
    }
}