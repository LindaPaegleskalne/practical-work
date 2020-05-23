package lv.bootcamp.practical.work.omdb;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;


public class OmdbResponseTest {
    private OmdbResponse omdbResponse;

    @Before
    public void init(){
        omdbResponse = new OmdbResponse();
        omdbResponse.setMovies(asList(
                omdbMovie("foo", (short)1989, "imdbId1" , "m", "link1"),
                omdbMovie("aaa", (short)2010, "imdbId2" , "m", "link2")));
    }

    @Test
    public void findMovieByTitle(){

        OmdbMovie expected = omdbMovie("foo", (short)1989, "imdbId1", "m", "link1");
        String search = "foo";
        OmdbMovie actual = omdbResponse.searchByTitle(search);
        assertEquals(expected, actual);

    }
    @Test
    public void findMovieByTitleWithNonExistingTitle(){
        OmdbMovie expected = null;
        String search = "foo a";
        OmdbMovie actual = omdbResponse.searchByTitle(search);
        assertEquals(expected, actual);
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
}
