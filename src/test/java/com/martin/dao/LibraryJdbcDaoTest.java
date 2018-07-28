package com.martin.dao;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryJdbcDaoTest {

    @Autowired
    private LibraryJDBCdao dao;

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthorWithNullFirst() {
       dao.insert( new Author(null, "Ivan"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthorWithNullLastname() {
        dao.insert( new Author("Ivan", null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthorWithLonger32Firstname() {
        String firstname = new String(new char[33]);
        String lastname = new String(new char[32]);
        dao.insert( new Author(firstname, lastname));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthorWithLonger32Lastname() {
        String firstname = new String(new char[32]);
        String lastname = new String(new char[33]);
        dao.insert( new Author(firstname, lastname));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertGenreWithNullTitle() {
        dao.insert( new Genre(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertGenreWithLonger32Title() {
        String title = new String(new char[33]);
        dao.insert( new Genre(title));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertBookWithNullTitle() {
        dao.insert( new Book(null, null, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertBookWithLonger32Title() {
        String title = new String(new char[33]);
        dao.insert( new Book(title, null, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterGetAllAuthorsWithNegativePage() {
        dao.getAll( Author.class, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterGetAllAuthorsWithNegativeAmountByOnePage() {
        dao.getAll( Author.class,1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterGetAllGenresWithNegativePage() {
        dao.getAll( Genre.class, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterGetAllGenresWithNegativeAmountByOnePage() {
        dao.getAll( Genre.class,1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterGetAlBooksWithNegativePage() {
        dao.getAll( Genre.class, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterGetAllBooksWithNegativeAmountByOnePage() {
        dao.getAll( Genre.class,1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateAuthorWithNullFirst() {
        dao.update( 1, new Author(null, "Ivan"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateAuthorWithNullLastname() {
        dao.update( 1, new Author("Ivan", null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateAuthorWithLonger32Firstname() {
        String firstname = new String(new char[33]);
        String lastname = new String(new char[32]);
        dao.update(1, new Author(firstname, lastname));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateAuthorWithLonger32Lastname() {
        String firstname = new String(new char[32]);
        String lastname = new String(new char[33]);
        dao.update(1, new Author(firstname, lastname));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateGenreWithNullTitle() {
        dao.update(1, new Genre(null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateGenreWithLonger32Title() {
        String title = new String(new char[33]);
        dao.update( 1, new Genre(title));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateBookWithNullTitle() {
        dao.update(1, new Book(null, new Author("Ivan", "Ivan"), new Genre ("Horror")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateBookWithNullAuthor() {
        dao.update(1, new Book("title", null, new Genre ("Horror")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateBookWithNullGenre() {
        dao.update(1, new Book("title", new Author("Ivan", "Ivan"), null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateBookWithGenreWithNullId() {
        Author author = new Author("Ivan", "Ivan");
        author.setId(0);
        Genre genre = new Genre("Horror");
        genre.setId(1);
        dao.update(1, new Book("title",author, genre));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateBookWithAuthorWithNullId() {
        Author author = new Author("Ivan", "Ivan");
        author.setId(1);
        Genre genre = new Genre("Horror");
        genre.setId(0);
        dao.update(1, new Book("title",author, genre));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterUpdateBookWithLonger32Title() {
        String title = new String(new char[33]);
        dao.update( 1, new Book(title, null, null));
    }
}
