package lv.bootcamp.practical.work.movies.omdb;

import java.util.ArrayList;
import java.util.List;

public class OmdbResponse {
    private List<OmdbMovie> omdbMovieList;

    public OmdbResponse() {
        omdbMovieList= new ArrayList<>();
    }

    public List<OmdbMovie> getOmdbMovieList() {
        return omdbMovieList;
    }

    public void setOmdbMovieList(List<OmdbMovie> omdbMovieList) {
        this.omdbMovieList = omdbMovieList;
    }
}
