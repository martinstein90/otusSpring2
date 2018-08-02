package com.martin.repository;

import com.martin.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryJpa implements  BookRepository{
    @Override
    public int insert(Book book) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        return null;
    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public List<Book> find(Book book) {
        return null;
    }

    @Override
    public int update(int id, Book book) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}
