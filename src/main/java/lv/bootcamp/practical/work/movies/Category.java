package lv.bootcamp.practical.work.movies;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany( mappedBy = "category")
    private Set<Movie> movies;
}
