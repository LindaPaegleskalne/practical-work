package lv.bootcamp.practical.work.movies;

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
import java.util.Collection;
import java.util.Optional;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class AdminController {

    public final MovieRepository movieRepository;
    public final CategoryRepository categoryRepository;

    @Autowired
    public AdminController(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin")
    public String admin(@RequestParam Optional<String> search,
                        @RequestParam Optional<Integer> page, Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        Page<Movie> movies;
        movies = movieRepository.findByName(search.orElse("_"), PageRequest.of(page.orElse(0),5));
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
        return "admin/index";
    }

    @GetMapping("/admin/editcategory/{id}")
    public String showUpdateFormCategory(@PathVariable("id") int id, Model model){
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        model.addAttribute("category", category);
        return "admin/update-category";
    }

    @PostMapping("/admin/updatecategory/{id}")
    public String updateCategory (@PathVariable("id") int id, @Valid Category category, Model model) {
        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        categoryRepository.delete(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/signupmovie")
    public String showSignUpFormMovie(Movie movie){
        return "admin/add-movie";
    }

    @PostMapping("/admin/addmovie")
    public String addMovie(@Valid Movie movie, Model model){
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/editmovie/{id}")
    public String showUpdateFormMovie(@PathVariable("id") int id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        model.addAttribute("movie", movie);
        return "admin/update-movie";
    }

    @PostMapping("/admin/updatemovie/{id}")
    public String updateMovie (@PathVariable("id") int id, @Valid Movie movie, Model model) {
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/index";
    }

    @GetMapping("/admin/deletemovie/{id}")
    public String deleteMovie(@PathVariable("id") int id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        movieRepository.delete(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "admin/index";
    }
}
