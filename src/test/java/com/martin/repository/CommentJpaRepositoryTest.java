package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import org.assertj.core.util.Lists;
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
        genreRepository.save(horror);
        comedy = new Genre("Comedy");
        genreRepository.save(comedy);
        pushkin = new Author("Alex", "Pushkin");
        authorRepository.save(pushkin);
        esenin = new Author("Sergey", "Esenin");
        authorRepository.save(esenin);
        tolstoy = new Author("Leo", "Tolstoy");
        authorRepository.save(tolstoy);
        ruslanLudmila = new Book("Ruslan and Ludmila", pushkin, comedy);
        bookRepository.save(ruslanLudmila);
        blackMan =  new Book("Black man", pushkin, horror);
        bookRepository.save(blackMan);
    }

    @After
    public void tearDown() {
        commentRepository.deleteAll();
        bookRepository.deleteAll();
        genreRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    public void checkInsertComment() {
        Comment inserted = new Comment( "Super", blackMan);
        commentRepository.save(inserted);
        Comment founded = commentRepository.findById(inserted.getId()).get();
        assertTrue(founded.equals(inserted));
    }

    @Test
    public void checkCount()  {
        long beforeCount = commentRepository.count();
        Comment comment1 = new Comment( "Super", ruslanLudmila);
        commentRepository.save(comment1);
        Comment comment2 = new Comment( "Super", ruslanLudmila);
        commentRepository.save(comment2);
        Comment comment3 = new Comment( "Super", blackMan);
        commentRepository.save(comment3);
        long afterCount = commentRepository.count();

        assertEquals(afterCount-beforeCount, 3);
    }

    @Test
    public void checkGetAll()  {
        Comment comment1 = new Comment( "Super", ruslanLudmila);
        commentRepository.save(comment1);
        Comment comment2 = new Comment( "Super", blackMan);
        commentRepository.save(comment2);
        Comment comment3 = new Comment( "Fail", blackMan);
        List<Comment> list = Lists.newArrayList(commentRepository.findAll());

        assertTrue(list.contains(comment1) &&
                list.contains(comment2) &&
                !list.contains(comment3));
    }

    @Test
    public void checkUpdate() {
        Comment comment = new Comment( "Super", ruslanLudmila);
        commentRepository.save(comment);

        String newComment = "newComment";
        comment.setComment(newComment);
        commentRepository.save(comment);

        Comment byId = commentRepository.findById(comment.getId()).get();

        assertTrue(byId.getComment().equals(newComment));
    }

    @Test
    public void checkDelete() {
        Comment comment1 = new Comment( "Super1", ruslanLudmila);
        commentRepository.save(comment1);
        Comment comment2 = new Comment( "Super2", blackMan);
        commentRepository.save(comment2);

        commentRepository.deleteById(comment1.getId());

        List<Comment> all = Lists.newArrayList(commentRepository.findAll());

        assertTrue(!all.contains(comment1) && all.contains(comment2) );
    }

}
