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
    public OmdbMovie searchByTitle(String title){
        for( OmdbMovie movie : movies){
            if(movie.getTitle().equals(title)){
                return movie;
            }
        }
        return null;
    }
}
