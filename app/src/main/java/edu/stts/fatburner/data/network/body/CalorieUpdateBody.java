package edu.stts.fatburner.data.network.body;

public class CalorieUpdateBody {
    private double calorie;

    public CalorieUpdateBody(double calorie) {
        this.calorie = calorie;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }
}
