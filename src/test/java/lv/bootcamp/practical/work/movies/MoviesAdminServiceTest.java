package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MoviesAdminServiceTest {

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MoviesAdminService moviesAdminService;

    @Test
    public void getStartPage() {

    }

    @Test
    public void getDefaultStartPage() {

    }

    @Test
    public void findMovieById() {
        Movie movie = new Movie();
        movie.setId(1);

        Mockito.when(repository.findById(1)).thenReturn(java.util.Optional.of(movie));
        assertThat(moviesAdminService.findByIdMovie(1)).isEqualTo(movie);
    }

    @Test
    public void createMovie() {
        Movie movie = new Movie();

        Mockito.when(repository.save(movie)).thenReturn(movie);
        assertThat(moviesAdminService.createMovie(movie)).isEqualTo(movie);
    }

    @Test
    public void deleteMovie() {



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
