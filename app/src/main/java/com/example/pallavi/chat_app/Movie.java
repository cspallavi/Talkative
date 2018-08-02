package com.example.pallavi.chat_app;

/**
 * Created by Pallavi on 28/06/2017.
 */

public class Movie {
    private String username,user_status;int senderid;String image1;

    public Movie() {
    }

    public Movie(String username,String user_status,int senderid,String image1) {
        this.username = username;
        this.user_status = user_status;
        this.senderid=senderid;
        this.image1=image1;

    }

    public String getusername() {
        return username;
    }

    public void setusername(String name) {
        this.username = name;
    }

    public String getuser_status() {
        return user_status;
    }

    public void setuser_status(String user_status) {
        this.user_status = user_status;
    }
    public int getsenderid() {
        return senderid;
    }
    public String getimage() {
        return image1;
    }
    public void setsenderid(int senderid) {
        this.senderid = senderid;
    }

}
