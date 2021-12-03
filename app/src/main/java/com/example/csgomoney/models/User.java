package com.example.csgomoney.models;

import org.parceler.Parcel;

@Parcel
public class User {
    String name;
    String value;
    String avatar;

    public User(String name, String value, String avatar){
        this.name=name;
        this.value=value;
        this.avatar=avatar;
    }

    public User(String name, String avatar){
        this.name=name;
        this.avatar=avatar;
    }

    public User(){

    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
