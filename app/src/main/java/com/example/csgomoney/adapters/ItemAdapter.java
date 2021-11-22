package com.example.csgomoney.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.csgomoney.R;
import com.example.csgomoney.models.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    Context context;
    List<Item> items;

    public ItemAdapter(Context context, List<Item> items){
        this.context=context;
        this.items=items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item=items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivIcon;
        TextView tvName;
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            ivIcon=itemView.findViewById(R.id.ivIcon);
        }

        public void bind(Item item) {
            tvName.setText(item.getName());
            tvPrice.setText(item.getPrice());
            Glide.with(context).load("http://cdn.steamcommunity.com/economy/image/"+item.getIcon()).into(ivIcon);
        }
    }
}
