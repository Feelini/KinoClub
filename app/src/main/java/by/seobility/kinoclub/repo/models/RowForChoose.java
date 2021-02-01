package by.seobility.kinoclub.repo.models;

public class RowForChoose {
    private long id;
    private String name;
    private String plural;

    public RowForChoose(long id, String name, String plural) {
        this.id = id;
        this.name = name;
        this.plural = plural;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlural() {
        return plural;
    }

    public void setPlural(String plural) {
        this.plural = plural;
    }
}
