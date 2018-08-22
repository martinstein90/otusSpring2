package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import com.martin.repository.CommentRepository;
import org.bson.types.ObjectId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.martin.service.Helper.*;
import static com.martin.service.Helper.handlerException;

@Service
public class CommentKvService implements CommentService {

    private final CommentRepository commentRepository;

    public CommentKvService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment add(String com) throws Exception {
        Comment comment = new Comment(com);
        try{
            commentRepository.save(comment);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, comment.toString());
        }
        return comment;
    }

    @Override
    public long getCount() {
        return commentRepository.count();
    }

    @Override
    public List<Comment> getAll(int page, int amountByOnePage) {
        return commentRepository.findAll(PageRequest.of(page,amountByOnePage)).getContent();
    }

    @Override
    public Comment findById(ObjectId id) throws Exception {
        Comment byId = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Comment.class.getSimpleName(), id)));
        return byId;
    }

    @Override
    public List<Comment> find(String subTitle) throws Exception {
        List<Comment> comments = null;
        try {
            comments = Lists.newArrayList(commentRepository.findByCommentContaining(subTitle));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Genre.class.getSimpleName());
        }
        return comments;
    }

    @Override
    public Comment update(ObjectId id, String comm) throws Exception {
        Comment comment = commentRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Comment.class.getSimpleName(), id)));;
        if(comm!= null)
            comment.setComment(comm);
        try {
            commentRepository.save(comment);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }
        return comment;
    }

    @Override
    public void delete(ObjectId id) throws Exception {
        try {
            commentRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Comment.class.getSimpleName());
        }
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}
