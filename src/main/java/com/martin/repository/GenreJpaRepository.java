package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class GenreJpaRepository implements GenreRepository {

    //Todo почему dao превратились в repository?

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void insert(Genre genre) {
        checkInsert(genre);

        em.persist(genre);
    }
    private void checkInsert(Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32)
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(g) from Genre g");
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        checkGetAll(page, amountByOnePage);

        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }
    private void checkGetAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <=0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");
    }

    @Override
    public Genre findById(long id) {
        checkFindById(id);

        return em.find(Genre.class, id);
    }
    private void checkFindById(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }

    @Override
    public List<Genre> find(Genre genre) {
        checkFind(genre);

        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.title= :title", Genre.class);
        query.setParameter("title", genre.getTitle());
        return query.getResultList();
    }
    void checkFind(Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");
    }

    @Override
    public List<Book> getBooks(long id) {
        Genre genre = em.find(Genre.class, id);
        System.out.println(genre);
        return genre.getBooks();
    }

    @Transactional
    @Override
    public int update(long id, Genre genre) {
        checkUpdate(genre);
        checkUpdate(id);

        Query query =  em.createQuery("update Genre g set g.title = :title where g.id = :id");
        query.setParameter("title", genre.getTitle());
        query.setParameter("id", id);

        return query.executeUpdate();
    }
    private void checkUpdate(Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");

    }
    private void checkUpdate(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }


    @Transactional
    @Override
    public void delete(long id) {
        checkDelete(id);

        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    private void checkDelete(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }
}
