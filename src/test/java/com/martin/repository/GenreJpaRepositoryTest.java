package com.martin.repository;


import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
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
        genreRepository.save(inserted);
        Genre founded = genreRepository.findById(inserted.getId()).get();
        assertTrue(founded.equals(inserted));

        genreRepository.deleteAll();
    }

    @Test
    public void checkCount()  {
        long beforeCount = genreRepository.count();
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);
        Genre horror = new Genre("Horror");
        genreRepository.save(horror);
        Genre detective = new Genre("Detective");
        genreRepository.save(detective);
        long afterCount = genreRepository.count();
        assertEquals(afterCount-beforeCount, 3);

        genreRepository.deleteAll();
    }

    @Test
    public void checkGetAll()  {
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);
        Genre horror = new Genre("Horror");
        genreRepository.save(horror);
        Genre detective = new Genre("detective");

        Set<Genre> set = Sets.newHashSet(genreRepository.findAll());

        assertTrue(set.contains(comedy) &&
                set.contains(horror) &&
                !set.contains(detective));

        genreRepository.deleteAll();
    }

    @Test
    public void checkGetBooks(){
        Author pushkin = new Author("Alex", "Pushkin");
        authorRepository.save(pushkin);
        Author levashov = new Author("Hikolay", "Levashov");
        authorRepository.save(levashov);
        Genre horror = new Genre("Horror");
        genreRepository.save(horror);
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);

        Book book1 = new Book("titl1", pushkin, horror);
        bookRepository.save(book1);
        Book book2 = new Book("titl2", pushkin, horror);
        bookRepository.save(book2);
        Book book3 = new Book("titl3", levashov, horror);
        bookRepository.save(book3);
        Book book4 = new Book("titl4", levashov, comedy);
        bookRepository.save(book4);

        Set<Book> set = Sets.newHashSet(genreRepository.getBooks(horror.getId()));

        assertTrue(set.contains(book1) && set.contains(book2) &&
                set.contains(book3) && !set.contains(book4));

        bookRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    public void checkUpdate() {
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);

        String title = "Horror";
        comedy.setTitle(title);
        genreRepository.save(comedy);

        Genre byId = genreRepository.findById(comedy.getId()).get();

        assertTrue(byId.getTitle().equals(title));

        genreRepository.deleteAll();
    }

    @Test
    public void checkDelete() {
        Genre horror = new Genre("Horror");
        genreRepository.save(horror);
        Genre comedy = new Genre("Comedy");
        genreRepository.save(comedy);

        genreRepository.deleteById(horror.getId());

        List<Genre> all = Lists.newArrayList(genreRepository.findAll());

        assertTrue(!all.contains(horror) && all.contains(comedy) );

        genreRepository.deleteAll();
    }

    @Test
    public void checkDeleteGenreWithBook() {
        Author author1 = new Author("Alex", "Petrov");
        authorRepository.save(author1);
        Author author2 = new Author("Alex", "Sidorov");
        authorRepository.save(author2);
        Author author3 = new Author("Ivan", "Sidorov");
        authorRepository.save(author3);

        Genre genre1 = new Genre("Horror");
        genreRepository.save(genre1);
        Genre genre2 = new Genre("Comedy");
        genreRepository.save(genre2);
        Genre genre3 = new Genre("Drama");
        genreRepository.save(genre3);

        Book book1 = new Book("titl1", author1, genre1);
        bookRepository.save(book1);
        Book book2 = new Book("titl2", author2, genre2);
        bookRepository.save(book2);

        genreRepository.deleteById(genre1.getId());

        System.out.println("----------------------------");
        Iterable<Author> authors = authorRepository.findAll();
        for (Author author:authors) {
            System.out.println(author);
        }
        Iterable<Genre> genres = genreRepository.findAll();
        for (Genre genre: genres) {
            System.out.println(genre);
        }
        Iterable<Book> books = bookRepository.findAll();
        for (Book book: books) {
            System.out.println(book);
        }

        Set<Genre> setGenres = Sets.newHashSet(genres);
        Set<Book> setBooks = Sets.newHashSet(books);
        assertTrue(!setGenres.contains(genre1) && setGenres.contains(genre2) && setGenres.contains(genre3));
        assertTrue( !setBooks.contains(book1) && setBooks.contains(book2));

        genreRepository.deleteAll();
    }

}
