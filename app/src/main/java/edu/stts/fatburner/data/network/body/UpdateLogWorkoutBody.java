package edu.stts.fatburner.data.network.body;

public class UpdateLogWorkoutBody {
    private int waktu;

    public UpdateLogWorkoutBody(int waktu) {
        this.waktu = waktu;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }
}
