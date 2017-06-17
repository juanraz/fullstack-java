package com.dh.chat.model;

import java.util.Date;

/**
 * Created by Juan Zapata on 6/16/2017.
 */
public class User extends Person{
    private String  userName,password;
    private Boolean isPremium;

    public User(long id, String fname, String lname, String email, Date dob, String userName, String password, Boolean isPremium) {
        super(id, fname, lname, email, dob);
        this.userName = userName;
        this.password = password;
        this.isPremium = isPremium;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    @Override
    public String toString(){
        return "User: "+this.getUserName()+"("+this.getFname()+" "+this.getLname()+")";
    }
}
