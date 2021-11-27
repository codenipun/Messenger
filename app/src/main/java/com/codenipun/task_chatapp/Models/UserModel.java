package com.codenipun.task_chatapp.Models;

public class UserModel {
    String Uname;
    String Uid;
    String Uprofile;

    public UserModel(String uname, String uid, String profile) {
        Uname = uname;
        Uid = uid;
        this.Uprofile = profile;
    }

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
        return Uprofile;
    }

    public void setProfile(String profile) {
        this.Uprofile = profile;
    }
}
