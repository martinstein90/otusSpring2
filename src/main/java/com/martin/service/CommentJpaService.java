package com.martin.service;

import com.google.common.collect.Lists;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import com.martin.domain.Genre;
import com.martin.repository.CommentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.martin.service.Helper.*;
import static com.martin.service.Helper.handlerException;

public class CommentJpaService implements CommentService
{

    private final CommentRepository commentRepository;
    private final BookService bookService;

    public CommentJpaService(CommentRepository commentRepository,
                             BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }


    @Override
    public Comment add(String com, int bookId) throws Exception {
        Comment comment = new Comment(com, bookService.findById(bookId));
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
    public Comment findById(long id) throws Exception {
        Optional<Comment> byId = commentRepository.findById(id);
        if(!byId.isPresent())
            throw new Exception(String.format(EMPTY_RESULT_BY_ID_ERROR_STRING, Comment.class.getSimpleName(), id));
        return byId.get();
    }

    @Override
    public List<Comment> find(String subTitle) throws Exception {
        List<Comment> comments = null;
        try {
            comments = Lists.newArrayList(commentRepository.findByCommentLike("%" + subTitle + "%" ));
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Genre.class.getSimpleName());
        }
        return comments;
    }

    @Override
    public Comment update(long id, String comm, int bookId) throws Exception {
        Comment comment = commentRepository.findById(id).get();
        if(comm!= null)
            comment.setComment(comm);
        if(bookId != 0)
            comment.setBook(bookService.findById(bookId));
        try {
            commentRepository.save(comment);
        }
        catch (DataIntegrityViolationException exception) {
            handlerException(exception, Book.class.getSimpleName());
        }

        return comment;
    }

    @Override
    public void delete(long id) throws Exception {
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
