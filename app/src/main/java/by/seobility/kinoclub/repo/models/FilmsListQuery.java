package by.seobility.kinoclub.repo.models;

public class FilmsListQuery {
    private String search;
    private int page;
    private String orderby;
    private String order;
    private String genre;
    private String country;
    private String quality;
    private String cat;
    private String year;

    public FilmsListQuery(String search, int page, String orderby, String order, String genre, String country, String quality, String cat, String year) {
        this.search = search;
        this.page = page;
        this.orderby = orderby;
        this.order = order;
        this.genre = genre;
        this.country = country;
        this.quality = quality;
        this.cat = cat;
        this.year = year;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
