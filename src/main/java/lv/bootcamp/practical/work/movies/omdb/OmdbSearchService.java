package lv.bootcamp.practical.work.movies.omdb;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class OmdbSearchService {
    private final RestTemplate restTemplate;

    public OmdbSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<OmdbMovie> searchMovies(String search){
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.omdbapi.com")
                .queryParam("apikey", "d4df4065")
                .queryParam("s", search)
                .build();


        return restTemplate.getForObject(uri.toUriString(), OmdbResponse.class).getOmdbMovieList();
    }
}
