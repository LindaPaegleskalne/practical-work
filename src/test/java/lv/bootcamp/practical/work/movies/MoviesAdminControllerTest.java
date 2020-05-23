package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.CategoriesAdminService;
import lv.bootcamp.practical.work.categories.Category;
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
import static org.assertj.core.api.Assertions.*;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MoviesAdminControllerTest {

    @Mock
    private MoviesAdminService moviesAdminService;
    @Mock
    private CategoriesAdminService categoriesAdminService;
    @InjectMocks
    private MoviesAdminController moviesAdminController;
    private Model model;
    private List<Category> categories;

    @BeforeEach
    void init(){
        model = new BindingAwareConcurrentModel();
        categories = asList(category(1, "Aa"), category(2,"Ab"));
    }

    @Test
    public void admin_getIndexPage(){
        String search = "foo";
        Optional<Integer> page = Optional.of(1);
        Page<Movie> movies = Mockito.mock(Page.class);

        when(moviesAdminService.startPage(search, page)).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = moviesAdminController.admin(search, model, page);

        assertThat(actual).isEqualTo("admin/index");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);
        assertThat(model.getAttribute("searchStr")).isEqualTo(search);
        assertThat(model.getAttribute("movies")).isEqualTo(movies);

        verify(moviesAdminService).startPage(search, page);
        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(moviesAdminService, categoriesAdminService);
    }

    @Test
    public void showSignUpFormMovie() {
        Movie movie = movie(1,"Aa", (short)2000, 7.0f, "descr",
                "imdb1", "imbd2", category(2,"Ccc"));

        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = moviesAdminController.showSignUpFormMovie(movie, model);

        assertThat(actual).isEqualTo("admin/add-movie");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(categoriesAdminService);
    }

    @Test
    void addMovieTest() {
        BindingResult bindingResult = mock(BindingResult.class);
        Page<Movie> movies = Mockito.mock(Page.class);
        Movie movie = movie(1, "A", (short) 2000, 4.0f, "aaa",
                "link1", "link2", category(1, "s"));

        when(bindingResult.hasErrors()).thenReturn(false);
        when(moviesAdminService.createMovie(movie)).thenReturn(movie);
        when(moviesAdminService.defaultStartPage()).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = moviesAdminController.addMovie(movie, bindingResult, model);

        assertThat(actual).isEqualTo("admin/index");
        assertThat(model.getAttribute("movies")).isEqualTo(movies);
        assertThat(model.getAttribute("searchStr")).isEqualTo("");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(bindingResult).hasErrors();
        verify(moviesAdminService).createMovie(movie);
        verify(moviesAdminService).defaultStartPage();
        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(moviesAdminService, categoriesAdminService, bindingResult);
    }

    @Test
    void addMovieTest_hasErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        Page<Movie> movies = Mockito.mock(Page.class);
        Movie movie = movie(1, "A", (short) 2000, 4.0f, "aaa",
                "link1", "link2", category(1, "s"));

        when(bindingResult.hasErrors()).thenReturn(true);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = moviesAdminController.addMovie(movie, bindingResult, model);

        assertThat(actual).isEqualTo("admin/add-movie");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(bindingResult).hasErrors();
        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(categoriesAdminService);
    }

    @Test
    void showUpdateFormMovie() {
        Integer id = 1;
        Movie movie = movie(id, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(moviesAdminService.findByIdMovie(id)).thenReturn(movie);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = moviesAdminController.showUpdateFormMovie(id, model);

        assertThat(actual).isEqualTo("admin/update-movie");
        assertThat(model.getAttribute("movie")).isEqualTo(movie);
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(moviesAdminService).findByIdMovie(id);
        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(moviesAdminService, categoriesAdminService);
    }

    @Test
    void updateMovie() {
        BindingResult bindingResult = mock(BindingResult.class);
        Integer id = 1;
        Page<Movie> movies = Mockito.mock(Page.class);
        Movie movie = movie(id, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(bindingResult.hasErrors()).thenReturn(false);
        when(moviesAdminService.createMovie(movie)).thenReturn(movie);
        when(moviesAdminService.defaultStartPage()).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = moviesAdminController.updateMovie(id, movie, bindingResult, model);

        assertThat(actual).isEqualTo("admin/index");
        assertThat(model.getAttribute("movies")).isEqualTo(movies);
        assertThat(model.getAttribute("searchStr")).isEqualTo("");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(bindingResult).hasErrors();
        verify(categoriesAdminService).findAllCategory();
        verify(moviesAdminService).createMovie(movie);
        verify(moviesAdminService).defaultStartPage();
        verifyNoMoreInteractions(categoriesAdminService, moviesAdminService, bindingResult);
    }

    @Test
    void updateMovie_hasErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        Integer id = 1;
        Movie movie = movie(id, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(bindingResult.hasErrors()).thenReturn(true);

        String actual = moviesAdminController.updateMovie(id, movie, bindingResult, model);

        assertThat(actual).isEqualTo("admin/update-movie");

        verify(bindingResult).hasErrors();
        verifyNoMoreInteractions(bindingResult);
    }

    @Test
    void deleteMovie() {
        Integer id = 1;
        Page<Movie> movies = Mockito.mock(Page.class);
        Movie movie = movie(id, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(moviesAdminService.findByIdMovie(id)).thenReturn(movie);
        when(moviesAdminService.deleteMovie(movie)).thenReturn(movie);
        when(moviesAdminService.defaultStartPage()).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = moviesAdminController.deleteMovie(id, model);

        assertThat(actual).isEqualTo("admin/index");
        assertThat(model.getAttribute("movies")).isEqualTo(movies);
        assertThat(model.getAttribute("searchStr")).isEqualTo("");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(moviesAdminService).findByIdMovie(id);
        verify(moviesAdminService).deleteMovie(movie);
        verify(moviesAdminService).defaultStartPage();
        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(moviesAdminService, categoriesAdminService);
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
