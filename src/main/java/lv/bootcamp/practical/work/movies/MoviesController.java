package lv.bootcamp.practical.work.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MoviesController {

    private final CategoryRepository categoryRepository;


    @Autowired
    public MoviesController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/category/{id}")
    public String showCategory(@PathVariable("id") int id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: "+ id));
        model.addAttribute("category", category);
        return "category";
    }

}
