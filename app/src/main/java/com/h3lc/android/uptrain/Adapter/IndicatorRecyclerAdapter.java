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

public class IndicatorRecyclerAdapter extends RecyclerView.Adapter<IndicatorRecyclerAdapter.DashboardItemViewHolder> {
    private List<DashboardItem> itemList;
    private Context mContext;

    public IndicatorRecyclerAdapter(Context mContext, List<DashboardItem> itemList) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public IndicatorRecyclerAdapter.DashboardItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_running_indicator, parent, false);
        return new IndicatorRecyclerAdapter.DashboardItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IndicatorRecyclerAdapter.DashboardItemViewHolder holder, int position) {
        bindNameTextView(holder, position);
        bindInfoTextView(holder, position);
        bindImage(holder, position);
    }

    private void bindNameTextView(IndicatorRecyclerAdapter.DashboardItemViewHolder holder, int position) {
        DashboardItem item = itemList.get(position);
        holder.nameTextView.setText(item.getName());
    }

    private void bindInfoTextView(IndicatorRecyclerAdapter.DashboardItemViewHolder holder, int position) {
        DashboardItem item = itemList.get(position);
        holder.infoTextView.setText(item.getInfo());
    }

    private void bindImage(IndicatorRecyclerAdapter.DashboardItemViewHolder holder, int position) {
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