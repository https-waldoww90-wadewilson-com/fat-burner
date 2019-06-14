package edu.stts.fatburner.data.network.response;

public class ScheduleResponse {
    private String id_log;
    private String nama;
    private String tipe;

    public ScheduleResponse(String id_log, String nama, String tipe) {
        this.id_log = id_log;
        this.nama = nama;
        this.tipe = tipe;
    }

    public String getId_log() {
        return id_log;
    }

    public void setId_log(String id_log) {
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
}
