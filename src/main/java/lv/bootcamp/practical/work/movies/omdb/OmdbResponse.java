package lv.bootcamp.practical.work.movies.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OmdbResponse {
    @JsonProperty("Search")
  private List<OmdbMovie> search;

    public List<OmdbMovie> getSearch() {
        return search;
    }

    public void setSearch(List<OmdbMovie> search) {
        this.search = search;
    }
    public OmdbMovie searchByTitle(String title){
        for( OmdbMovie movie : search){
            if(movie.getTitle().equals(title)){
                return movie;
            }
        }
        return null;
    }
}
