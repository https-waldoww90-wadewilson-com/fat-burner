package edu.stts.fatburner.data.model;

public class Article {
    private String artikelid;
    private String email;
    private String imageurl;
    private String datecreated;
    private String isi;

    public Article(String artikelid, String email, String imageurl, String datecreated, String isi) {
        this.artikelid = artikelid;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
