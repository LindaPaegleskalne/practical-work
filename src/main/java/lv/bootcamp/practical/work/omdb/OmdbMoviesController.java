package lv.bootcamp.practical.work.omdb;

import lv.bootcamp.practical.work.categories.CategoriesAdminService;
import lv.bootcamp.practical.work.movies.MoviesAdminService;
import lv.bootcamp.practical.work.movies.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class OmdbMoviesController {
    private final OmdbService omdbService;
    private final MoviesAdminService moviesAdminService;
    private final CategoriesAdminService categoriesAdminService;

    @Autowired
    public OmdbMoviesController(OmdbService omdbService, MoviesAdminService moviesAdminService, CategoriesAdminService categoriesAdminService) {
        this.omdbService = omdbService;
        this.moviesAdminService = moviesAdminService;
        this.categoriesAdminService = categoriesAdminService;
    }

    @GetMapping("/admin/omdbmoviesearch")
    public String omdbMovieSearch(@RequestParam(required = false) String search, Model model){
        model.addAttribute("omdbMovies", omdbService.findOmdbMovies(search));
        return "admin/omdb-movies";
    }

    @GetMapping("/admin/editomdbmovie/{title}")
    public String showUpdateFormOmdbMovie(@PathVariable("title") String title, Model model){
        model.addAttribute("movie", omdbService.cloneMovieFromOmdb(title));
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/add-omdb-movie";
    }

    @PostMapping("/admin/addomdbmovie/{title}")
    public String addOmdbMovie (@PathVariable("title") String title, @Valid Movie movie, BindingResult bindingResult, Model model) {
            if(bindingResult.hasErrors()) {
                model.addAttribute("categories", categoriesAdminService.findAllCategory());
                return "admin/add-omdb-movie";
            }
        moviesAdminService.createMovie(movie);
        model.addAttribute("movies", moviesAdminService.defaultStartPage());
        model.addAttribute("searchStr", "");
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/index";
    }
}
