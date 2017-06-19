package com.dh.chat.model;

import java.util.List;

/**
 * Created by Juan Zapata on 6/16/2017.
 */
public class Group {
    private long id;
    private String name,password;
    private User admin;
    private Boolean isPrivate;
    private List<User> users;
    private Boolean isVisible;

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Group(long id, String name, String password, User admin, Boolean isPrivate, List<User> users) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.isPrivate = isPrivate;
        this.users = users;
        this.isVisible = !isPrivate;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString(){
        return "Group: "+getName()+" ("+(getPrivate()?"Private":"Public")+")";
    }
}
