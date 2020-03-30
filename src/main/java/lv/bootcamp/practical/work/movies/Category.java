package lv.bootcamp.practical.work.movies;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Category {
    @Id
    @Column( name = "categoriesID")
    @GeneratedValue(generator = "uuid")
    private int id;
    private String name;
}
