package edu.stts.fatburner.data.network.response;

public class InsertResponse {
    private boolean error;
    private String message;

    public InsertResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
