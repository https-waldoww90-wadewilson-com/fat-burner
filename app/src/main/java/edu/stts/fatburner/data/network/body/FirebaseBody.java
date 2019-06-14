package edu.stts.fatburner.data.network.body;

public class FirebaseBody {
    private String firebase_key;

    public FirebaseBody(String firebase_key) {
        this.firebase_key = firebase_key;
    }

    public String getFirebase_key() {
        return firebase_key;
    }

    public void setFirebase_key(String firebase_key) {
        this.firebase_key = firebase_key;
    }
}
