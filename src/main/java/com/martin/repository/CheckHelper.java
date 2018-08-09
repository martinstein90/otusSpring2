package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;

class CheckHelper {

    static void checkInsert(Author author) {
        if(author.getFirstname() == null || author.getLastname() == null)
            throw new IllegalArgumentException("Must be firstname != null and lastname != null by author");

        if(author.getFirstname().length() > 32 || author.getLastname().length() > 32 )
            throw new IllegalArgumentException("Must be firstname.length() < 32 and lastname.length() < 32 by author");
    }

    static void checkInsert(Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32)
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");
    }

    static void checkInsert(Comment comment) {
        if(comment.getComment() == null)
            throw new IllegalArgumentException("Must be comment != null by comment");

        if(comment.getComment().length() > 255 )
            throw new IllegalArgumentException("Must be comment.length() < 255  by comment");
    }

    static void checkInsert(Book book) {
        if(book.getTitle() == null || book.getAuthor() == null || book.getGenre() == null)
            throw new IllegalArgumentException("Must be title != null and author =! null and genre != null by book");

        if(book.getAuthor().getId() == 0 || book.getGenre().getId() == 0)
            throw new IllegalArgumentException("Must be id author =! 0 and id genre != 0 by book");

        if(book.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by book");
    }

    static void checkGetAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <=0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");
    }

    static void checkFindById(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }

    static void checkFind(Author author) {
        if(author.getFirstname() == null && author.getLastname() == null)
            throw new IllegalArgumentException("Must be firstname != null or lastname != null by author");

        if(author.getFirstname() != null) {
            if (author.getFirstname().length() > 32)
                throw new IllegalArgumentException("Must be firstname.length() < 32  by author");
        }
        if(author.getLastname() != null) {
            if (author.getLastname().length() > 32)
                throw new IllegalArgumentException("Must be  lastname.length() < 32  by author");
        }
    }

    static void checkFind(Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");
    }

    static void checkFind(Book book) {
        if(book.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by book");

        if(book.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by book");
    }

    static void checkUpdate(Author author) {
        if(author.getFirstname() == null && author.getLastname() == null)
            throw new IllegalArgumentException("Must be fistname != null or lastname != null by author");

        if(author.getFirstname().length() > 32 || author.getLastname().length() > 32 )
            throw new IllegalArgumentException("Must be firstname.length() < 32  and lastname.length() < 32  by author");
    }

    static void checkUpdate(Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");

    }

    static void checkUpdate(Comment comment) {
        if(comment.getComment() == null )
            throw new IllegalArgumentException("Must be comment != null  by comment");

        if(comment.getComment().length() > 255 )
            throw new IllegalArgumentException("Must be comment.length() < 255  by comment");

    }

    static void checkUpdate(Book book) {
        if(book.getTitle() == null && book.getAuthor() == null && book.getGenre()== null)
            throw new IllegalArgumentException("Must be title != null or genre != null or author != null by book");
        if(book.getAuthor() != null)
            if(book.getAuthor().getId() == 0)
                throw new IllegalArgumentException("Must be id author != 0 by book");
        if(book.getGenre() != null)
            if(book.getGenre().getId()==0)
                throw new IllegalArgumentException("Must be id genre != 0 by book");
        if(book.getTitle() != null)
            if(book.getTitle().length() > 32 )
                throw new IllegalArgumentException("Must be title.length() < 32 by book");
    }

    static void checkUpdate(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }

    static void checkDelete(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }
}
