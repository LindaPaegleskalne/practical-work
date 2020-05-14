package lv.bootcamp.practical.work.categories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoriesAdminServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoriesAdminService categoriesAdminService;

    @Test
    void findAllCategory() {
        
    }

    @Test
    void createCategory() {

    }

    @Test
    void deleteCategory() {

    }

}
