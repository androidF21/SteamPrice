package com.example.csgomoney.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.csgomoney.LoginActivity;
import com.example.csgomoney.ProfileActivity;
import com.example.csgomoney.R;
import com.example.csgomoney.models.Item;
import com.example.csgomoney.models.User;
import com.parse.ParseUser;

import java.security.AccessController;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static Context context;
    List<Item> items;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(context).inflate(R.layout.user_profile, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(context).inflate(R.layout.inventory_item, parent, false);
            return new ViewHolderTwo(view);
        } else {
            throw new RuntimeException("Unknown error");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int listPosition) {
        if (holder.getItemViewType() == TYPE_ONE) {
            ((ViewHolderOne) holder).bind(items.get(listPosition).getLoggedInUser());
        } else {
            ((ViewHolderTwo) holder).bind(items.get(listPosition));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).getLoggedInUser() != null) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }

    }

    static class ViewHolderOne extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvUsername;
        TextView tvValue;
        TextView tvNumber;
        Button btnLogout;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            btnLogout = itemView.findViewById(R.id.btnLogout);

        }

        public void bind(User user) {
            tvUsername.setText(user.getName());
            tvValue.setText("Total Inventory Value:");
            tvNumber.setText("$" + user.getValue());
            Glide.with(context).load(user.getAvatar()).into(ivAvatar);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseUser.logOut();
                    Intent i = new Intent(view.getContext(),LoginActivity.class);
                    context.startActivity(i);
                    //context.finish();
                }
            });
        }


    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvPrice;

        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }

        public void bind(Item item) {
            tvName.setText(item.getName());
            if (item.getPrice() != null) {
                itemView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                tvPrice.setText(item.getPrice());
            }
            Glide.with(context).load("http://cdn.steamcommunity.com/economy/image/" + item.getIcon()).into(ivIcon);
        }
    }
}
