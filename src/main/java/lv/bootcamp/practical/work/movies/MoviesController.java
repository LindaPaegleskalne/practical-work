package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.Service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MoviesController {

    private final MovieService movieService;
    @Autowired
    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", movieService.findAllCategories());
        return "index";
    }

    @GetMapping("/category/{id}")
    public String showCategory(@PathVariable("id") int id,
                               @RequestParam(required = false) String search, Model model) {
        model.addAttribute("category", movieService.findCategory(id));
        model.addAttribute("movies", movieService.findMoviesByCategory(id,search ));
        return "category";
    }

}

