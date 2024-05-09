package com.h3lc.android.uptrain.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.h3lc.android.uptrain.R;
import com.h3lc.android.uptrain.Pojo.DashboardItem;

import java.util.List;

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardItemViewHolder> {
    private List<DashboardItem> itemList;
    private Context mContext;

    public DashboardRecyclerAdapter(Context mContext, List<DashboardItem> itemList) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DashboardItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_dashboard, parent, false);
        return new DashboardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardItemViewHolder holder, int position) {
        bindNameTextView(holder, position);
        bindInfoTextView(holder, position);
        bindImage(holder, position);
    }

    private void bindNameTextView(DashboardItemViewHolder holder, int position) {
        DashboardItem item = itemList.get(position);
        holder.nameTextView.setText(item.getName());
    }

    private void bindInfoTextView(DashboardItemViewHolder holder, int position) {
        DashboardItem item = itemList.get(position);
        holder.infoTextView.setText(item.getInfo());
    }

    private void bindImage(DashboardItemViewHolder holder, int position) {
        DashboardItem item = itemList.get(position);
        Drawable imageDrawable = item.getImg();
        holder.imageView.setImageDrawable(imageDrawable);
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class DashboardItemViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView infoTextView;
        public ImageView imageView;

        public DashboardItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvName);
            infoTextView = itemView.findViewById(R.id.tvInfo);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}