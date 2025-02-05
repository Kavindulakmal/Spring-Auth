package com.example.demo.response;

public class LoginResponse {

    String message;
    Boolean status;

    public LoginResponse(String message, boolean status) {
        this.message = message;
        this.status = Boolean.valueOf(status);
    }

    public LoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Boolean.valueOf(status);
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
