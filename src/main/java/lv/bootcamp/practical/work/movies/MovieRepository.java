package lv.bootcamp.practical.work.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Query(value = "SELECT * FROM movies WHERE name LIKE %:name%",
            nativeQuery = true)
    Page<Movie> findByName(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT * FROM movies WHERE name LIKE %:name% AND category = :id",
            nativeQuery = true)
    Collection<Movie> findByNameAndId(@Param("name") String name, @Param("id") Integer category);

    @Query(value = "SELECT * FROM movies WHERE category = :id",
            nativeQuery = true)
    Collection<Movie> findByCategoryId(@Param("id") Integer category);
}
