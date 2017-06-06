package com.ecnu.entity;

import android.app.Application;

/**
 * Created by CY on 2017/4/16.
 */

public class User extends Application{
    private int id;
    private String username;
    private String password;
    private int sex;
    private int age;
    private String address;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String photo;

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User(String username, String password, int sex, int age, String address, String photo) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
