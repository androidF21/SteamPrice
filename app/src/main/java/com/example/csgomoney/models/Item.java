package com.example.csgomoney.models;

public class Item {
    String name;
    String market_hash_name;
    String icon;
    String price;

    public Item(String name, String market_hash_name, String icon, String price){
        this.name=name;
        this.market_hash_name=market_hash_name;
        this.icon=icon;
        this.price=price;
    }

    public Item(String name, String market_hash_name, String icon){
        this.name=name;
        this.market_hash_name=market_hash_name;
        this.icon=icon;
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
}
