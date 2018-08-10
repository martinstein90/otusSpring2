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

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentJpaRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;


    private Genre horror, comedy;
    private Author pushkin, esenin, tolstoy;
    private Book ruslanLudmila, blackMan;

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
        ruslanLudmila = new Book("Ruslan and Ludmila", pushkin, comedy);
        bookRepository.insert(ruslanLudmila);
        blackMan =  new Book("Black man", pushkin, horror);
        bookRepository.insert(blackMan);
    }

    @After
    public void tearDown() {
        bookRepository.delete(ruslanLudmila.getId());
        bookRepository.delete(blackMan.getId());
        genreRepository.delete(horror.getId());
        genreRepository.delete(comedy.getId());
        authorRepository.delete(pushkin.getId());
        authorRepository.delete(esenin.getId());
        authorRepository.delete(tolstoy.getId());
    }

    @Test
    public void checkInsertComment() {
        Comment inserted = new Comment( "Super", blackMan);
        commentRepository.insert(inserted);
        Comment founded = commentRepository.findById(inserted.getId());
        assertTrue(founded.equals(inserted));

        commentRepository.delete(inserted.getId());
    }

    @Test
    public void checkCount()  {
        long beforeCount = commentRepository.getCount();
        Comment comment1 = new Comment( "Super", ruslanLudmila);
        commentRepository.insert(comment1);
        Comment comment2 = new Comment( "Super", ruslanLudmila);
        commentRepository.insert(comment2);
        Comment comment3 = new Comment( "Super", blackMan);
        commentRepository.insert(comment3);
        long afterCount = commentRepository.getCount();

        assertEquals(afterCount-beforeCount, 3);

        commentRepository.delete(comment1.getId());
        commentRepository.delete(comment2.getId());
        commentRepository.delete(comment3.getId());
    }

    @Test
    public void checkGetAll()  {
        Comment comment1 = new Comment( "Super", ruslanLudmila);
        commentRepository.insert(comment1);
        Comment comment2 = new Comment( "Super", blackMan);
        commentRepository.insert(comment2);
        Comment comment3 = new Comment( "Fail", blackMan);
        List<Comment> list = commentRepository.getAll(1, 100);

        assertTrue(list.contains(comment1) &&
                list.contains(comment2) &&
                !list.contains(comment3));

        commentRepository.delete(comment1.getId());
        commentRepository.delete(comment2.getId());
    }

    @Test
    public void checkUpdate() {
        Comment comment = new Comment( "Super", ruslanLudmila);
        commentRepository.insert(comment);

        String title = "newComment";
        Comment updated = new Comment(title, null);
        commentRepository.update(comment.getId(), updated);

        Comment byId = commentRepository.findById(comment.getId());

        assertTrue(byId.getComment().equals(title));

        commentRepository.delete(comment.getId());
    }

    @Test
    public void checkDelete() {
        Comment comment1 = new Comment( "Super1", ruslanLudmila);
        commentRepository.insert(comment1);
        Comment comment2 = new Comment( "Super2", blackMan);
        commentRepository.insert(comment2);

        commentRepository.delete(comment1.getId());

        List<Comment> all = commentRepository.getAll(1, 100);

        assertTrue(!all.contains(comment1) && all.contains(comment2) );

        commentRepository.delete(comment2.getId());
    }
}
