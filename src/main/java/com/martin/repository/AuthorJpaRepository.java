package com.martin.repository;

import com.martin.caching.CachableFindById;
import com.martin.caching.CachableGetAll;
import com.martin.domain.Author;
import com.martin.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static com.martin.repository.CheckHelper.*;


@SuppressWarnings("JpaQlInspection") //Todo что это фигня, без которой не написать jpql?
@Repository
public class AuthorJpaRepository implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Author insert(Author author) {
        checkInsert(author);

        em.persist(author);
        return author;
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(a) from Author a");
        return (Long)query.getSingleResult();
    }

    @CachableGetAll
    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        checkGetAll(page, amountByOnePage);

        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }

    @CachableFindById
    @Override
    public Author findById(long id) {
        checkFindById(id);

        return em.find(Author.class, id);
    }

    @Override
    public List<Author> find(Author author) {
        checkFind(author);

        TypedQuery<Author> query = em.createQuery(createJpqlForFind(author), Author.class);
        if(author.getFirstname() != null)
            query.setParameter("firstname", author.getFirstname());
        if(author.getLastname() != null)
            query.setParameter("lastname", author.getLastname());

        return query.getResultList();
    }
    private String createJpqlForFind(Author author){
        StringBuilder jpql = new StringBuilder("select a from Author a where ");
        if(author.getFirstname() != null)
            jpql.append("a.firstname= :firstname ");
        if(author.getLastname() != null) {
            if(author.getFirstname() != null)
                jpql.append("and ");
            jpql.append("a.lastname= :lastname");
        }
        //Todo Вы говорили что все правильно,но очень опасно из-за sql иньекции
        //Такие штуки не нужно реализовывать, или нужно но как-то по-другому. Сделал тест на sql иньекцию
        return jpql.toString();
    }

    @Transactional
    @Override
    public List<Book> getBooks(long id) {
        return new ArrayList<>(em.find(Author.class, id).getBooks());
    }

    @Override
    @Transactional
    public Author update(long id, Author author) {
        checkUpdate(author);
        checkUpdate(id);

        Author byId = findById(id);
        if(author.getFirstname()!= null)
            byId.setFirstname(author.getFirstname());
        if(author.getLastname() != null)
            byId.setLastname(author.getLastname());
        em.merge(byId);
        return byId;
    }

    @Transactional
    @Override
    public void delete(long id) {
        checkDelete(id);

        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
