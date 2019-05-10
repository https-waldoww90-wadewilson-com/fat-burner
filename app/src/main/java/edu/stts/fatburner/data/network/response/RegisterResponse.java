package edu.stts.fatburner.data.network.response;

public class RegisterResponse {
    private boolean error;
    private String message;

    public RegisterResponse(boolean error, String message) {
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
