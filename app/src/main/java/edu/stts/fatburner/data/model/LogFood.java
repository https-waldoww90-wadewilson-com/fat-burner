package edu.stts.fatburner.data.model;

public class LogFood {
    private int id_log;
    private String nama;
    private String tipe;
    private String tanggal;
    private int satuan;
    private int kalori;
    private int berat;

    public LogFood(int id_log, String nama, String tipe, String tanggal, int satuan, int kalori, int berat) {
        this.id_log = id_log;
        this.nama = nama;
        this.tipe = tipe;
        this.tanggal = tanggal;
        this.satuan = satuan;
        this.kalori = kalori;
        this.berat = berat;
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

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getSatuan() {
        return satuan;
    }

    public void setSatuan(int satuan) {
        this.satuan = satuan;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }
}
