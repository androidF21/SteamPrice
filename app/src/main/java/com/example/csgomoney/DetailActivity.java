package com.example.csgomoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.csgomoney.models.Item;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    TextView itemName;
    ImageView itemImage;
    TextView itemPrice;
    TextView tvDescription;
    ProgressBar loadingDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        itemName = findViewById(R.id.itemName);
        itemImage = findViewById(R.id.itemImage);
        itemPrice = findViewById(R.id.itemPrice);
        tvDescription = findViewById(R.id.tvDescription);
        loadingDesc = findViewById(R.id.loadingDesc);

        Item item = Parcels.unwrap(getIntent().getParcelableExtra("item"));
        itemName.setText(Html.fromHtml(item.getName()));
        itemPrice.setText(item.getPrice());
        Glide.with(this).load("http://cdn.steamcommunity.com/economy/image/" + item.getIcon()).into(itemImage);
        itemImage.setBackground(ContextCompat.getDrawable(this,R.drawable.border));
        String namesColor= (item.getName()).substring(0,22);
        if (namesColor.equals("<font color='#CF6A32'>")) {
            itemImage.setBackground(ContextCompat.getDrawable(this,R.drawable.stattrakborder));
        }
        if (namesColor.equals("<font color='#8650AC'>")) {
            itemImage.setBackground(ContextCompat.getDrawable(this,R.drawable.purpleborder));
        }
        if (namesColor.equals("<font color='#FFD700'>")) {
            itemImage.setBackground(ContextCompat.getDrawable(this,R.drawable.souvenirborder));
        }


        if (item.getDescription()!=null) {
            tvDescription.setText(Html.fromHtml(item.getDescription()));
        }
        else{
            tvDescription.setText("Loading");
            loadingDesc.setVisibility(View.VISIBLE);
        }

        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        if (tvDescription.getText()=="Loading"){
            loadingDesc.setVisibility(View.VISIBLE);
        }
        if (tvDescription.getText()!="Loading"){
            loadingDesc.setVisibility(View.INVISIBLE);
        }

    }
}