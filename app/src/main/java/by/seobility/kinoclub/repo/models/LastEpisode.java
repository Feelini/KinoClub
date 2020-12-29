package by.seobility.kinoclub.repo.models;

public class LastEpisode {
    private String season;
    private String episode;

    public LastEpisode(String season, String episode) {
        this.season = season;
        this.episode = episode;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
