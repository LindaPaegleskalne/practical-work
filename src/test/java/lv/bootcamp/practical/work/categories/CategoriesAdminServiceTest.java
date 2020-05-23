package lv.bootcamp.practical.work.categories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriesAdminServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoriesAdminService categoriesAdminService;

    @Test
    public void findAllCategory() {
        List<Category> categories = asList(category(1, "Aa"),
                category(2,"Ab"));

        when(categoryRepository.findAll()).thenReturn(categories);

        assertThat(categoriesAdminService.findAllCategory()).isEqualTo(categories);

        verify(categoryRepository).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void findByIdCategory() {
        Integer id = 1;
        Category category = category(1, "Aa");

        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));

        Object actual = categoriesAdminService.findByIdCategory(id);

        assertThat(actual).isEqualTo(category);

        verify(categoryRepository).findById(id);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void findByIdCategory_duplicateCategory() {
        Integer id = 1;
        Category category = category(1, "Aa");

        when(categoryRepository.findById(id))
                .thenThrow(DataIntegrityViolationException.class);

        categoriesAdminService.findByIdCategory(id);

        verify(categoryRepository).findById(id);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void createCategory() {
        Category category = category(1, "Aaa");

        when(categoryRepository.save(category)).thenReturn(category);

        assertThat(categoriesAdminService.createCategory(category)).isEqualTo(category);

        verify(categoryRepository).save(category);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void createCategory_duplicateCategory() {
        Category category = mock(Category.class);

        when(categoryRepository.save(category))
                .thenThrow(DataIntegrityViolationException.class);

        categoriesAdminService.createCategory(category);

        verify(categoryRepository).save(category);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void deleteCategory() {
        Category category = mock(Category.class);

        categoriesAdminService.deleteCategory(category);

        verify(categoryRepository, times(1)).delete(eq(category));
        verifyNoMoreInteractions(categoryRepository);
    }

    private Category category(Integer id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
