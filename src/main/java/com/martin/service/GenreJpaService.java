package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.repository.GenreRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreJpaService implements GenreService{

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";

    private final GenreRepository genreRepository;

    public GenreJpaService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void add(String title) throws Exception {
        Genre genre = new Genre(title);
        try{
            genreRepository.insert(genre);
        }
        catch (DuplicateKeyException exception){
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, genre));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, genre));
        }
    }

    @Override
    public long getCount() {
        return genreRepository.getCount();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        return genreRepository.getAll(page, amountByOnePage);
    }

    @Override
    public Genre findById(long id) throws Exception {
        Genre genre;
        try {
            genre = genreRepository.findById(id);
        }
        catch (EmptyResultDataAccessException exception) {
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id));
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Override
    public List<Genre> find(String title) throws Exception {
        Genre genre = new Genre(title);
        List<Genre> genres;
        try {
            genres = genreRepository.find(genre);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genres;
    }

    @Override
    public List<Book> getBooks(long id) {
        return genreRepository.getBooks(id);
    }

    @Override
    public void update(long id, String title) throws Exception {
        Genre genre = new Genre(title);
        int amountUpdated;
        try {
            amountUpdated = genreRepository.update(id, genre);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }

        if(amountUpdated == 0)
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id));
    }

    @Override
    public void delete(long id) throws Exception {
        try {
            genreRepository.delete(id);
        }
        catch(DataAccessException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
    }
}
