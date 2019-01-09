package com.appristine.clusterclear.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.appristine.clusterclear.R;
import com.appristine.clusterclear.listners.RecyclerItemClickListener;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FrameRecycler extends RecyclerView.Adapter<FrameRecycler.ViewHolder> {

    public ArrayList<String> imgurl;
    RecyclerItemClickListener mClickListener;
    Context mContext;



    public FrameRecycler(ArrayList<String> url,RecyclerItemClickListener listener,Context context) {
        imgurl = url;
        mClickListener = listener;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView item;
        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.iv_item);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View framelayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_adapter_item,null);
        ViewHolder viewHolder = new ViewHolder(framelayout);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(imgurl.get(position))
                .into(holder.item);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onRecyclerItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return imgurl.size();
    }
}
