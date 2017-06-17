package com.dh.chat.model;

import java.util.Date;

/**
 * Created by Juan Zapata on 6/16/2017.
 */
public class Person {
    private long    id;
    private String  fname,lname,email;
    private Date    dob;

    public Person(long id, String fname, String lname, String email, Date dob) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.dob = dob;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}
