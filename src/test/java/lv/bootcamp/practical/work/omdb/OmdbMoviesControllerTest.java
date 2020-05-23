package lv.bootcamp.practical.work.omdb;

import lv.bootcamp.practical.work.categories.CategoriesAdminService;
import lv.bootcamp.practical.work.categories.Category;
import lv.bootcamp.practical.work.movies.Movie;
import lv.bootcamp.practical.work.movies.MoviesAdminController;
import lv.bootcamp.practical.work.movies.MoviesAdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    public void getSearchResults() {
        Model model = new BindingAwareConcurrentModel();
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
    public void updateOmdbMovie() {
        Model model = new BindingAwareConcurrentModel();
        BindingResult bindingResult = mock(BindingResult.class);
        String title = "foo";
        Movie movie = movie(1, title, (short) 2000, 4.0f,
                "abc", "link1", "link2", category(1, "a"));

        when(bindingResult.hasErrors()).thenReturn(false);
        when(moviesAdminService.createMovie(movie)).thenReturn(movie);

        String actual = moviesAdminController.addMovie(movie, bindingResult, model);
        assertThat(actual).isEqualTo("admin/index");

        verify(bindingResult).hasErrors();
        verify(moviesAdminService).createMovie(movie);
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
