package lv.bootcamp.practical.work.omdb;


import lv.bootcamp.practical.work.movies.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OmdbServiceTest {
    @Mock private OmdbGateway omdbGateway;
    @Mock private OmdbResponse omdbResponse;
    @InjectMocks private OmdbService omdbService;


    @Test
    public void findOmdbMovies(){
        String search = "foo";
        when(omdbGateway.movies(search)).thenReturn(omdbResponse);
        List<OmdbMovie> actual = omdbService.findOmdbMovies(search);
        assertThat(actual).isEqualTo(omdbResponse.getMovies());
        verify(omdbGateway).movies(search);
        verifyNoMoreInteractions(omdbGateway);
    }

    @Test
    public void finfOmdbMovieByTitle(){
        List<OmdbMovie> omdbMovies = (asList(
                omdbMovie("foo", (short)1989, "imdbId1" , "m", "link1"),
                omdbMovie("aaa", (short)2010, "imdbId2" , "m", "link2")));
        String title = "foo";
        Movie expected = movie("foo", (short)1989, "https://www.imdb.com/title/imdbId1", "link1");
        when(omdbResponse.getMovies()).thenReturn(omdbMovies);
        Movie actual = omdbService.findOmdbMovieByTitle(title);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void returnMovie(){
        OmdbMovie omdbMovie = omdbMovie("foo", (short)1989, "imdbId1" , "m", "link1");
        Movie expected = movie("foo", (short)1989, "https://www.imdb.com/title/imdbId1", "link1");
        Movie actual = omdbService.returnMovie(omdbMovie);
        assertThat(actual).isEqualTo(expected);
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
    private Movie movie(String name, Short year, String linkImdb, String poster) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setYear(year);
        movie.setLinkImdb(linkImdb);
        movie.setLinkPoster(poster);
        return movie;
    }
}
