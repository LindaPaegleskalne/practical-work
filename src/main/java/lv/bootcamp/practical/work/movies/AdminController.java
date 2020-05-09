package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.Service.AdminService;
import lv.bootcamp.practical.work.movies.omdb.OmdbMovie;
import lv.bootcamp.practical.work.movies.omdb.OmdbResponse;
import lv.bootcamp.practical.work.Service.OmdbSearchService;
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
public class AdminController {

    private final AdminService adminService;
    private OmdbSearchService omdbSearchService;
    private OmdbResponse omdbResponse;

    @Autowired
    public AdminController(AdminService adminService, OmdbSearchService omdbSearchService) {
        this.adminService = adminService;
        this.omdbSearchService = omdbSearchService;
    }

    @GetMapping("/admin")
    public String admin(@RequestParam(required = false) String search, Model model,
                        @RequestParam(required = false) Optional<Integer> page){
        model.addAttribute("categories", adminService.findAllCategory());
        model.addAttribute("movies", adminService.startPage(search, model, page));
        return "admin/index";
    }

    @GetMapping("/admin/signupcategory")
    public String showSignUpFormCategory(Category category){
        return "admin/add-category";
    }

    @PostMapping("/admin/addcategory")
    public String addCategory(@Valid Category category, Model model,
                              @RequestParam(required = false) String search,
                             @RequestParam(required = false) Optional<Integer> page){
        adminService.createCategory(category);
        model.addAttribute("movies", adminService.startPage(search, model, page));
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/editcategory/{id}")
    public String showUpdateFormCategory(@PathVariable("id") int id, Model model){
        Category category =  adminService.findByIdCategory(id);
        model.addAttribute("category", category);
        return "admin/update-category";
    }

    @PostMapping("/admin/updatecategory/{id}")
    public String updateCategory (@PathVariable("id") int id, @Valid Category category, Model model,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) Optional<Integer> page) {
        adminService.createCategory(category);
        model.addAttribute("movies", adminService.startPage(search, model, page));
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Optional<Integer> page){
        Category category = adminService.findByIdCategory(id);
        adminService.deleteCategory(category);
        model.addAttribute("movies", adminService.startPage(search, model, page));
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/signupmovie")
    public String showSignUpFormMovie(Movie movie, Model model){
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/add-movie";
    }

    @PostMapping("/admin/addmovie")
    public String addMovie(@Valid Movie movie, Model model,
                           @RequestParam(required = false) String search,
                           @RequestParam(required = false) Optional<Integer> page){
        adminService.createMovie(movie);
        model.addAttribute("movies", adminService.startPage(search, model, page));
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/editmovie/{id}")
    public String showUpdateFormMovie(@PathVariable("id") Integer id, Model model){
        Movie movie = adminService.findByIdMovie(id);
        model.addAttribute("movie", movie);
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/update-movie";
    }

    @PostMapping("/admin/updatemovie/{id}")
    public String updateMovie (@PathVariable("id") Integer id, @Valid Movie movie, Model model,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) Optional<Integer> page) {
        adminService.createMovie(movie);
        model.addAttribute("movies", adminService.startPage(search, model, page));
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/deletemovie/{id}")
    public String deleteMovie(@PathVariable("id") Integer id, Model model,
                              @RequestParam(required = false) String search,
                              @RequestParam(required = false) Optional<Integer> page){
        Movie movie = adminService.findByIdMovie(id);
        adminService.deleteMovie(movie);
        model.addAttribute("movies", adminService.startPage(search, model, page));
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/omdbmoviesearch")
    public String omdbMovieSearch(@RequestParam(required = false) String search, Model model){
        omdbResponse = omdbSearchService.searchMovies(search);
        model.addAttribute("omdbMovies", omdbResponse.getSearch());
        return "admin/omdb-movies";
    }

    @GetMapping("/admin/editomdbmovie/{title}")
    public String showUpdateFormOmdbMovie(@PathVariable("title") String title, Model model){
        OmdbMovie omdbMovie = omdbResponse.searchByTitle(title);
        model.addAttribute("movie", omdbMovie.addOmdbMovie());
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/add-omdb-movie";
    }

    @PostMapping("/admin/addomdbmovie/{title}")
    public String addOmdbMovie ( @PathVariable("title") String title, @Valid Movie movie, Model model,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Optional<Integer> page) {
        adminService.createMovie(movie);
        model.addAttribute("movies", adminService.startPage(search, model, page));
        model.addAttribute("categories", adminService.findAllCategory());
        return "admin/index";
    }
}
