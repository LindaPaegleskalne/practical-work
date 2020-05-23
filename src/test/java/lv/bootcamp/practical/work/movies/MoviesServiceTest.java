package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MoviesServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MoviesService moviesService;


    @Test
    public void findMoviesByCategory() {
        Integer id = 1;
        String search = "";
        Pageable pageable = PageRequest.of(0,5, Sort.by("name"));
        Page<Movie> movies = mock(Page.class);

        when(movieRepository.findByCategoryId(id, pageable)).thenReturn(movies);

        assertThat(moviesService.findMoviesByCategory(id, search, Optional.of(1))).isEqualTo(movies);

        verify(movieRepository).findByCategoryId(id, pageable);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void findMoviesByNameAndCategory(){
        Integer id = 1;
        String search = "name";
        Pageable pageable = PageRequest.of(0,5, Sort.by("name"));
        Page<Movie> movies = mock(Page.class);

        when(movieRepository.findByNameAndId(search, id,  pageable)).thenReturn(movies);

        assertThat(moviesService.findMoviesByCategory(id, search, Optional.of(1))).isEqualTo(movies);

        verify(movieRepository).findByNameAndId(search, id,  pageable);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void findMovieByName() {
        String search = "foo";
        List<Movie> movies = asList(
                movie (1, "foo a", (short) 2000, 4,7.0f, "description",
                        "link1", "link2", category(1, "a")),
                movie (2, "foo b", (short) 2001, 3, 7.2f, "description",
                        "link1", "link2", category(1, "a")));

        when(movieRepository.findMovieByName(search)).thenReturn(movies);

        assertThat(moviesService.findMovieByName(search)).isEqualTo(movies);

        verify(movieRepository).findMovieByName(search);
        verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void findAndIncrementById() {
        Integer id = 1;
        int increment = 1;
        Movie movie = movie (1, "A", (short) 2000, 0, 7.0f, "description",
                        "link1", "link2", category(1, "a"));

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.incrementMovieViews(id, increment)).thenReturn(1);

        assertThat(moviesService.findAndIncrementById(id)).isEqualTo(movie);

        verify(movieRepository).findById(id);
        verify(movieRepository).incrementMovieViews(id, increment);
        verifyNoMoreInteractions(movieRepository);
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
                movie (1, "A", (short) 2000, 4, 7.0f, "description",
                        "link1", "link2", category(1, "a")),
                movie (2, "B", (short) 2001,3, 7.2f, "description",
                        "link1", "link2", category(1, "a")));

        when(movieRepository.findTop5ByOrderByViewsDesc()).thenReturn(movies);

        assertThat(moviesService.popularMovies()).isEqualTo(movies);

        verify(movieRepository).findTop5ByOrderByViewsDesc();
        verifyNoMoreInteractions(movieRepository);
    }


    private Movie movie(Integer id, String name, Short year, Integer views, Float rating, String description,
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
