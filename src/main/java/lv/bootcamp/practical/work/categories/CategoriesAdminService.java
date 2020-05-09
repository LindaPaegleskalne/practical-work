package lv.bootcamp.practical.work.categories;

import lv.bootcamp.practical.work.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesAdminService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoriesAdminService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    public Category findByIdCategory(Integer id) {
        return categoryRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Invalid category ID: "+ id));
    }

    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }

    public Category deleteCategory(Category category) {
        categoryRepository.delete(category);
        return category;
    }
}
