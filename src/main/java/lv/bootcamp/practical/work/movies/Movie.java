package lv.bootcamp.practical.work.movies;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie{
    @Id
    @GeneratedValue(generator = "uuid")
    private int id;
    private String name;
    private int year;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private Float rating;
    private String linkIMDB;
    private String linkPoster;
    @ManyToOne
    @JoinColumn(name = "categoriesID")
    private Category category;


}