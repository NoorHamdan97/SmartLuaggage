package com.example.suitcase;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;

import java.util.List;

@IgnoreExtraProperties
public class Suitcase {
    private String SC_Name;
    private String SC_SN;
    private boolean SC_Switch;
    private boolean SC_InUse;
    private int SC_UsersNum;
    private List<String> SC_Users;
    private @ServerTimestamp
    Date timestamp;

    public Suitcase(String SC_Name, String SC_SN, boolean SC_Switch, boolean SC_InUse, int SC_UsersNum, List<String> SC_Users) {
        this.SC_Name = SC_Name;
        this.SC_SN = SC_SN;
        this.SC_Switch = SC_Switch;
        this.SC_InUse = SC_InUse;
        this.SC_UsersNum = SC_UsersNum;
        this.SC_Users = SC_Users;
    }

    public Suitcase() {
    }

    public String getSC_Name() {
        return SC_Name;
    }

    public void setSC_Name(String SC_Name) {
        this.SC_Name = SC_Name;
    }

    public String getSC_SN() {
        return SC_SN;
    }

    public void setSC_SN(String SC_SN) {
        this.SC_SN = SC_SN;
    }

    public boolean isSC_Switch() {
        return SC_Switch;
    }

    public void setSC_Switch(boolean SC_Switch) {
        this.SC_Switch = SC_Switch;
    }

    public boolean isSC_InUse() {
        return SC_InUse;
    }

    public void setSC_InUse(boolean SC_InUse) {
        this.SC_InUse = SC_InUse;
    }

    public int getSC_UsersNum() {
        return SC_UsersNum;
    }

    public void setSC_UsersNum(int SC_UsersNum) {
        this.SC_UsersNum = SC_UsersNum;
    }

    public List<String> getSC_Users() {
        return SC_Users;
    }

    public void setSC_Users(List<String> SC_Users) {
        this.SC_Users = SC_Users;
    }
}
