package edu.stts.fatburner.data.model;

public class Article {
    private String artikelid;
    private String judul;
    private String nama;
    private String imageurl;
    private String datecreated;
    private String isi;

    public Article(String artikelid, String judul, String nama, String imageurl, String datecreated, String isi) {
        this.artikelid = artikelid;
        this.judul = judul;
        this.nama = nama;
        this.imageurl = imageurl;
        this.datecreated = datecreated;
        this.isi = isi;
    }

    public String getArtikelid() {
        return artikelid;
    }

    public void setArtikelid(String artikelid) {
        this.artikelid = artikelid;
    }

    public String getJudul() { return judul; }

    public void setJudul(String judul) { this.judul = judul; }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
