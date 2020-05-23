package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoviesAdminServiceTest {

    @Mock private MovieRepository movieRepository;

    @InjectMocks private MoviesAdminService moviesAdminService;

    @Test
    public void getStartPage() {
        String search = "foo";
        Pageable pageable = PageRequest.of(0,5, Sort.by("name"));
        Page<Movie> movies = mock(Page.class);

        when(movieRepository.findByName(search, pageable)).thenReturn(movies);

        assertThat(moviesAdminService.startPage(search, Optional.of(1))).isEqualTo(movies);

        verify(movieRepository).findByName(search, pageable);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void getDefaultStartPage() {
        Pageable pageable = PageRequest.of(0,5, Sort.by("name"));
        Page<Movie> movies = mock(Page.class);

        when(movieRepository.findByName("", pageable)).thenReturn(movies);

        assertThat(moviesAdminService.defaultStartPage()).isEqualTo(movies);

        verify(movieRepository).findByName("", pageable);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void findMovieById() {
        Integer id = 1;
        Movie movie = movie (1, "A", (short) 2000, 0, 7.0f, "description",
                "link1", "link2", category(1, "a"));

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        assertThat(moviesAdminService.findByIdMovie(id)).isEqualTo(movie);

        verify(movieRepository).findById(id);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void findMovieById_idNotFound() {
        Integer id = 1;

        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moviesAdminService.findByIdMovie(id)).
                isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid movie ID: "+ id);

        verify(movieRepository).findById(id);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void createMovie() {
        Movie movie = movie (1, "A", (short) 2000, 0, 7.0f, "description",
                "link1", "link2", category(1, "a"));

        when(movieRepository.save(movie)).thenReturn(movie);

        assertThat(moviesAdminService.createMovie(movie)).isEqualTo(movie);

        verify(movieRepository).save(movie);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void createMovie_duplicateMovie() {
        Movie movie = mock(Movie.class);

        when(movieRepository.save(movie))
                .thenThrow(DataIntegrityViolationException.class);

        moviesAdminService.createMovie(movie);

        verify(movieRepository).save(movie);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void deleteMovie() {
        Movie movie = mock(Movie.class);

        moviesAdminService.deleteMovie(movie);

        verify(movieRepository, times(1)).delete(eq(movie));
        verifyNoMoreInteractions(movieRepository);

    }

    private Movie movie(Integer id, String name, Short year, int views, Float rating, String description,
                        String linkImdb, String poster, Category category) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        movie.setYear(year);
        movie.setViews(views);
        movie.setRating(rating);
        movie.setDescription(description);
        movie.setLinkImdb(linkImdb);
        movie.setLinkPoster(poster);
        movie.setCategory(category);
        return movie;
    }

    private Category category(Integer id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
