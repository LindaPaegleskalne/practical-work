package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "movies",  uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "year"})})
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Please enter a name")
    @Size(max = 150, message = "Name cannot be longer than 150 characters")
    private String name;

    @NotNull
    @Range(min = 1888L, max = 2020L, message = "Year must be from 1888 to 2020")
    private Short year;

    private Integer views = 0;

    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 500, message = "Description cannot exceed 500 characters in lenght")
    private String description;

    @NotNull
    @Range(min = 1L, max = 10L, message = "Rating must be from 1 to 10")
    @Digits(integer = 2, fraction = 1, message = "The rating can only have one decimal place")
    private Float rating;

    @Column(name = "link_imdb")
    @NotBlank(message = "Please enter an IMDB link for movie")
    @URL(message = "URL is not valid")
    private String linkImdb;

    @Column(name = "link_poster")
    @URL(message = "URL is not valid")
    private String linkPoster;

    @ManyToOne
    @JoinColumn(name = "category")
    @NotNull(message = "Please choose category")
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getLinkImdb() {
        return linkImdb;
    }

    public void setLinkImdb(String linkImdb) {
        this.linkImdb = linkImdb;
    }

    public String getLinkPoster() {
        return linkPoster;
    }

    public void setLinkPoster(String linkPoster) {
        this.linkPoster = linkPoster;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return  name.equals(movie.name)
                &&
                year.equals(movie.year)
                &&
                views.equals(movie.views)
                &&
                linkImdb.equals(movie.linkImdb)
                &&
                linkPoster.equals(movie.linkPoster);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, views, description, rating, linkImdb, linkPoster, category);
    }
}