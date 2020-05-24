package lv.bootcamp.practical.work.omdb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OmdbGatewayTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OmdbGateway omdbGateway;

    @Test
    public void returnMovies() {
        OmdbResponse omdbResponse = mock(OmdbResponse.class);
        String name = "sun";
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.omdbapi.com")
                .queryParam("apikey", "d4df4065")
                .queryParam("type", "movie")
                .queryParam("s", name)
                .build();
        when(restTemplate.getForObject(uri.toUriString(), OmdbResponse.class)).thenReturn(omdbResponse);
        assertThat(omdbGateway.movies(name)).isEqualTo(omdbResponse);
        verify(restTemplate).getForObject(uri.toUriString(), OmdbResponse.class);
        verifyNoMoreInteractions(restTemplate);
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
