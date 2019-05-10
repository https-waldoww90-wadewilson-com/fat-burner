package edu.stts.fatburner.data.network.body;

public class LogFoodBody {
    private int id_user;
    private int id_food;
    private String tipe;
    private int satuan;

    public LogFoodBody(int id_user, int id_food, String tipe, int satuan) {
        this.id_user = id_user;
        this.id_food = id_food;
        this.tipe = tipe;
        this.satuan = satuan;
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

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getSatuan() {
        return satuan;
    }

    public void setSatuan(int satuan) {
        this.satuan = satuan;
    }
}
