package by.seobility.kinoclub.repo.models;

public class YearsList {
    private String status;
    private Years data;
    private String message;

    public YearsList(String status, Years data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Years getData() {
        return data;
    }

    public void setData(Years data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
