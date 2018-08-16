package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.repository.GenreRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.martin.service.Helper.ASSOCIATED_ERROR_STRING;
import static com.martin.service.Helper.EMPTY_RESULT_BY_ID_ERROR_STRING;
import static com.martin.service.Helper.handlerException;

@Service
public class GenreJpaService implements GenreService {

    private final GenreRepository genreRepository;

    public GenreJpaService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre add(String title) throws Exception {
        Genre genre = new Genre(title);
        try{
            genreRepository.save(genre);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, genre.toString());
        }
        return genre;
    }

    @Override
    public long getCount() {
        return genreRepository.count();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        return genreRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public Genre findById(long id) throws Exception {
        Genre byId = genreRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id)));
           return byId;
    }

    @Override
    public List<Genre> find(String title) throws Exception {
        List<Genre> genres = null;
        try {
            genres = Lists.newArrayList(genreRepository.findByTitle(title));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Genre.class.getSimpleName());
        }
        return genres;
    }

    @Override
    public List<Book> getBooks(long id) {
        return Lists.newArrayList(genreRepository.getBooks(id));
    }

    @Override
    public Genre update(long id, String title) throws Exception {
        Genre genre = genreRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Genre.class.getSimpleName(), id)));
        if(title!= null) {
            genre.setTitle(title);
            try {
                genreRepository.save(genre);
            }
            catch (DataIntegrityViolationException exception) {
                handlerException(exception, Author.class.getSimpleName());
            }
        }
        return genre;
    }

    @Override
    public void delete(long id, boolean withBook) throws Exception {
        if(getBooks(id).isEmpty() && !withBook)
            throw new IllegalStateException(String.format(ASSOCIATED_ERROR_STRING, Genre.class.getSimpleName(), Book.class.getSimpleName()));
        else
            deleteWithBook(id);
    }

    @Override
    public void delete(long id) throws Exception {
        if(getBooks(id).isEmpty())
            throw new IllegalStateException(String.format(ASSOCIATED_ERROR_STRING, Genre.class.getSimpleName(), Book.class.getSimpleName()));
        else
            deleteWithBook(id);
    }

    private void deleteWithBook(long id) throws Exception {
        try {
            genreRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Genre.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        genreRepository.deleteAll();
    }
}
