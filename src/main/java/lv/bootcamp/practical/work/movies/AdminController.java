package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.movies.omdb.OmdbMovie;
import lv.bootcamp.practical.work.movies.omdb.OmdbResponse;
import lv.bootcamp.practical.work.movies.omdb.OmdbSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public final MovieRepository movieRepository;
    public final CategoryRepository categoryRepository;
    private OmdbSearchService omdbSearchService;
    private OmdbResponse omdbResponse;

    @Autowired
    public AdminController(MovieRepository movieRepository, CategoryRepository categoryRepository, OmdbSearchService omdbSearchService) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.omdbSearchService = omdbSearchService;
    }

    @GetMapping("/admin")
    public String admin(@RequestParam Optional<String> search,
                        @RequestParam Optional<Integer> page, Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        Page<Movie> movies = movieRepository.findByName(search.orElse("_"),
                PageRequest.of(page.orElse(0),5, Sort.by("name")));
        model.addAttribute("movies", movies);
        return "admin/index";
    }

    @GetMapping("/admin/signupcategory")
    public String showSignUpFormCategory(Category category){
        return "admin/add-category";
    }

    @PostMapping("/admin/addcategory")
    public String addCategory(@Valid Category category, Model model){
        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/editcategory/{id}")
    public String showUpdateFormCategory(@PathVariable("id") int id, Model model){
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: "+ id));
        model.addAttribute("category", category);
        return "admin/update-category";
    }

    @PostMapping("/admin/updatecategory/{id}")
    public String updateCategory (@PathVariable("id") int id, @Valid Category category, Model model) {
        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: "+ id));
        categoryRepository.delete(category);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/signupmovie")
    public String showSignUpFormMovie(Movie movie, Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/add-movie";
    }

    @PostMapping("/admin/addmovie")
    public String addMovie(@Valid Movie movie, Model model){
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/editmovie/{id}")
    public String showUpdateFormMovie(@PathVariable("id") int id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        model.addAttribute("movie", movie);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/update-movie";
    }

    @PostMapping("/admin/updatemovie/{id}")
    public String updateMovie (@PathVariable("id") int id, @Valid Movie movie, Model model) {
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/deletemovie/{id}")
    public String deleteMovie(@PathVariable("id") int id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        movieRepository.delete(movie);
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
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
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/add-omdb-movie";
    }

    @PostMapping("/admin/addomdbmovie/{title}")
    public String addOmdbMovie ( @PathVariable("title") String title, @Valid Movie movie, Model model) {
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/index";
    }
}
