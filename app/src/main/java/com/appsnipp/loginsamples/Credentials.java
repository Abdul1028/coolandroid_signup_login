package com.appsnipp.loginsamples;

public class Credentials {

    String email,name,pass,date_and_time;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Credentials(String email, String name, String pass, String date_and_time) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.date_and_time = date_and_time;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }


}
