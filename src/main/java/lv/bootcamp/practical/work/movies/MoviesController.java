package lv.bootcamp.practical.work.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collection;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class MoviesController {

    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public MoviesController(CategoryRepository categoryRepository, MovieRepository movieRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/category/{id}")
    public String showCategory(@PathVariable("id") int id,
                               @RequestParam(required = false) String search, Model model) {
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
        //model.addAttribute("movies", movies);
        return "category";
    }

}

