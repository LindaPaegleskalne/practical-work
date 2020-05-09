package lv.bootcamp.practical.work.movies;

import lv.bootcamp.practical.work.categories.Category;

import javax.persistence.*;

@Entity
@Table(name = "movies",  uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "year"})})
public class Movie{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Short year;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private Float rating;
    @Column(name = "link_imdb")
    private String linkImdb;
    @Column(name = "link_poster")
    private String linkPoster;
    @ManyToOne
    @JoinColumn(name = "category")
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