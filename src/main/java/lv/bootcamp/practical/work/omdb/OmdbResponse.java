package lv.bootcamp.practical.work.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OmdbResponse {
    @JsonProperty("Search")
  private List<OmdbMovie> movies;

    public List<OmdbMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<OmdbMovie> movies) {
        this.movies = movies;
    }

}
