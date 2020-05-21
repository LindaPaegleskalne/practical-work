package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import lv.bootcamp.practical.work.categories.CategoryRepository;
import lv.bootcamp.practical.work.movies.Movie;
import lv.bootcamp.practical.work.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class MoviesService {
    private static final Integer DEFAULT_PAGE_NR = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 5;

    private final MovieRepository movieRepository;

    @Autowired
    public MoviesService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> findMoviesByCategory(Integer id, String search, Optional<Integer> page){
        Page<Movie> movies;
        Pageable pageable = PageRequest.of(page.orElse(DEFAULT_PAGE_NR) - 1,
                DEFAULT_PAGE_SIZE, Sort.by("name"));
        if (isBlank(search)) {
            movies = movieRepository.findByCategoryId(id, pageable);
        } else {
            movies = movieRepository.findByNameAndId(search, id, pageable);
        }
        return movies;
    }

    public Iterable<Movie> findMovieByName (String search) {
        Collection<Movie> movies;
            movies = movieRepository.findMovieByName(search);
        return movies;
    }

    public Movie findAndIncrementById(Integer id) {
        Movie movie = movieRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        movieRepository.incrementMovieViews(id, 1);
        return movie;
    }

    public List<Movie> popularMovies(){
        return movieRepository.findTop5ByOrderByViewsDesc();
    }
}
