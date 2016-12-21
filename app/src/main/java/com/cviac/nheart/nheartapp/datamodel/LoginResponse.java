package com.cviac.nheart.nheartapp.datamodel;

/**
 * Created by Cviac on 20/12/2016.
 */
public class LoginResponse {

    private String success;
    private String token;

    private ErrorInfo error;

    public LoginResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ErrorInfo getError() {
        return error;
    }

    public void setError(ErrorInfo error) {
        this.error = error;
    }
}
