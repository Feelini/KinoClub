package by.seobility.kinoclub.repo.models;

import java.util.List;

public class Film {
    private long id;
    private String title;
    private String description;
    private String cat;
    private String poster;
    private String poster_md;
    private String year;
    private String rating;
    private String iframe;
    private String trailer;
    private String duration;
    private String updated;
    private String created;
    private List<String> actors;
    private List<String> directors;
    private List<String> composers;
    private List<String> countries;
    private List<String> genres;
    private LastEpisode last_episode;
    private List<String> qualites;
    private List<String> translations;

    public Film(long id, String title, String description, String cat, String poster,
                String poster_md, String year, String rating, String iframe, String trailer,
                String duration, String updated, String created, List<String> actors,
                List<String> directors, List<String> composers, List<String> countries,
                List<String> genres, LastEpisode last_episode, List<String> qualites,
                List<String> translations) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cat = cat;
        this.poster = poster;
        this.poster_md = poster_md;
        this.year = year;
        this.rating = rating;
        this.iframe = iframe;
        this.trailer = trailer;
        this.duration = duration;
        this.updated = updated;
        this.created = created;
        this.actors = actors;
        this.directors = directors;
        this.composers = composers;
        this.countries = countries;
        this.genres = genres;
        this.last_episode = last_episode;
        this.qualites = qualites;
        this.translations = translations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPoster_md() {
        return poster_md;
    }

    public void setPoster_md(String poster_md) {
        this.poster_md = poster_md;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getIframe() {
        return iframe;
    }

    public void setIframe(String iframe) {
        this.iframe = iframe;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getComposers() {
        return composers;
    }

    public void setComposers(List<String> composers) {
        this.composers = composers;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public LastEpisode getLast_episode() {
        return last_episode;
    }

    public void setLast_episode(LastEpisode last_episode) {
        this.last_episode = last_episode;
    }

    public List<String> getQualites() {
        return qualites;
    }

    public void setQualites(List<String> qualites) {
        this.qualites = qualites;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }
}
