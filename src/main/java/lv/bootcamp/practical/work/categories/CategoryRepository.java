package lv.bootcamp.practical.work.categories;

import lv.bootcamp.practical.work.categories.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
