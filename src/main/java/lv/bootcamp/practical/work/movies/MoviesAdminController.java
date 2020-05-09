package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.CategoriesAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;
import java.util.Optional;


@Controller
public class MoviesAdminController {

    private final MoviesAdminService moviesAdminService;
    private final CategoriesAdminService categoriesAdminService;


    @Autowired
    public MoviesAdminController(MoviesAdminService moviesAdminService, CategoriesAdminService categoriesAdminService) {
        this.moviesAdminService = moviesAdminService;
        this.categoriesAdminService = categoriesAdminService;
    }

    @GetMapping("/admin")
    public String admin(@RequestParam(required = false) String search, Model model,
                        @RequestParam(required = false) Optional<Integer> page){
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        model.addAttribute("searchStr", search);
        model.addAttribute("movies", moviesAdminService.startPage(search, page));
        return "admin/index";
    }

    @GetMapping("/admin/signupmovie")
    public String showSignUpFormMovie(Movie movie, Model model){
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/add-movie";
    }

    @PostMapping("/admin/addmovie")
    public String addMovie(@Valid Movie movie, Model model){
        moviesAdminService.createMovie(movie);
        model.addAttribute("movies", moviesAdminService.defaultStartPage());
        model.addAttribute("searchStr", "");
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/editmovie/{id}")
    public String showUpdateFormMovie(@PathVariable("id") Integer id, Model model){
        Movie movie = moviesAdminService.findByIdMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/update-movie";
    }

    @PostMapping("/admin/updatemovie/{id}")
    public String updateMovie (@PathVariable("id") Integer id, @Valid Movie movie, Model model) {
        moviesAdminService.createMovie(movie);
        model.addAttribute("movies", moviesAdminService.defaultStartPage());
        model.addAttribute("searchStr", "");
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/deletemovie/{id}")
    public String deleteMovie(@PathVariable("id") Integer id, Model model){
        Movie movie = moviesAdminService.findByIdMovie(id);
        moviesAdminService.deleteMovie(movie);
        model.addAttribute("movies", moviesAdminService.defaultStartPage());
        model.addAttribute("searchStr", "");
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/index";
    }
}
