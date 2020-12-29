package by.seobility.kinoclub.repo.models;

import java.util.List;

public class FilmsList {
    private String status;
    private List<Film> data;
    private String message;

    public FilmsList(String status, List<Film> data, String message) {
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

    public List<Film> getData() {
        return data;
    }

    public void setData(List<Film> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
