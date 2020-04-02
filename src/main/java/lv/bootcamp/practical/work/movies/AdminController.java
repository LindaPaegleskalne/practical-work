package lv.bootcamp.practical.work.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
    public String admin(Model model){
        model.addAttribute("movies", movieRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin";
    }
    @GetMapping("/signupcategory")
    public String showSignUpFormCategory(Category category){
        return "add-category";
    }

    @PostMapping("/addcategory")
    public String addCategory(@Valid Category category, Model model){
        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin";
    }
    @GetMapping("/editcategory/{id}")
    public String showUpdateFormCategory(@PathVariable("id") int id, Model model){
        Category category =  categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        model.addAttribute("category", category);
        return "update-category";
    }

    @PostMapping("/updatecategory/{id}")
    public String updateCategory (@PathVariable("id") int id, @Valid Category category, Model model) {
        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin";
    }

    @GetMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        categoryRepository.delete(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin";


    }
    @GetMapping("/signupmovie")
    public String showSignUpFormMovie(Movie movie){
        return "add-movie";
    }

    @PostMapping("/addmovie")
    public String addMovie(@Valid Movie movie, Model model){
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "admin";
    }
    @GetMapping("/editmovie/{id}")
    public String showUpdateFormMovie(@PathVariable("id") int id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        model.addAttribute("movie", movie);
        return "update-movie";
    }

    @PostMapping("/updatemovie/{id}")
    public String updateMovie (@PathVariable("id") int id, @Valid Movie movie, Model model) {
        movieRepository.save(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "admin";
    }

    @GetMapping("/deletemovie/{id}")
    public String deleteMovie(@PathVariable("id") int id, Model model){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: "+ id));
        movieRepository.delete(movie);
        model.addAttribute("movies", movieRepository.findAll());
        return "admin";
    }
}
