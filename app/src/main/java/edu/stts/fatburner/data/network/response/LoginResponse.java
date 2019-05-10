package edu.stts.fatburner.data.network.response;

public class LoginResponse {
    private boolean error;
    private String message;

    public LoginResponse(boolean error, String message) {
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
