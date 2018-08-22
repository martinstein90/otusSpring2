package com.martin.service;

import com.martin.domain.Book;
import com.martin.domain.Comment;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookService {

    Book add(String title, ObjectId authorId, ObjectId genreId) throws Exception;
    Book add(String authorTitle, String authorFirsname, String authorLastname, String genreTitle) throws Exception;
    long getCount();
    List<Book> getAll(int page, int amountByOnePage);
    List<Book> findByTitle(String title) throws Exception;
    List<Book> findByAuthor(ObjectId authorId) throws Exception;
    List<Book> findByGenre(ObjectId genreId) throws Exception;
    Book findById(ObjectId id) throws Exception;
    void addComments(ObjectId booktId, ObjectId commentId) throws Exception;
    List<Comment> getComments(ObjectId id) throws Exception;
    Book update(ObjectId id, String title) throws Exception;
    Book update(ObjectId id, String bookTitle, ObjectId authorId, ObjectId genreId) throws Exception;
    void delete(ObjectId id) throws Exception;
    void deleteAll();
}
