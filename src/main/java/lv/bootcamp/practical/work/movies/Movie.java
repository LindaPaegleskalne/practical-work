package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "movies",  uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "year"})})
public class Movie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank( message = "Please enter a name")
    @Size(max = 150, message = "Name cannot be longer than 150 characters")
    private String name;

    @NotNull
    @Range( min = 1888l, max = 2020l, message = "Year must be from 1888 to 2020")
    private Short year;

    @Column(name = "description", columnDefinition = "TEXT")
    @Size(max = 500, message = "Description cannot exceed 500 characters in lenght")
    private String description;

    @NotNull
    @Range( min = 1l, max = 10l, message = "Rating must be from 1 to 10")
    @Digits(integer = 2, fraction = 1, message = "The rating can only have one decimal place")
    private Float rating;

    @Column(name = "link_imdb")
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
}