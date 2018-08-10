package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.repository.GenreRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Genre add(String title) throws Exception {
        Genre genre = new Genre(title);
        try{
            genreRepository.insert(genre);
        }
        catch (DataIntegrityViolationException exception) {
            String causeMsg= exception.getCause().getCause().getMessage();
            if(causeMsg.contains("Нарушение уникального индекса или первичного ключ"))
                throw new Exception(String.format(DUPLICATE_ERROR_STRING, genre));
            else
                throw new Exception(String.format(ERROR_STRING, genre));
        }
        return genre;
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
        catch (DataIntegrityViolationException exception) {
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
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genres;
    }

    @Override
    public List<Book> getBooks(long id) {
        return genreRepository.getBooks(id);
    }

    @Override
    public Genre update(long id, String title) throws Exception {
        Genre genre = new Genre(title);
        try {
            genreRepository.update(id, genre);
        }
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
        return genre;
    }

    @Override
    public void delete(long id) throws Exception {
        try {
            genreRepository.delete(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Genre.class.getSimpleName()));
        }
    }
}
