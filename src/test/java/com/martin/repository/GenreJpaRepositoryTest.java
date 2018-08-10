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
public class GenreJpaRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void checkInsertGenre() {
        Genre inserted = new Genre("horror");
        genreRepository.insert(inserted);
        Genre founded = genreRepository.findById(inserted.getId());
        assertTrue(founded.equals(inserted));

        genreRepository.delete(inserted.getId());
    }

    @Test
    public void checkCount()  {
        long beforeCount = genreRepository.getCount();
        Genre comedy = new Genre("Comedy");
        genreRepository.insert(comedy);
        Genre horror = new Genre("Horror");
        genreRepository.insert(horror);
        Genre detective = new Genre("Detective");
        genreRepository.insert(detective);
        long afterCount = genreRepository.getCount();
        assertEquals(afterCount-beforeCount, 3);

        genreRepository.delete(comedy.getId());
        genreRepository.delete(horror.getId());
        genreRepository.delete(detective.getId());
    }

    @Test
    public void checkGetAll()  {
        Genre comedy = new Genre("Comedy");
        genreRepository.insert(comedy);
        Genre horror = new Genre("Horror");
        genreRepository.insert(horror);
        Genre detective = new Genre("detective");

        Set<Genre> set = new HashSet<>(genreRepository.getAll(1, 100));

        assertTrue(set.contains(comedy) &&
                set.contains(horror) &&
                !set.contains(detective));

        genreRepository.delete(comedy.getId());
        genreRepository.delete(horror.getId());
    }

    @Test
    public void checkGetBooks(){
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.insert(pushkin);
        Author levashov = new Author("Hikolay", "Levashov");
        authorRepository.insert(levashov);
        Genre horror = new Genre("Horror");
        genreRepository.insert(horror);
        Genre comedy = new Genre("Comedy");
        genreRepository.insert(comedy);

        Book book1 = new Book("titl1", pushkin, horror);
        bookRepository.insert(book1);
        Book book2 = new Book("titl2", pushkin, horror);
        bookRepository.insert(book2);
        Book book3 = new Book("titl3", levashov, horror);
        bookRepository.insert(book3);
        Book book4 = new Book("titl4", levashov, comedy);
        bookRepository.insert(book4);

        Set<Book> set = new HashSet<>(genreRepository.getBooks(horror.getId()));

        assertTrue(set.contains(book1) && set.contains(book2) &&
                set.contains(book3) && !set.contains(book4));

        bookRepository.delete(book1.getId());
        bookRepository.delete(book2.getId());
        bookRepository.delete(book3.getId());
        bookRepository.delete(book4.getId());
        genreRepository.delete(horror.getId());
        genreRepository.delete(comedy.getId());
    }

    @Test
    public void checkUpdate() {
        Genre comedy = new Genre("Comedy");
        genreRepository.insert(comedy);

        Genre horror = new Genre( "Horror");
        genreRepository.update(comedy.getId(), horror);

        Genre byId = genreRepository.findById(comedy.getId());

        assertTrue(byId.equals(horror));

        genreRepository.delete(comedy.getId());
    }

    @Test
    public void checkDelete() {
        Genre horror = new Genre("Horror");
        genreRepository.insert(horror);
        Genre comedy = new Genre("Comedy");
        genreRepository.insert(comedy);

        genreRepository.delete(horror.getId());

        List<Genre> all = genreRepository.getAll(1, 100);

        assertTrue(!all.contains(horror) && all.contains(comedy) );

        genreRepository.delete(comedy.getId());
    }

}
