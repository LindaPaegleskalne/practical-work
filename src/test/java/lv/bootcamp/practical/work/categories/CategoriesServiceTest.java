package lv.bootcamp.practical.work.categories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriesServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    CategoriesService categoriesService;

    @Test
    public void findAllCategories() {
        List<Category> categories = asList(category(1, "Aa"),
                category(2,"Ab"));

        when(categoryRepository.findAll()).thenReturn(categories);

        assertThat(categoriesService.findAllCategories()).isEqualTo(categories);

        verify(categoryRepository).findAll();
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void findCategory() {
        int id = 1;
        Category category = category(1,"Aaa");

        when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));

        assertThat(categoriesService.findCategory(id)).isEqualTo(category);

        verify(categoryRepository).findById(id);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void findCategory_ifNotFound() {
        Integer id = 1;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriesService.findCategory(id))
                .isEqualToComparingFieldByField(new IllegalArgumentException("Invalid user Id:" + id));

        verify(categoryRepository).findById(id);
        verifyNoMoreInteractions(categoryRepository);
    }

    private Category category(Integer id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
