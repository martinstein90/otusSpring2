package com.martin.dao;

import com.martin.dao.LibraryJDBCdao;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryJdbcDaoTest {

    @Autowired
    private LibraryJDBCdao dao;

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthotWithNullFirst() {
       dao.insert( new Author(null, "Ivan"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthotWithNullLastname() {
        dao.insert( new Author("Ivan", null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthotWithLonger32Firstname() {
        String firstname = new String(new char[33]);
        String lastname = new String(new char[32]);
        dao.insert( new Author(firstname, lastname));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExceptionAfterInsertAuthotWithLonger32Lastname() {
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
}
