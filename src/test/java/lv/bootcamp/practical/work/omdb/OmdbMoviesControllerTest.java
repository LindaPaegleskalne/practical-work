package lv.bootcamp.practical.work.omdb;

import lv.bootcamp.practical.work.categories.CategoriesAdminService;
import lv.bootcamp.practical.work.categories.Category;
import lv.bootcamp.practical.work.movies.Movie;
import lv.bootcamp.practical.work.movies.MoviesAdminController;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OmdbMoviesControllerTest {
    @Mock private  OmdbService omdbService;
    @Mock  private  MoviesAdminService moviesAdminService;
    @Mock  private  CategoriesAdminService categoriesAdminService;

    @InjectMocks
    private OmdbMoviesController omdbMoviesController;
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
    public void getSearchResults() {
        String search = "foo";
        List<OmdbMovie> omdbMovies = asList(
                omdbMovie("foo", (short)1989, "imdbId1" , "m", "link1"),
        omdbMovie("foo f", (short)2010, "imdbId2" , "m", "link2"));
        when(omdbService.findOmdbMovies(search)).thenReturn(omdbMovies);

        String actual = omdbMoviesController.omdbMovieSearch(search, model);

        assertThat(actual).isEqualTo("admin/omdb-movies");
        assertThat(model.getAttribute("omdbMovies")).isEqualTo(omdbMovies);

        verify(omdbService).findOmdbMovies(search);
        verifyNoMoreInteractions(omdbService);
    }

    @Test
    public void showUpdateFormOmdbMovie() {
        Model model = new BindingAwareConcurrentModel();
        String title = "foo";
        Movie movie = movie(1, title, (short) 2000, 4.0f,
                "abc", "link1", "link2", category(1, "a"));

        when(omdbService.cloneMovieFromOmdb(title)).thenReturn(movie);

        String actual = omdbMoviesController.showUpdateFormOmdbMovie(title, model);
        assertThat(actual).isEqualTo("admin/add-omdb-movie");

        verify(omdbService).cloneMovieFromOmdb(title);
    }

    @Test
    void addMovieFromOmdb() {
        BindingResult bindingResult = mock(BindingResult.class);
        Page<Movie> movies = Mockito.mock(Page.class);
        String title = "A";
        Movie movie = movie(1, "A", (short) 2000, 4.0f, "aaa",
                "link1", "link2", category(1, "s"));

        when(bindingResult.hasErrors()).thenReturn(false);
        when(moviesAdminService.createMovie(movie)).thenReturn(movie);
        when(moviesAdminService.defaultStartPage()).thenReturn(movies);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = omdbMoviesController.addOmdbMovie(title, movie, bindingResult, model);

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
    void addMovieFromOmdb_hasErrors() {
        BindingResult bindingResult = mock(BindingResult.class);
        Page<Movie> movies = Mockito.mock(Page.class);
        String title = "A";
        Movie movie = movie(1, "A", (short) 2000, 4.0f, "aaa",
                "link1", "link2", category(1, "s"));

        when(bindingResult.hasErrors()).thenReturn(true);
        when(categoriesAdminService.findAllCategory()).thenReturn(categories);

        String actual = omdbMoviesController.addOmdbMovie(title, movie, bindingResult, model);

        assertThat(actual).isEqualTo("admin/add-omdb-movie");
        assertThat(model.getAttribute("categories")).isEqualTo(categories);

        verify(bindingResult).hasErrors();
        verify(categoriesAdminService).findAllCategory();
        verifyNoMoreInteractions(categoriesAdminService);
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

    private OmdbMovie omdbMovie(String title, Short year, String imdbId, String type, String poster) {
        OmdbMovie omdbMovie = new OmdbMovie();
        omdbMovie.setTitle(title);
        omdbMovie.setYear(year);
        omdbMovie.setImdbID(imdbId);
        omdbMovie.setType(type);
        omdbMovie.setPoster(poster);
        return omdbMovie;
    }

    private Category category(Integer id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
