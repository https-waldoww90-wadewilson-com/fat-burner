package edu.stts.fatburner.classObject;

public class Article {
    public String nama, tanggal, isi;

    public Article(String nama, String tanggal, String isi) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.isi = isi;
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

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
