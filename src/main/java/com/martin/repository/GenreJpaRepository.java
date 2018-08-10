package com.martin.repository;

import com.martin.domain.Book;
import com.martin.domain.Genre;
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

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(g) from Genre g");
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Genre> getAll(int page, int amountByOnePage) {
        checkGetAll(page, amountByOnePage);

        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @Override
    public Genre findById(long id) {
        checkFindById(id);

        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> find(Genre genre) {
        checkFind(genre);

        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.title= :title", Genre.class);
        query.setParameter("title", genre.getTitle());
        return query.getResultList();
    }

    @Transactional
    @Override
    public List<Book> getBooks(long id) {
        return new ArrayList<>(em.find(Genre.class, id).getBooks());
    }

    @Transactional
    @Override
    public void update(long id, Genre genre) {
        checkUpdate(genre);
        checkUpdate(id);

        Genre byId = findById(id);
        if(genre.getTitle() != null)
            byId.setTitle(genre.getTitle());
       em.merge(byId);
    }

    @Transactional
    @Override
    public void delete(long id) {
        checkDelete(id);

        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
