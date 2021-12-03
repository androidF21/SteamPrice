package com.example.csgomoney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.csgomoney.models.Item;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    TextView itemName;
    ImageView itemImage;
    TextView itemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = findViewById(R.id.itemName);
        itemImage = findViewById(R.id.itemImage);
        itemPrice = findViewById(R.id.itemPrice);

        Item item = Parcels.unwrap(getIntent().getParcelableExtra("item"));
        itemName.setText(item.getName());
        itemPrice.setText(item.getPrice());
        Glide.with(this).load("http://cdn.steamcommunity.com/economy/image/" + item.getIcon()).into(itemImage);

    }
}