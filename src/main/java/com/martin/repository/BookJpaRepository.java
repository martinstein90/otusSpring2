package com.martin.repository;

import com.martin.caching.CachableFindById;
import com.martin.caching.CachableGetAll;
import com.martin.domain.Book;
import com.martin.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static com.martin.repository.CheckHelper.*;

@SuppressWarnings("JpaQlInspection")
@Repository
public class BookJpaRepository implements  BookRepository{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Book insert(Book book) {
        checkInsert(book);

        em.persist(book);
        return book;
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(b) from Book b");
        return (Long)query.getSingleResult();
    }

    @CachableGetAll
    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        checkGetAll(page, amountByOnePage);

        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @CachableFindById
    @Override
    public Book findById(long id) {
        checkFindById(id);
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> find(Book book) {
        checkFind(book);

        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title= :title", Book.class);
        query.setParameter("title", book.getTitle());
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Comment> getComments(long id) {
        return new ArrayList<>(em.find(Book.class, id).getComments());
    }

    @Override
    public Book update(long id, Book book) {
        checkUpdate(book);
        checkUpdate(id);

        Book byId = findById(id);
        if(book.getTitle() != null)
            byId.setTitle(book.getTitle());
        if(book.getAuthor() != null)
            if(book.getAuthor().getId() !=0)
                byId.setAuthor(book.getAuthor());
        if(book.getGenre() != null)
            if(book.getGenre().getId() != 0)
                byId.setGenre(book.getGenre());
        return book;
    }

    @Override
    public void delete(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
