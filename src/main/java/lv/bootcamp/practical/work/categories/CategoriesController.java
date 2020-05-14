package lv.bootcamp.practical.work.categories;

import lv.bootcamp.practical.work.movies.MoviesAdminService;
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
public class CategoriesController {
    private final CategoriesAdminService categoriesAdminService;
    private final MoviesAdminService moviesAdminService;


    @Autowired
    public CategoriesController(CategoriesAdminService categoriesAdminService, MoviesAdminService moviesAdminService) {
        this.categoriesAdminService = categoriesAdminService;
        this.moviesAdminService = moviesAdminService;
    }

    @GetMapping("/admin/signupcategory")
    public String showSignUpFormCategory(Category category){
        return "admin/add-category";
    }

    @PostMapping("/admin/addcategory")
    public String addCategory(@Valid Category category, Model model){
        categoriesAdminService.createCategory(category);
        model.addAttribute("movies", moviesAdminService.defaultStartPage());
        model.addAttribute("searchStr", "");
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/editcategory/{id}")
    public String showUpdateFormCategory(@PathVariable("id") int id, Model model){
        Category category =  categoriesAdminService.findByIdCategory(id);
        model.addAttribute("category", category);
        return "admin/update-category";
    }

    @PostMapping("/admin/updatecategory/{id}")
    public String updateCategory (@PathVariable("id") int id, @Valid Category category, Model model) {
        categoriesAdminService.createCategory(category);
        model.addAttribute("movies", moviesAdminService.defaultStartPage());
        model.addAttribute("searchStr", "");
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/index";
    }

    @GetMapping("/admin/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model){
        Category category = categoriesAdminService.findByIdCategory(id);
        categoriesAdminService.deleteCategory(category);
        model.addAttribute("movies", moviesAdminService.defaultStartPage());
        model.addAttribute("searchStr", "");
        model.addAttribute("categories", categoriesAdminService.findAllCategory());
        return "admin/index";
    }
}