package edu.stts.fatburner.data.network.body;

public class ScheduleBody {
    private int id_user;
    private int id_food;
    private String time;
    private String tipe;

    public ScheduleBody(int id_user, int id_food, String time, String tipe) {
        this.id_user = id_user;
        this.id_food = id_food;
        this.time = time;
        this.tipe = tipe;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
