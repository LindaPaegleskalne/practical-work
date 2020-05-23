package lv.bootcamp.practical.work.categories;

import lv.bootcamp.practical.work.movies.Movie;
import lv.bootcamp.practical.work.movies.MoviesAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareConcurrentModel;
import java.util.List;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriesControllerTest {

    @Mock
    private MoviesAdminService moviesAdminService;
    @Mock
    private CategoriesAdminService categoriesAdminService;

    @InjectMocks
    private CategoriesController categoriesController;
    private Model model;
    private Category category;
    private List<Category> categories;

    @BeforeEach
    void init(){
        model = new BindingAwareConcurrentModel();
        category = category(1, "A");
        categories = asList(category(1, "Aa"), category(2,"Ab"));
    }

    @Test
    public void showSignUpFormCategory() {

        String actual = categoriesController.showSignUpFormCategory(category);
        assertThat(actual).isEqualTo("admin/add-category");
    }

    @Test
    public void addCategory() {
        BindingResult bindingResult = mock(BindingResult.class);
        Page<Movie> movies = Mockito.mock(Page.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(moviesAdminService.defaultStartPage()).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);
        when(categoriesAdminService.createCategory(category)).thenReturn(category);

        String actual = categoriesController.addCategory(category, bindingResult, model);

        assertThat(actual).isEqualTo("admin/index");
        assertThat(model.getAttribute("movies")).isEqualTo(movies);
        assertThat(model.getAttribute("searchStr")).isEqualTo("");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(categoriesAdminService).findAllCategory();
        verify(categoriesAdminService).createCategory(category);
        verifyNoMoreInteractions(categoriesAdminService);
    }

    @Test
    public void addCategory_hasErrors() {
        BindingResult bindingResult = mock(BindingResult.class);

       when(bindingResult.hasErrors()).thenReturn(true);

        String actual = categoriesController.addCategory(category, bindingResult, model);

        assertThat(actual).isEqualTo("admin/add-category");
    }

    @Test
    public void showUpdateFormCategory() {
        Integer id = 1;

        when(categoriesAdminService.findByIdCategory(id)).thenReturn(category);

        String actual = categoriesController.showUpdateFormCategory(id, model);

        assertThat(actual).isEqualTo("admin/update-category");
        assertThat(model.getAttribute("category")).isEqualTo(category);

        verify(categoriesAdminService).findByIdCategory(id);
        verifyNoMoreInteractions(categoriesAdminService);
    }

    @Test
    public void updateCategory() {
        Integer id = 1;
        BindingResult bindingResult = mock(BindingResult.class);
        Page<Movie> movies = Mockito.mock(Page.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(categoriesAdminService.createCategory(category)).thenReturn(category);
        when(moviesAdminService.defaultStartPage()).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = categoriesController.updateCategory(id, category, bindingResult, model);

        assertThat(actual).isEqualTo("admin/index");
        assertThat(model.getAttribute("movies")).isEqualTo(movies);
        assertThat(model.getAttribute("searchStr")).isEqualTo("");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(categoriesAdminService).createCategory(category);
        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(categoriesAdminService);
    }

    @Test
    public void updateCategory_hasErrors() {
        Integer id = 1;
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = categoriesController.updateCategory(id, category, bindingResult, model);

        assertThat(actual).isEqualTo("admin/update-category");
    }

    @Test
    public void deleteCategory() {
        Integer id = 1;
        Page<Movie> movies = Mockito.mock(Page.class);

        when(categoriesAdminService.findByIdCategory(id)).thenReturn(category);
        when(categoriesAdminService.deleteCategory(category)).thenReturn(category);
        when(moviesAdminService.defaultStartPage()).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = categoriesController.deleteCategory(id, model);

        assertThat(actual).isEqualTo("admin/index");
        assertThat(model.getAttribute("movies")).isEqualTo(movies);
        assertThat(model.getAttribute("searchStr")).isEqualTo("");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(categoriesAdminService).findByIdCategory(id);
        verify(categoriesAdminService).deleteCategory(category);
        verify(categoriesAdminService).findAllCategory();
        verify(moviesAdminService).defaultStartPage();
        verifyNoMoreInteractions(categoriesAdminService, moviesAdminService);
    }

    private Category category(Integer id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    private Movie movie(Integer id, String name, Short year, Float rating, String description,
                        String linkImdb, String poster, Category category) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        movie.setYear(year);
        movie.setRating(rating);
        movie.setDescription(description);
        movie.setLinkImdb(linkImdb);
        movie.setLinkPoster(poster);
        movie.setCategory(category);
        return movie;
    }
}
