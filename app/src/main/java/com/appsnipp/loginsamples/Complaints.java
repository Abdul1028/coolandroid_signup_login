package com.appsnipp.loginsamples;

public class Complaints {
    private String name,contact,email,complaint;

    public Complaints(String name, String contact, String email, String complaint) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.complaint = complaint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
