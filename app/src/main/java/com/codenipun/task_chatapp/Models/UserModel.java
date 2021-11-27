package com.codenipun.task_chatapp.Models;

public class UserModel {
    String Uname;
    String Uid;
    String profile;

    public UserModel(String uname, String uid, String profile) {
        Uname = uname;
        Uid = uid;
        this.profile = profile;
    }
    public UserModel(){}
    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
