package com.martin.repository;

import com.martin.domain.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        return null;
    }

    @Override
    public Author findById(int id) {
            return em.find(Author.class, id);
    }

    @Override
    public List<Author> find(Author author) {
        return null;
    }

    @Override
    public int update(int id, Author author) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}
