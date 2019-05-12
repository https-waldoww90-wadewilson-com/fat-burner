package edu.stts.fatburner.data.network.body;

public class UpdateLogFoodBody {
    private int satuan;

    public UpdateLogFoodBody(int satuan) {
        this.satuan = satuan;
    }

    public int getSatuan() {
        return satuan;
    }

    public void setSatuan(int satuan) {
        this.satuan = satuan;
    }
}
