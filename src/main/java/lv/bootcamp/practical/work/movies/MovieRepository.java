package lv.bootcamp.practical.work.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Query(value = "SELECT * FROM movies WHERE (:name is null or name LIKE %:name%)",
            nativeQuery = true)
    Page<Movie> findByName(@Param("name") String name, Pageable pageRequest);

    @Query(value = "SELECT * FROM movies WHERE (:name is null or name LIKE %:name% AND category = :id)",
            nativeQuery = true)
    Page<Movie> findByNameAndId(@Param("name") String name, @Param("id") Integer category, Pageable pageRequest);

    @Query(value = "SELECT * FROM movies WHERE category = :id",
            nativeQuery = true)
    Page<Movie> findByCategoryId(@Param("id") Integer category, Pageable pageRequest);

    @Query(value = "SELECT * FROM movies WHERE (:name is null or name LIKE %:name%)",
            nativeQuery = true)
    Collection<Movie> findMovieByName(@Param("name") String name);

    List<Movie> findTop5ByOrderByViewsDesc();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Movie movie set movie.views = movie.views + :incrementBy where movie.id = :id")
    int incrementMovieViews(@Param("id") Integer id, @Param("incrementBy") int incrementBy);

}
