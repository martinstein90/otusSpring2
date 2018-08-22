package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.assertj.core.util.Sets;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void checkInsertAuthor() {
        Author inserted = new Author("Sergey", "Esenin");
        authorRepository.save(inserted);
        Author founded = authorRepository.findById(inserted.getId()).get();
        assertTrue(founded.equals(inserted));
    }

    @Test
    public void checkCount()  {
        long beforeCount = authorRepository.count();
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.save(pushkin);
        Author esenin = new Author("Sergey", "Esenin");
        authorRepository.save(esenin);
        Author tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.save(tolstoy);
        long afterCount = authorRepository.count();
        assertEquals(afterCount-beforeCount, 3);
    }

    @Test
    public void checkGetAll()  {
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.save(pushkin);
        Author tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.save(tolstoy);
        Author dostoevskiy = new Author("Fedor", "Dostoevskiy");

        Set<Author> set = Sets.newHashSet(authorRepository.findAll());

        assertTrue(set.contains(pushkin) &&
                set.contains(tolstoy) &&
                !set.contains(dostoevskiy));
    }

    @Test
    public void checkUpdate() {
        Author petrov = new Author("Alex", "Petrov");
        authorRepository.save(petrov);

        String newLastname = "Kozlov";
        petrov.setLastname(newLastname);
        authorRepository.save(petrov);

        Author byId = authorRepository.findById(petrov.getId()).get();
        assertTrue(byId.getLastname().equals(newLastname));
    }

    @Test
    public void checkDelete() {
        Author author1 = new Author("Alex", "Petrov");
        authorRepository.save(author1);
        Author author2 = new Author("Alex", "Sidorov");
        authorRepository.save(author2);
        Author author3 = new Author("Ivan", "Sidorov");
        authorRepository.save(author3);

        authorRepository.deleteById(author1.getId());

        Set<Author> all = Sets.newHashSet(authorRepository.findAll());

        assertTrue(!all.contains(author1) && all.contains(author2) && all.contains(author3));
    }


    @Test
    public void sqlInjectionTest() {
        Author author1 = new Author("Ivan",  "Petrov");
        authorRepository.save(author1);

        Author author2 = new Author("Alex",  "; drop table authors");
        authorRepository.save(author2);

        Author byId = authorRepository.findById(author1.getId()).get();
        assertTrue(author1.equals(byId));

    }

}