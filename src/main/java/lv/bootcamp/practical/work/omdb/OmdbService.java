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

    public List<OmdbMovie> findOmdbMovies(String search) {
        omdbResponse = omdbGateway.movies(search);
        return omdbResponse.getMovies();
    }

    public Movie cloneMovieFromOmdb(String title) {
        OmdbMovie omdbMovie = omdbResponse.searchByTitle(title);
        Movie movie = new Movie();
        if(omdbMovie!=null) {
            movie.setName(omdbMovie.getTitle());
            movie.setYear(omdbMovie.getYear());
            movie.setLinkImdb("https://www.imdb.com/title/" + omdbMovie.getImdbID());
            movie.setLinkPoster(omdbMovie.getPoster().equals("N/A") ? null : omdbMovie.getPoster());
        }
        return movie;
    }
}
