package com.martin.service;

import com.martin.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorRxServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    public void checkAddAuthor() throws Exception {
        String firstname = "Ivan123";
        String lastname = "Petrov3";
        Author added = authorService.add(firstname, lastname).block();
        assertNotNull(added.getId());
        assertEquals(added.getFirstname(), firstname);
        assertEquals(added.getLastname(), lastname);
    }

    @Test(expected = Exception.class)
    public void getExceptionAfterDoubleInsertOneAuthor() throws Exception {
        String firstname = "Alex";
        String lastname = "Petrov";
        authorService.add(firstname, lastname).block();
        authorService.add(firstname, lastname).block();
    }

    @Test
    public void checkGetCount() throws Exception {
        long before = authorService.getCount().block();
        authorService.add("Alex", "Petrov").block();
        authorService.add("Ivan", "Petrov").block();
        authorService.add("Kirill", "Petrov").block();
        long after = authorService.getCount().block();
        assertEquals(3, after-before);
    }

    @Test
    public void checkGetAllByPages() throws Exception {
        List<Author> savedAuthors= new ArrayList<>(20);
        for (int i=0; i<20; i++)
            savedAuthors.add(authorService.add("Alex"+i, "Petrov"+i).block());

        List<Author> authors = authorService.getAll(2, 4).buffer().blockLast();

        assertEquals(savedAuthors.get(8) ,authors.get(0));
        assertEquals(savedAuthors.get(9) ,authors.get(1));
        assertEquals(savedAuthors.get(10) ,authors.get(2));
        assertEquals(savedAuthors.get(11) ,authors.get(3));
    }

    @Test
    public void checkGetAll() throws Exception {
        List<Author> savedAuthors= new ArrayList<>(20);
        for (int i=0; i<20; i++)
            savedAuthors.add(authorService.add("Alex"+i, "Petrov"+i).block());

        List<Author> authors = authorService.getAll(0, Integer.MAX_VALUE).buffer().blockLast();

        assertEquals(authors.size() ,20);
    }
    
    @Test
    public void checkFind() throws Exception {
        Author aPetrov = authorService.add("Alex", "Petrov").block();
        Author iDzuba = authorService.add("Ivan", "Dzuba").block();
        Author aSidirov = authorService.add("Alex", "Sidorov").block();
        Author lSidirov = authorService.add("Leo", "Sidorov").block();

        List<Author> authors = authorService.find("Alex", null).buffer().blockLast();
        assertTrue(authors.contains(aPetrov));
        assertTrue(authors.contains(aSidirov));
        assertTrue(!authors.contains(iDzuba));
        assertTrue(!authors.contains(lSidirov));

        authors = authorService.find(null, "Sidorov").buffer().blockLast();;
        assertTrue(authors.contains(aSidirov));
        assertTrue(authors.contains(lSidirov));
        assertTrue(!authors.contains(iDzuba));
        assertTrue(!authors.contains(aPetrov));

        authors = authorService.find("Alex", "Petrov").buffer().blockLast();
        assertTrue(authors.contains(aPetrov));
        assertTrue(authors.contains(aSidirov));
    }

    @Test
    public void checkUpdate() throws Exception {
        Author petrov = authorService.add("Alex", "Petrov").block();
        String newFirstname = "Ivan";
        Author updatedAuthor = authorService.update(petrov.getId(), newFirstname, null).block();
        assertEquals(updatedAuthor.getFirstname(), newFirstname);
    }

    @Test
    public void checkDeleteById() throws Exception {
        Author aPetrov = authorService.add("Alex", "Petrov").block();
        Author iDzuba = authorService.add("Ivan", "Dzuba").block();

        authorService.delete(aPetrov.getId()).block();

        List<Author> authors = authorService.getAll(0, Integer.MAX_VALUE).buffer().blockLast();

        assertTrue(!authors.contains(aPetrov));
        assertTrue(authors.contains(iDzuba));
    }

    @Test
    public void checkDeleteAll() throws Exception {
        Author aPetrov = authorService.add("Alex", "Petrov").block();
        Author iDzuba = authorService.add("Ivan", "Dzuba").block();
        Author aSidirov = authorService.add("Alex", "Sidorov").block();
        Author lSidirov = authorService.add("Leo", "Sidorov").block();

        authorService.deleteAll().block();

        List<Author> authors = authorService.getAll(0, Integer.MAX_VALUE).buffer().blockLast();
        assertNull(authors);
    }
}
