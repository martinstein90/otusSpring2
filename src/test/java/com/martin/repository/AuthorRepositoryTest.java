package com.martin.repository;

import com.martin.domain.Author;
import org.assertj.core.util.Sets;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void checkInsertAuthor() {
        Author inserted = new Author("Sergey", "Esenin");
        Author savedAuthor = authorRepository.save(inserted).block();
        Author foundAuthor = authorRepository.findById(savedAuthor.getId()).block();
        assertTrue(savedAuthor.equals(foundAuthor));
    }

    @Test(expected = DuplicateKeyException.class)
    public void getExceptionAfterDoubleInsertAuthor() {
        Author author = new Author("Sergey", "Esenin");
        Author author1 = new Author("Sergey", "Esenin");
        authorRepository.save(author).block();
        authorRepository.save(author1).block();
    }

    @Test
    public void checkCount()  {
        long beforeCount = authorRepository.count().block();
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.save(pushkin).block();
        Author esenin = new Author("Sergey", "Esenin");
        authorRepository.save(esenin).block();
        Author tolstoy = new Author("Leo1", "Tolstoy");
        authorRepository.save(tolstoy).block();
        long afterCount = authorRepository.count().block();
        assertEquals(3, afterCount-beforeCount);
    }

    @Test
    public void checkGetAll()  {
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.save(pushkin).block();
        Author tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.save(tolstoy).block();
        Set<Author> set = Sets.newHashSet(authorRepository.findAll().toIterable());
        assertTrue(set.contains(pushkin));
        assertTrue(set.contains(tolstoy));
    }

    @Test
    public void checkUpdate() {
        Author petrov = new Author("Alex", "Petrov");
        authorRepository.save(petrov).block();

        String newLastname = "Kozlov";
        petrov.setLastname(newLastname);
        authorRepository.save(petrov).block();

        Author byId = authorRepository.findById(petrov.getId()).block();
        assertTrue(byId.getLastname().equals(newLastname));
    }

    @Test
    public void checkDelete() {
        Author author1 = new Author("Alex", "Petrov");
        authorRepository.save(author1).block();
        Author author2 = new Author("Alex", "Sidorov");
        authorRepository.save(author2).block();
        Author author3 = new Author("Ivan", "Sidorov");
        authorRepository.save(author3).block();

        authorRepository.deleteById(author1.getId()).block();

        Set<Author> all = Sets.newHashSet(authorRepository.findAll().toIterable());

        assertTrue(!all.contains(author1));
        assertTrue(all.contains(author2));
        assertTrue(all.contains(author3));
    }

}