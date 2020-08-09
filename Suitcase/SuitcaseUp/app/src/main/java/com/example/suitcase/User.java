package com.example.suitcase;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


@IgnoreExtraProperties
public class User {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private int suitcaseNum;
    private List<String> suitcases;
    private @ServerTimestamp Date timestamp;


    public User(String N,String E,String P,String PhoneNumb,List<String> SC){
        this.name =N;
        this.email=E;
        this.password=P;
        this.suitcaseNum=0;
        this.phoneNumber=PhoneNumb;
        this.suitcases= new ArrayList<String >(7);
    }

    public List<String> getSuitcases() {
        return suitcases;
    }

    public void setSuitcases(List<String> suitcases) {
        this.suitcases = suitcases;
    }

    public User(){
    }

    public int getSuitcaseNum() {
        return suitcaseNum;
    }

    public void setSuitcaseNum(int suitcaseNum) {
        this.suitcaseNum = suitcaseNum;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
//
    public void setEmail(String email) {
        this.email = email;
    }
//
    public String getEmail() {
        return this.email;
    }
//
    public void setName(String name) {
        this.name = name;
    }
//
    public String getName() {
        return name;
    }
//
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
