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
        Author inserted = authorRepository.insert(new Author("Sergey", "Esenin"));
        Author founded = authorRepository.findById(inserted.getId());
        assertTrue(founded.equals(inserted));
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
    }

    @Test
    public void checkGetAll()  {
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.insert(pushkin);

        Author tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.insert(tolstoy);

        Author dostoevskiy = new Author("Fedor", "Dostoevskiy");

        Set<Author> set = new HashSet<>(authorRepository.getAll(1, 100));

        assertTrue(set.contains(pushkin) && set.contains(tolstoy) &&
                            !set.contains(dostoevskiy));
    }

    @Test
    public void checkGetBooks(){
        Author pushkin =authorRepository.insert(new Author("Alex", "Pushkin"));
        Author levashov =authorRepository.insert(new Author("Hikolay", "Levashov"));
        Genre genre = genreRepository.insert(new Genre("Horror"));

        Book book1 = bookRepository.insert(new Book("titl1", pushkin, genre));
        Book book2 = bookRepository.insert(new Book("titl2", pushkin, genre));
        Book book3 = bookRepository.insert(new Book("titl3", pushkin, genre));
        Book book4 = bookRepository.insert(new Book("titl4", levashov, genre));

        Set<Book> set = new HashSet<>(authorRepository.getBooks(pushkin.getId()));

        assertTrue(set.contains(book1) && set.contains(book2) &&
                set.contains(book3) && !set.contains(book4));
    }

    @Test
    public void checkUpdate() {
        Author petrov = new Author("Alex", "Petrov");
        authorRepository.insert(petrov);

        Author sidorov = new Author("Alex", "Sidorov");
        authorRepository.insert(sidorov);

        Author sidorov2 = new Author("Ivan", "Sidorov");
        authorRepository.insert(sidorov2);

        String newLastname = "Kozlov";
        Author updatedPetrov = authorRepository.update(petrov.getId(), new Author(petrov.getFirstname(), newLastname));

        assertTrue(updatedPetrov.getFirstname().equals(petrov.getFirstname()) &&
                updatedPetrov.getLastname().equals(newLastname));
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
    }

    @Test
    public void sqlInjectionTest() {
        Author author1 = new Author("Alex", "Petrov");
        authorRepository.insert(author1);

        Author author2 = new Author("Alex", "Sidorov");
        authorRepository.insert(author2);

        Author author3 = new Author("Ivan", "Sidorov");
        authorRepository.insert(author3);

        authorRepository.update(1, new Author("Alex", "; drop table authors"));

        Author byId = authorRepository.findById(1);
        System.out.println(byId);


    }
}