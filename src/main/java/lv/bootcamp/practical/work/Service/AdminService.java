package lv.bootcamp.practical.work.Service;

import lv.bootcamp.practical.work.movies.CategoryRepository;
import lv.bootcamp.practical.work.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AdminService(MovieRepository movieRepository, CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
    }



}
