package lv.bootcamp.practical.work.Service;

import lv.bootcamp.practical.work.movies.Category;
import lv.bootcamp.practical.work.movies.CategoryRepository;
import lv.bootcamp.practical.work.movies.Movie;
import lv.bootcamp.practical.work.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class AdminService {
    private static final Integer DEFAULT_PAGE_NR = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 5;

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AdminService(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    public Category findByIdCategory(Integer id) {
        return categoryRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid category ID: "+ id));
    }

    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public Category deleteCategory(Category category) {
        categoryRepository.delete(category);
        return category;
    }


//    public Iterable<Movie> findAllMovie(String search, Optional<Integer> page) {
//        return movieRepository.findAll();
//    }

//    public Page<Movie> findByNameMovie(String search, Pageable pageable) {
//        return movieRepository.findByName(search, pageable);
//    }

    public Page<Movie> startPage(String search, Model model, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(DEFAULT_PAGE_NR) - 1,
                DEFAULT_PAGE_SIZE, Sort.by("name"));
        model.addAttribute("searchStr", search);
        return movieRepository.findByName(search, pageable);
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
