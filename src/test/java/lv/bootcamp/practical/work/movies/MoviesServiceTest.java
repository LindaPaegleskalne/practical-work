package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MoviesServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MoviesService moviesService;


    @Test
    public void findMoviesByCategory() {
        Integer id = 1;
        Pageable pageable = mock(Pageable.class);
        List<Movie> movies = asList(
                movie (1, "A", (short) 2000, 7.0f, "description",
                        "link1", "link2", category(1, "a")),
                movie (2, "B", (short) 2001, 7.2f, "description",
                        "link1", "link2", category(1, "a")));


    }

    @Test
    public void findMovieByName() {
        String search = "foo";
        List<Movie> movies = asList(
                movie (1, "A", (short) 2000, 7.0f, "description",
                        "link1", "link2", category(1, "a")),
                movie (2, "B", (short) 2001, 7.2f, "description",
                        "link1", "link2", category(1, "a")));

        when(movieRepository.findMovieByName(search)).thenReturn(movies);

        assertThat(moviesService.findMovieByName(search)).isEqualTo(movies);

        verify(movieRepository).findMovieByName(search);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void findAndIncrementById() {
        Integer id = 1;


        //when(movieRepository.findById(id)).thenReturn();
    }

    @Test
    public void findAndIncrementById_idNotFound() {
        Integer id = 1;

        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> moviesService.findAndIncrementById(id)).
                isInstanceOf(IllegalArgumentException.class).hasMessage("Invalid movie ID: "+ id);

        verify(movieRepository).findById(id);
        verifyNoMoreInteractions(movieRepository);
    }


    @Test
    public void popularMovies() {
        List<Movie> movies = asList(
                movie (1, "A", (short) 2000, 7.0f, "description",
                        "link1", "link2", category(1, "a")),
                movie (2, "B", (short) 2001, 7.2f, "description",
                        "link1", "link2", category(1, "a")));

        when(movieRepository.findTop5ByOrderByViewsDesc()).thenReturn(movies);

        assertThat(moviesService.popularMovies()).isEqualTo(movies);

        verify(movieRepository).findTop5ByOrderByViewsDesc();
        verifyNoMoreInteractions(movieRepository);
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

    private Category category(Integer id, String name){
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
