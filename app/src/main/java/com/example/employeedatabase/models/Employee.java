package com.example.employeedatabase.models;

import java.io.Serializable;

public class Employee implements Serializable {
    private String id;
    private String name;
    private String designation;
    private String field;
    private String email;
    private long phone;
    private double salary;
    private String photo;

    public String getId() {
        return id;
    }


    public String getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getField() {
        return field;
    }

    public String getEmail() {
        return email;
    }

    public long getPhone() {
        return phone;
    }

    public double getSalary() {
        return salary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long setPhone(long phone) {
        this.phone = phone;
        return phone;
    }

    public double setSalary(double salary) {
        this.salary = salary;
        return salary;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
