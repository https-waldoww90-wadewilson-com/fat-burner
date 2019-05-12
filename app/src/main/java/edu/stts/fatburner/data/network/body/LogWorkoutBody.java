package edu.stts.fatburner.data.network.body;

public class LogWorkoutBody {
    private int id_user;
    private int id_workout;
    private int waktu;

    public LogWorkoutBody(int id_user, int id_workout, int waktu) {
        this.id_user = id_user;
        this.id_workout = id_workout;
        this.waktu = waktu;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_workout() {
        return id_workout;
    }

    public void setId_workout(int id_workout) {
        this.id_workout = id_workout;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }
}
