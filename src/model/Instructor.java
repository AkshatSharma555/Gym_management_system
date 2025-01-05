package model;

import java.sql.Date;

public class Instructor {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Date hireDate;
    private String specialization;

    // Parameterized constructor
    public Instructor(int id, String name, String email, String phone, Date hireDate, String specialization) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.hireDate = hireDate;
        this.specialization = specialization;
    }

    // No-argument constructor (optional)
    public Instructor() {
        // Default values can be set here if needed
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
