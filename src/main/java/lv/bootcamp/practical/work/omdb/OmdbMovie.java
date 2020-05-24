package lv.bootcamp.practical.work.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class OmdbMovie {

    private String title;
    private short year;
    @JsonProperty("imdbID")
    private String imdbID;
    private String type;
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OmdbMovie omdbMovie = (OmdbMovie) o;
        return year == omdbMovie.year
                &&
                title.equals(omdbMovie.title)
                &&
                imdbID.equals(omdbMovie.imdbID)
                &&
                type.equals(omdbMovie.type)
                &&
                poster.equals(omdbMovie.poster);
    }
}