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
    public OmdbService(OmdbGateway omdbGateway, OmdbResponse omdbResponse) {
        this.omdbGateway = omdbGateway;
        this.omdbResponse = omdbResponse;
    }

    public List<OmdbMovie> findOmdbMovies(String search) {
        omdbResponse = omdbGateway.movies(search);
        return omdbResponse.getMovies();
    }

    public Movie findOmdbMovieByTitle(String title) {
        Movie movie = new Movie();
        for( OmdbMovie omdbMovie : omdbResponse.getMovies()){
            if(omdbMovie.getTitle().equals(title)){
                movie = returnMovie(omdbMovie);
            }
        }
        return movie;
    }

    public Movie returnMovie(OmdbMovie omdbMovie){
        Movie movie = new Movie();
        movie.setName(omdbMovie.getTitle());
        movie.setYear(omdbMovie.getYear());
        movie.setLinkImdb("https://www.imdb.com/title/" + omdbMovie.getImdbID());
        movie.setLinkPoster(omdbMovie.getPoster().equals("N/A") ? "" : omdbMovie.getPoster());
        return movie;
    }
}
