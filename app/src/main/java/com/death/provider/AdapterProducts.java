package com.death.provider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by rajora_sd on 3/24/2017.
 */

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.MyViewHolder> {


    Context mContext;
    private List<PaytmModel> paytmModels;
    public AdapterProducts(Context context, List<PaytmModel> paytmModels) {
        mContext = context;
        this.paytmModels = paytmModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_container, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PaytmModel paytmModel = paytmModels.get(position);
        holder.pProductName.setText(paytmModel.getpName());
        holder.pProductPrice.setText(paytmModel.getpPrice());
        Glide.with(mContext).load(paytmModel.getpImageLink())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pImageView);
    }

    @Override
    public int getItemCount() {
        return paytmModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView pImageView;
        TextView pProductName;
        TextView pProductPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            pImageView = (ImageView) itemView.findViewById(R.id.pImage);
            pProductName = (TextView) itemView.findViewById(R.id.pName);
            pProductPrice = (TextView) itemView.findViewById(R.id.price);

        }
    }
}

