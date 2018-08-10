package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import org.junit.After;
import org.junit.Before;
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
public class BookJpaRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Genre horror, comedy;
    private Author pushkin, esenin, tolstoy;

    @Before
    public void setUp() {
        horror = new Genre("Horror");
        genreRepository.insert(horror);
        comedy = new Genre("Comedy");
        genreRepository.insert(comedy);
        pushkin = new Author("Alex", "Pushkin");
        authorRepository.insert(pushkin);
        esenin = new Author("Sergey", "Esenin");
        authorRepository.insert(esenin);
        tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.insert(tolstoy);
    }

    @After
    public void tearDown() {
        genreRepository.delete(horror.getId());
        genreRepository.delete(comedy.getId());
        authorRepository.delete(pushkin.getId());
        authorRepository.delete(esenin.getId());
        authorRepository.delete(tolstoy.getId());
    }

    @Test
    public void checkInsertBook() {
        Book book = new Book( "Book1", pushkin, horror);
        bookRepository.insert(book);
        Book founded = bookRepository.findById(book.getId());
        assertTrue(founded.equals(founded));

        bookRepository.delete(book.getId());
    }

    @Test
    public void checkCount()  {
        long beforeCount = bookRepository.getCount();
        Book book1 = new Book( "Book1", pushkin, horror);
        bookRepository.insert(book1);
        Book book2 = new Book( "Book2", pushkin, comedy);
        bookRepository.insert(book2);
        Book book3 = new Book( "Book3", esenin, comedy);
        bookRepository.insert(book3);
        long afterCount = bookRepository.getCount();

        assertEquals(afterCount-beforeCount, 3);

        bookRepository.delete(book1.getId());
        bookRepository.delete(book2.getId());
        bookRepository.delete(book3.getId());
    }

    @Test
    public void checkGetAll()  {
        Book book1 = new Book( "Book1", esenin, horror);
        bookRepository.insert(book1);
        Book book2 = new Book( "Book2", pushkin, comedy);
        bookRepository.insert(book2);
        Book book3 = new Book( "Book3", tolstoy, comedy);
        List<Book> list = bookRepository.getAll(1, 100);

        assertTrue(list.contains(book1) &&
                list.contains(book2) &&
                !list.contains(book3));

        bookRepository.delete(book1.getId());
        bookRepository.delete(book2.getId());
    }


    @Test
    public void getComment() {
        Book book = new Book("Book1", pushkin, horror);
        bookRepository.insert(book);
        Comment comment1 = new Comment("Comment1", book);
        commentRepository.insert(comment1);
        Comment comment2 = new Comment("Comment2", book);
        commentRepository.insert(comment2);

        Set<Comment> comments = new HashSet<>(bookRepository.getComments(book.getId()));

        assertTrue(comments.contains(comment1) && comments.contains(comment2));
        commentRepository.delete(comment1.getId());
        commentRepository.delete(comment2.getId());
        bookRepository.delete(book.getId());
    }
}
