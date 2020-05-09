package lv.bootcamp.practical.work.Service;

import lv.bootcamp.practical.work.movies.Category;
import lv.bootcamp.practical.work.movies.CategoryRepository;
import lv.bootcamp.practical.work.movies.Movie;
import lv.bootcamp.practical.work.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collection;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class MovieService {

    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(CategoryRepository categoryRepository, MovieRepository movieRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
    }

    public Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
    public Category findCategory(int id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: "+ id));
        return category;
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
