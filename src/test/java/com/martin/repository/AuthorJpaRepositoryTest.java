package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorJpaRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void checkInsertAuthor() {
        Author inserted = new Author("Sergey", "Esenin");
        authorRepository.insert(inserted);
        Author founded = authorRepository.findById(inserted.getId());
        assertTrue(founded.equals(inserted));

        authorRepository.delete(inserted.getId());
    }

    @Test
    public void checkCount()  {
        long beforeCount = authorRepository.getCount();
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.insert(pushkin);
        Author esenin = new Author("Sergey", "Esenin");
        authorRepository.insert(esenin);
        Author tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.insert(tolstoy);
        long afterCount = authorRepository.getCount();
        assertEquals(afterCount-beforeCount, 3);

        authorRepository.delete(pushkin.getId());
        authorRepository.delete(esenin.getId());
        authorRepository.delete(tolstoy.getId());
    }

    @Test
    public void checkGetAll()  {
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.insert(pushkin);
        Author tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.insert(tolstoy);
        Author dostoevskiy = new Author("Fedor", "Dostoevskiy");

        Set<Author> set = new HashSet<>(authorRepository.getAll(1, 100));

        assertTrue(set.contains(pushkin) &&
                set.contains(tolstoy) &&
                !set.contains(dostoevskiy));

        authorRepository.delete(pushkin.getId());
        authorRepository.delete(tolstoy.getId());
    }

    @Test
    public void checkGetBooks(){
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.insert(pushkin);
        Author levashov = new Author("Hikolay", "Levashov");
        authorRepository.insert(levashov);
        Genre genre = new Genre("Horror");
        genreRepository.insert(genre);

        Book book1 = new Book("titl1", pushkin, genre);
        bookRepository.insert(book1);
        Book book2 = new Book("titl2", pushkin, genre);
        bookRepository.insert(book2);
        Book book3 = new Book("titl3", pushkin, genre);
        bookRepository.insert(book3);
        Book book4 = new Book("titl4", levashov, genre);
        bookRepository.insert(book4);

        Set<Book> set = new HashSet<>(authorRepository.getBooks(pushkin.getId()));

        assertTrue(set.contains(book1) && set.contains(book2) &&
                set.contains(book3) && !set.contains(book4));

        bookRepository.delete(book1.getId());
        bookRepository.delete(book2.getId());
        bookRepository.delete(book3.getId());
        bookRepository.delete(book4.getId());
        authorRepository.delete(pushkin.getId());
        authorRepository.delete(levashov.getId());
        genreRepository.delete(genre.getId());
    }

    @Test
    public void checkUpdate() {
        Author petrov = new Author("Alex", "Petrov");
        authorRepository.insert(petrov);

        String newLastname = "Kozlov";
        Author updatedPetrov = new Author(petrov.getFirstname(), newLastname);
        authorRepository.update(petrov.getId(), updatedPetrov);

        Author byId = authorRepository.findById(petrov.getId());
        assertTrue(byId.equals(updatedPetrov));
        authorRepository.delete(petrov.getId());
    }

    @Test
    public void checkDelete() {
        Author author1 = new Author("Alex", "Petrov");
        authorRepository.insert(author1);

        Author author2 = new Author("Alex", "Sidorov");
        authorRepository.insert(author2);

        Author author3 = new Author("Ivan", "Sidorov");
        authorRepository.insert(author3);

        authorRepository.delete(author1.getId());

        List<Author> all = authorRepository.getAll(1, 100);

        assertTrue(!all.contains(author1) && all.contains(author2) && all.contains(author3));

        authorRepository.delete(author2.getId());
        authorRepository.delete(author3.getId());
    }

    @Test
    public void sqlInjectionTest() {
        Author author1 = new Author("Alex", "Petrov");
        authorRepository.insert(author1);

        Author author2 = new Author("Alex", "Sidorov");
        authorRepository.insert(author2);

        authorRepository.update(1, new Author("Alex", "; drop table authors"));

        Author byId = authorRepository.findById(1);
        System.out.println(byId);

        authorRepository.delete(author1.getId());
        authorRepository.delete(author2.getId());
    }
}