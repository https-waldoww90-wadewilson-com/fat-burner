package edu.stts.fatburner.data.network.response;

public class LoginResponse {
    private boolean error;
    private LoginData message;

    public LoginResponse(boolean error, LoginData message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public LoginData getMessage() {
        return message;
    }

    public void setMessage(LoginData message) {
        this.message = message;
    }
}

