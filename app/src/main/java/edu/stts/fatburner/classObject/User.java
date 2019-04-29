package edu.stts.fatburner.classObject;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String username;
    private String password;
    private int goal;
    private boolean gender;
    private double weight;
    private double height;
    private double bloodsugar;
    private double cholesterol;

    public User(){

    }
    public User(String email, String username, String password, int goal, boolean gender, double weight, double height, double bloodsugar, double cholesterol) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.goal = goal;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bloodsugar = bloodsugar;
        this.cholesterol = cholesterol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(double bloodsugar) {
        this.bloodsugar = bloodsugar;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }
}
