package com.martin.service;

import com.martin.domain.Comment;
import com.martin.repository.CommentRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public class CommentJpaService implements CommentService{

    private final CommentRepository commentRepository;
    private final BookService bookService;

    private final String ERROR_STRING = "Операция с объектом %s не выполнена";
    private final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    private final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";

    public CommentJpaService(CommentRepository commentRepository,
                             BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }


    @Override
    public void add(String comment, int bookId) throws Exception {
        Comment com = new Comment(comment, bookService.findById(bookId));
        try {
            commentRepository.insert(com);
        }
        catch (DataIntegrityViolationException exception) {
            String causeMsg= exception.getCause().getCause().getMessage();
            if(causeMsg.contains("Нарушение уникального индекса или первичного ключ"))
                throw new Exception(String.format(DUPLICATE_ERROR_STRING, com));
            else
                throw new Exception(String.format(ERROR_STRING, com));
        }
    }

    @Override
    public long getCount() {
        return commentRepository.getCount();

    }

    @Override
    public List<Comment> getAll(int page, int amountByOnePage) {
        return commentRepository.getAll(page, amountByOnePage);
    }

    @Override
    public Comment findById(long id) throws Exception {
        Comment com;
        try {
            com = commentRepository.findById(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new Exception(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
        return com;
    }

    @Override
    public Comment update(long id, String comment, int bookId) throws Exception {
        Comment com = new Comment(comment, bookService.findById(bookId));
        try {
            com = commentRepository.update(id, com);
        }
        catch (DataIntegrityViolationException exception) {
            System.out.println(exception.getMessage());
            throw new Exception(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
        return com;
    }

    @Override
    public void delete(long id) throws Exception {
        try {
            commentRepository.delete(id);
        }
        catch (DataIntegrityViolationException exception) {
            System.out.println(exception.getMessage());
            throw new Exception(String.format(ERROR_STRING, Comment.class.getSimpleName()));
        }
    }
}
