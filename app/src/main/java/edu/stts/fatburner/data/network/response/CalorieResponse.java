package edu.stts.fatburner.data.network.response;

public class CalorieResponse {
    private double calorie;
    private double weight;
    private double height;
    private int bloodsugar;
    private int cholesterol;
    private int goal;

    public CalorieResponse(double calorie, double weight, double height, int bloodsugar, int cholesterol, int goal) {
        this.calorie = calorie;
        this.weight = weight;
        this.height = height;
        this.bloodsugar = bloodsugar;
        this.cholesterol = cholesterol;
        this.goal = goal;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
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

    public int getBloodsugar() {
        return bloodsugar;
    }

    public void setBloodsugar(int bloodsugar) {
        this.bloodsugar = bloodsugar;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }
}
