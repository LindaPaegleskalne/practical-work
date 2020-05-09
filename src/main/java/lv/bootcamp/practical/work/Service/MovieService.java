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

    public void printMovies(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
    }

    public void printMoviesByCategory(int id, String search, Model model){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: "+ id));
        model.addAttribute("category", category);

        Collection<Movie> movies;
        if (isBlank(search)) {
            movies = movieRepository.findByCategoryId(id);
        } else {
            movies = movieRepository.findByNameAndId(search, id);
        }

        if(movies.isEmpty()){
            model.addAttribute("movies","NoData");
        }else{
            model.addAttribute("movies", movies);
        }
    }
}
