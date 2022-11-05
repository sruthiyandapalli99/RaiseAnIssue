package com.example.raiseanissue;

public class userTable {


    String user;
    String phone;
    String pass;

  public userTable(){

  }
    public userTable(String user, String phone, String pass) {
        this.user = user;
        this.phone = phone;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public String getPhone() {
        return phone;
    }

    public String getPass() {
        return pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
