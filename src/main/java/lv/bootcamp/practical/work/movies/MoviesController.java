package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MoviesController {

    private final MoviesService moviesService;
    private final CategoriesService categoriesService;

    @Autowired
    public MoviesController(MoviesService moviesService, CategoriesService categoriesService) {
        this.moviesService = moviesService;
        this.categoriesService = categoriesService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoriesService.findAllCategories());
        return "index";
    }

    @GetMapping("/search")
    public String searchResults(@RequestParam String search, Model model) {
        model.addAttribute("moviesAll", moviesService.findMovieByName(search));
        model.addAttribute("search", search);
        return "search";
    }

    @GetMapping("/category/{id}")
    public String showCategory(@PathVariable("id") Integer id, @RequestParam(required = false) String search,
                               @RequestParam(required = false) Optional<Integer> page, Model model) {
        model.addAttribute("category", categoriesService.findCategory(id));
        model.addAttribute("movies", moviesService.findMoviesByCategory(id, search, page));
        model.addAttribute("search", search);
        return "category";
    }

}

