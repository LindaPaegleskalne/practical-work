package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.CategoriesAdminService;
import lv.bootcamp.practical.work.categories.Category;
import lv.bootcamp.practical.work.categories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;
import static org.assertj.core.api.Assertions.*;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MoviesAdminControllerTest {

    @Mock private MoviesAdminService moviesAdminService;
    @Mock private CategoriesAdminService categoriesAdminService;

    @InjectMocks
    private MoviesAdminController moviesAdminController;

    @Test
    public void admin_getIndexPage() {
        Model model = new BindingAwareConcurrentModel();

        List<Category> category = asList(
                category(1, "A"),
                category(2, "B")
                );

        assertThat(model.getAttribute("categories")).isEqualTo(category);
    }

    @Test
    public void getAddMovieForm() {
        Model model = new BindingAwareConcurrentModel();
        Movie movie = mock(Movie.class);

        String actual = moviesAdminController.showSignUpFormMovie(movie, model);
        assertThat(actual).isEqualTo("admin/add-movie");

        verifyNoInteractions(movie);
    }

    @Test
    void addMovieTest() {
        Model model = new BindingAwareConcurrentModel();

        Movie movie = movie(1, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(moviesAdminService.createMovie(movie)).thenReturn(movie);

        String actual = moviesAdminController.addMovie(movie, model);
        assertThat(actual).isEqualTo("admin/index");

        verify(moviesAdminService).createMovie(movie);
    }

    @Test
    void showUpdateFormMovie() {
        Model model = new BindingAwareConcurrentModel();
        Integer id = 1;

        Movie movie = movie(id, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(moviesAdminService.findByIdMovie(id)).thenReturn(movie);

        String actual = moviesAdminController.showUpdateFormMovie(id, model);
        assertThat(actual).isEqualTo("admin/update-movie");
        assertThat(model.getAttribute("movie")).isEqualTo(movie);

        verify(moviesAdminService).findByIdMovie(id);
    }

    @Test
    void updateMovie() {
        Model model = new BindingAwareConcurrentModel();
        Integer id = 1;

        Movie movie = movie(id, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(moviesAdminService.createMovie(movie)).thenReturn(movie);

        String actual = moviesAdminController.updateMovie(id, movie, model);
        assertThat(actual).isEqualTo("admin/index");

        verify(moviesAdminService).createMovie(movie);
    }

    @Test
    void deleteMovie() {
        Model model = new BindingAwareConcurrentModel();
        Integer id = 1;

        Movie movie = movie(id, "A", (short) 2000, 4.0f,
                "aaa", "link1", "link2", category(1, "s"));

        when(moviesAdminService.findByIdMovie(id)).thenReturn(movie);
        when(moviesAdminService.deleteMovie(movie)).thenReturn(movie);

        String actual = moviesAdminController.deleteMovie(id, model);
        assertThat(actual).isEqualTo("admin/index");

        verify(moviesAdminService).findByIdMovie(id);
        verify(moviesAdminService).deleteMovie(movie);
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
