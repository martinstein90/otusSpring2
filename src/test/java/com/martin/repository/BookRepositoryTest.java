package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.SchemaOutputResolver;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class BookRepositoryTest {

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
        genreRepository.save(horror);
        comedy = new Genre("Comedy");
        genreRepository.save(comedy);
        pushkin = new Author("Alex", "Pushkin");
        authorRepository.save(pushkin);
        esenin = new Author("Sergey", "Esenin");
        authorRepository.save(esenin);
        tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.save(tolstoy);
    }

    @Test
    public void checkInsertBook() {
        Book book = new Book( "Book1", pushkin, horror);
        bookRepository.save(book);
        Book founded = bookRepository.findById(book.getId()).get();
        assertTrue(founded.equals(founded));
    }

    @Test
    public void checkCount()  {
        long beforeCount = bookRepository.count();
        Book book1 = new Book( "Book1", pushkin, horror);
        bookRepository.save(book1);
        Book book2 = new Book( "Book2", pushkin, comedy);
        bookRepository.save(book2);
        Book book3 = new Book( "Book3", esenin, comedy);
        bookRepository.save(book3);
        long afterCount = bookRepository.count();

        assertEquals(afterCount-beforeCount, 3);
    }

    @Test
    public void checkGetBooksByAuthor(){
        Book book1 = new Book("titl1", pushkin, horror);
        bookRepository.save(book1);
        Book book2 = new Book("titl2", pushkin, horror);
        bookRepository.save(book2);
        Book book3 = new Book("titl3", pushkin, horror);
        bookRepository.save(book3);
        Book book4 = new Book("titl4", esenin, horror);
        bookRepository.save(book4);

        Set<Book> set = Sets.newHashSet(bookRepository.findByAuthor(pushkin.getId()));

        assertTrue(set.contains(book1) && set.contains(book2) &&
                set.contains(book3) && !set.contains(book4));
    }

    @Test
    public void checkGetBooksByGenre(){

        Book book1 = new Book("titl1", pushkin, horror);
        bookRepository.save(book1);
        Book book2 = new Book("titl2", pushkin, horror);
        bookRepository.save(book2);
        Book book3 = new Book("titl3", esenin, horror);
        bookRepository.save(book3);
        Book book4 = new Book("titl4", esenin, comedy);
        bookRepository.save(book4);

        Set<Book> set = Sets.newHashSet(bookRepository.findByGenre(horror.getId()));

        assertTrue(set.contains(book1) && set.contains(book2) &&
                set.contains(book3) && !set.contains(book4));
    }

    @Test
    public void checkGetAll()  {
        Book book1 = new Book( "Book1", esenin, horror);
        bookRepository.save(book1);
        Book book2 = new Book( "Book2", pushkin, comedy);
        bookRepository.save(book2);
        Book book3 = new Book( "Book3", tolstoy, comedy);
        List<Book> list = Lists.newArrayList(bookRepository.findAll());

        assertTrue(list.contains(book1) &&
                list.contains(book2) &&
                !list.contains(book3));

        bookRepository.deleteAll();
    }

    @Test
    public void checkGetComment() {
        Book book = new Book("Book1", pushkin, horror);
        bookRepository.save(book);
        Comment comment1 = new Comment("Comment1");
        commentRepository.save(comment1);
        bookRepository.addComment(book.getId(), comment1);
        Comment comment2 = new Comment("Comment2");
        commentRepository.save(comment2);
        bookRepository.addComment(book.getId(), comment2);

        Book newBook = bookRepository.findById(book.getId()).get();

        List<Comment> comments = newBook.getComments();

        assertTrue(comments.contains(comment1) && comments.contains(comment2));
    }

    @Test
    public void checkFindByTitleLike()  {
        Book book1 = new Book( "Asfffs", esenin, horror);
        bookRepository.save(book1);
        Book book2 = new Book( "Bfgfffs", pushkin, comedy);
        bookRepository.save(book2);
        Book book3 = new Book( "fffD", tolstoy, comedy);
        bookRepository.save(book3);
        Book book4 = new Book( "Rfff", tolstoy, comedy);
        bookRepository.save(book4);
        Book book5 = new Book( "Ggggg", tolstoy, comedy);
        bookRepository.save(book5);
        List<Book> books = Lists.newArrayList(bookRepository.findByTitleContaining("fff"));

        assertTrue(books.contains(book1) && books.contains(book2) &&
                books.contains(book3) && books.contains(book4) && !books.contains(book5));
    }

    @Test
    public void checkDelete() {
        Book book1 = new Book("Book1", pushkin, horror);
        bookRepository.save(book1);
        Book book2 = new Book("Book2", tolstoy, comedy);
        bookRepository.save(book2);
        Book book3 = new Book("Book3", tolstoy, comedy);
        bookRepository.save(book3);

        bookRepository.deleteById(book1.getId());


        Set<Book> books = Sets.newHashSet(bookRepository.findAll());
        assertTrue(!books.contains(book1) && books.contains(book2) && books.contains(book3));
    }
}
