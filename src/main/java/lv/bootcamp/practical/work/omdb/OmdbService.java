package lv.bootcamp.practical.work.omdb;

import lv.bootcamp.practical.work.movies.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmdbService {
    private final OmdbGateway omdbGateway;
    private OmdbResponse omdbResponse;

    @Autowired
    public OmdbService(OmdbGateway omdbGateway) {
        this.omdbGateway = omdbGateway;
    }

    public List<OmdbMovie> findOmdbMovies(String search){
        omdbResponse = omdbGateway.movies(search);
        return omdbResponse.getMovies();
    }

    public Movie addOmdbMovie(String title) {
        OmdbMovie omdbMovie = omdbResponse.searchByTitle(title);
        return omdbMovie.addOmdbMovie();
    }
}
