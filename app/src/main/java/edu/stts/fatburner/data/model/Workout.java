package edu.stts.fatburner.data.model;

import java.io.Serializable;

public class Workout implements Serializable {
    private String id;
    private String nama;
    private int kalori;
    private int waktu;

    public Workout(String id, String nama, int kalori, int waktu) {
        this.id = id;
        this.nama = nama;
        this.kalori = kalori;
        this.waktu = waktu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }
}
