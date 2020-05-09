package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import lv.bootcamp.practical.work.categories.CategoryRepository;
import lv.bootcamp.practical.work.movies.Movie;
import lv.bootcamp.practical.work.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class MoviesService {

    private final MovieRepository movieRepository;

    @Autowired
    public MoviesService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> findMoviesByCategory(int id, String search){
        Collection<Movie> movies;
        if (isBlank(search)) {
            movies = movieRepository.findByCategoryId(id);
        } else {
            movies = movieRepository.findByNameAndId(search, id);
        }
        return movies;
    }
}
