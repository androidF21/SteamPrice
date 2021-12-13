package com.example.csgomoney.models;

import org.parceler.Parcel;

@Parcel
public class Item {
    String name;
    String market_hash_name;
    String icon;
    String price;
    String description;
    String NameColor;
    User loggedInUser;

    // empty constructor for parcelable
    public Item() { }

    public Item(String name, String market_hash_name, String icon, String description){
        this.name=name;
        this.market_hash_name=market_hash_name;
        this.icon=icon;
        this.description=description;
    }

    public Item(String name, String market_hash_name, String icon, String description, String price){
        this.name=name;
        this.market_hash_name=market_hash_name;
        this.icon=icon;
        this.description=description;
        this.price=price;
    }

    public Item(String name, String market_hash_name, String icon){
        this.name=name;
        this.market_hash_name=market_hash_name;
        this.icon=icon;
    }

    public Item(User loggedInUser){
        this.loggedInUser=loggedInUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket_hash_name() {
        return market_hash_name;
    }

    public void setMarket_hash_name(String name) {
        this.market_hash_name = market_hash_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNameColor(String NameColor){ this.NameColor = NameColor;}
}
