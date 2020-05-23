package lv.bootcamp.practical.work.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)

public class OmdbMovie {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private short year;
    @JsonProperty("imdbID")
    private String imdbID;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;

    public String getTitle() {
        return title;
    }

    public void setTitle( String title) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmdbMovie omdbMovie = (OmdbMovie) o;
        return year == omdbMovie.year &&
                title.equals(omdbMovie.title) &&
                imdbID.equals(omdbMovie.imdbID) &&
                type.equals(omdbMovie.type) &&
                poster.equals(omdbMovie.poster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, imdbID, type, poster);
    }
}

