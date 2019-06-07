package edu.stts.fatburner.data.network.response;

public class CalorieResponse {
    private double calorie;

    public CalorieResponse(double calorie) {
        this.calorie = calorie;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }
}
