package lv.bootcamp.practical.work.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;

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

    public Object createCategory(Category category) {
        try {
            categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            return new DuplicateKeyException("category name already exists");
        }
        return category;
    }

    public Category deleteCategory(Category category) {
        categoryRepository.delete(category);
        return category;
    }
}
