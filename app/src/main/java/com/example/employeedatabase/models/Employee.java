package com.example.employeedatabase.models;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String name;
    private String designation;
    private String field;
    private String email;
    private int phone;
    private int salary;
    private int photo;
    public Employee(){ }


    public Employee(int id, String name, String designation, String field, String email, int phone, int salary, int photo) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.field = field;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.photo = photo;
    }

    /*public Employee(String name, String designation, String field) {
        this.name = name;
        this.designation = designation;
        this.field = field;
    }*/

    public int getId() {
        return id;
    }

    public int getPhoto() {
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

    public int getPhone() {
        return phone;
    }

    public int getSalary() {
        return salary;
    }


    public void setId(int id) { this.id = id;   }

    public void setName(String name) { this.name = name; }

    public void setDesignation(String designation) { this.designation = designation;    }

    public void setField(String field) { this.field = field;   }

    public void setEmail(String email) { this.email = email; }

    public int setPhone(int phone) { this.phone = phone;
        return phone;
    }

    public int setSalary(int salary) { this.salary = salary;
        return salary;
    }

    public void setPhoto(int photo) { this.photo = photo;  }
}
