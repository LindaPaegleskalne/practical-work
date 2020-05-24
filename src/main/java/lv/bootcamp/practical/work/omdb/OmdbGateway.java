package lv.bootcamp.practical.work.omdb;

import lv.bootcamp.practical.work.omdb.OmdbResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OmdbGateway {
    private final RestTemplate restTemplate;

    public OmdbGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OmdbResponse movies(String name) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.omdbapi.com")
                .queryParam("apikey", "d4df4065")
                .queryParam("type", "movie")
                .queryParam("s", name)
                .build();

        OmdbResponse response = restTemplate.getForObject(uri.toUriString(), OmdbResponse.class);
        return response;

    }
}
