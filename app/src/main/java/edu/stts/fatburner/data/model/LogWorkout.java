package edu.stts.fatburner.data.model;

import java.io.Serializable;

public class LogWorkout implements Serializable {
    private int id_log;
    private String nama;
    private String tanggal;
    private int waktu_workout;
    private String waktu_def;
    private int kalori;

    public LogWorkout(int id_log, String nama, String tanggal, int waktu_workout, String waktu_def, int kalori) {
        this.id_log = id_log;
        this.nama = nama;
        this.tanggal = tanggal;
        this.waktu_workout = waktu_workout;
        this.waktu_def = waktu_def;
        this.kalori = kalori;
    }

    public int getId_log() {
        return id_log;
    }

    public void setId_log(int id_log) {
        this.id_log = id_log;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getWaktu_workout() {
        return waktu_workout;
    }

    public void setWaktu_workout(int waktu_workout) {
        this.waktu_workout = waktu_workout;
    }

    public String getWaktu_def() {
        return waktu_def;
    }

    public void setWaktu_def(String waktu_def) {
        this.waktu_def = waktu_def;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }
}
