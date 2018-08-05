package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class BookJpaRepository implements  BookRepository{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void insert(Book book) {
        checkInsert(book);

        em.persist(book);
    }
    private void checkInsert(Book book) {
        if(book.getTitle() == null )
            throw new IllegalArgumentException("Must be title != null by book");

        if(book.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by book");
    }


    @Override
    public long getCount() {
        Query query = em.createQuery("select count(b) from Book b");
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Book> getAll(int page, int amountByOnePage) {
        checkGetAll(page, amountByOnePage);

        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }
    private void checkGetAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <=0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> find(Book book) {
        return null;
    }

    @Override
    public int update(long id, Book book) {
        return 0;
    }

    @Override
    public void delete(long id) {

    }
}
