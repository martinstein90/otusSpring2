package com.martin.repository;


import com.martin.domain.Comment;
import org.assertj.core.util.Lists;
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
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void checkInsertComment() {
        Comment inserted = new Comment( "Super");
        commentRepository.save(inserted);
        Comment founded = commentRepository.findById(inserted.getId()).get();
        assertTrue(founded.equals(inserted));
    }

    @Test
    public void checkCount()  {
        long beforeCount = commentRepository.count();
        Comment comment1 = new Comment( "Super");
        commentRepository.save(comment1);
        Comment comment2 = new Comment( "Super");
        commentRepository.save(comment2);
        Comment comment3 = new Comment( "Super");
        commentRepository.save(comment3);
        long afterCount = commentRepository.count();

        assertEquals(afterCount-beforeCount, 3);
    }

    @Test
    public void checkGetAll()  {
        Comment comment1 = new Comment( "Super");
        commentRepository.save(comment1);
        Comment comment2 = new Comment( "Super2");
        commentRepository.save(comment2);
        Comment comment3 = new Comment( "Fail");
        List<Comment> list = Lists.newArrayList(commentRepository.findAll());

        assertTrue(list.contains(comment1) &&
                list.contains(comment2) &&
                !list.contains(comment3));
    }

    @Test
    public void checkUpdate() {
        Comment comment = new Comment( "Super");
        commentRepository.save(comment);

        String newComment = "newComment";
        comment.setComment(newComment);
        commentRepository.save(comment);

        Comment byId = commentRepository.findById(comment.getId()).get();

        assertTrue(byId.getComment().equals(newComment));
    }

    @Test
    public void checkDelete() {
        Comment comment1 = new Comment( "Super1");
        commentRepository.save(comment1);
        Comment comment2 = new Comment( "Super2");
        commentRepository.save(comment2);

        commentRepository.deleteById(comment1.getId());

        List<Comment> all = Lists.newArrayList(commentRepository.findAll());

        assertTrue(!all.contains(comment1) && all.contains(comment2) );
    }

}
