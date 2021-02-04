package by.seobility.kinoclub.repo.models;

import java.util.List;

public class RowForChooseList {
    private String status;
    private List<RowForChoose> data;
    private String message;

    public RowForChooseList(String status, List<RowForChoose> data, String message) {
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

    public List<RowForChoose> getData() {
        return data;
    }

    public void setCategories(List<RowForChoose> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
