package edu.stts.fatburner.data.network.body;

public class AddArticleBody {
    private int user_id;
    private String judul;
    private String isi;

    public AddArticleBody(int user_id, String judul, String isi) {
        this.user_id = user_id;
        this.judul = judul;
        this.isi = isi;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
