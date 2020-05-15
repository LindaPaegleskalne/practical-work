package lv.bootcamp.practical.work.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MoviesAdminService {
    private static final Integer DEFAULT_PAGE_NR = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 5;

    private final MovieRepository movieRepository;

    @Autowired
    public MoviesAdminService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Page<Movie> startPage(String search, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(DEFAULT_PAGE_NR) - 1,
                DEFAULT_PAGE_SIZE, Sort.by("name"));
        return movieRepository.findByName(search, pageable);
    }

    public Page<Movie> defaultStartPage() {
        Pageable pageable = PageRequest.of(DEFAULT_PAGE_NR - 1, DEFAULT_PAGE_SIZE, Sort.by("name"));
        return movieRepository.findByName("", pageable);
    }

    public Movie findByIdMovie(Integer id) {
        return movieRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
    }

    public Movie createMovie(Movie movie) {
        movieRepository.save(movie);
        return movie;
    }

    public Movie deleteMovie(Movie movie) {
        movieRepository.delete(movie);
        return movie;
    }
}
