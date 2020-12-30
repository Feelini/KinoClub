package by.seobility.kinoclub.repo.models;

public class SimilarFilms {
    private long film_id;

    public SimilarFilms(long film_id) {
        this.film_id = film_id;
    }

    public long getFilm_id() {
        return film_id;
    }

    public void setFilm_id(long film_id) {
        this.film_id = film_id;
    }
}
