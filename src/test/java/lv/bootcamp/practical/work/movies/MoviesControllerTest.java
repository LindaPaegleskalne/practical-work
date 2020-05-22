package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.CategoriesService;
import lv.bootcamp.practical.work.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoviesControllerTest {

    @Mock
    private MoviesService moviesService;
    @Mock
    private CategoriesService categoriesService;

    @InjectMocks
    private MoviesController moviesController;
    private Model model;
    private List<Category> categories;
    private List<Movie> movies;
    private Movie movie;

    @BeforeEach
    void init() {
        model = new BindingAwareConcurrentModel();
        categories = Arrays.asList(
                category(1, "A"),
                category(2, "B"));
        movies = Arrays.asList(
                movie(1,"S"),
                movie(2,"P"));
    }

    @Test
    public void getIndexPage() {
        when(categoriesService.findAllCategories()).thenReturn(categories);
        when(moviesService.popularMovies()).thenReturn(movies);

        String actual = moviesController.index(model);

        assertThat(actual).isEqualTo("index");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);
        assertThat(model.getAttribute("popularMovies")).isEqualTo(movies);

        verify(categoriesService).findAllCategories();
        verify(moviesService).popularMovies();
        verifyNoMoreInteractions(categoriesService, moviesService);
    }

    @Test
    public void getSearchResults() {
        String search = "foo";

        when(moviesService.findMovieByName(search)).thenReturn(movies);

        String actual = moviesController.searchResults(search, model);

        assertThat(actual).isEqualTo("search");
        assertThat(model.getAttribute("moviesAll")).isEqualTo(movies);
        assertThat(model.getAttribute("search")).isEqualTo(search);

        verify(moviesService).findMovieByName(search);
        verifyNoMoreInteractions(moviesService);
    }

    @Test
    public void showCategory() {
        Integer id = 1;
        String search = "foo";
        Category category = new Category();
        Optional<Integer> page = Optional.of(1);

        when(categoriesService.findCategory(id)).thenReturn(category);
        when(moviesService.findMoviesByCategory(id, search,page)).thenReturn(movies);

        String actual = moviesController.showCategory(id, search, page, model);

        assertThat(actual).isEqualTo("category");
        assertThat(model.getAttribute("category")).isEqualTo(category);
        assertThat(model.getAttribute("search")).isEqualTo(search);

        verify(categoriesService).findCategory(id);
        verify(moviesService).findMoviesByCategory(id, search, page);
        verifyNoMoreInteractions(categoriesService, moviesService);
    }

    @Test
    public void showUpdateFormMovie() {
        Integer id = 1;

        when(moviesService.findAndIncrementById(id)).thenReturn(movie);

        String actual = moviesController.showUpdateFormMovie(id, model);

        assertThat(actual).isEqualTo("movie");
        assertThat(model.getAttribute("movie")).isEqualTo(movie);

        verify(moviesService).findAndIncrementById(id);
        verifyNoMoreInteractions(moviesService);
    }

    private Category category(Integer id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }

    private Movie movie(Integer id, String name) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        return movie;
    }
}
