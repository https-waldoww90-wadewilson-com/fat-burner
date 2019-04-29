package edu.stts.fatburner.classObject;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;
    private String kategori;
    private String nama;
    private String kalori;
    private String satuan;
    private String berat;
    private int jumlah;

    public Food(String id, String kategori, String nama, String kalori, String satuan, String berat, int jumlah) {
        this.id = id;
        this.kategori = kategori;
        this.nama = nama;
        this.kalori = kalori;
        this.satuan = satuan;
        this.berat = berat;
        this.jumlah = jumlah;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
