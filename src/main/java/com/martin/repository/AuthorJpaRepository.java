package com.martin.repository;

import com.martin.domain.Author;
import com.martin.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;
import java.util.Set;

@SuppressWarnings("JpaQlInspection") //Todo чьл то это фигня, без которой не написать jpql?
@Repository
public class AuthorJpaRepository implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void insert(Author author) {
        checkInsert(author);

        em.persist(author);
    }
    private void checkInsert(Author author) {
        if(author.getFirstname() == null || author.getLastname() == null)
            throw new IllegalArgumentException("Must be firstname != null and lastname != null by author");

        if(author.getFirstname().length() > 32 || author.getLastname().length() > 32 )
            throw new IllegalArgumentException("Must be firstname.length() < 32  and lastname.length() < 32  by author");
    }

    @Override
    public long getCount() {
        Query query = em.createQuery("select count(a) from Author a");
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Author> getAll(int page, int amountByOnePage) {
        checkGetAll(page, amountByOnePage);

        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        query.setFirstResult((--page) * amountByOnePage);
        query.setMaxResults(amountByOnePage);
        return query.getResultList();
    }
    private void checkGetAll(int page, int amountByOnePage) {
        if(amountByOnePage <= 0 || page <=0)
            throw new IllegalArgumentException("Must be amountByOnePage > 0 and page > 0");
    }

    @Override
    public Author findById(long id) {
        checkFindById(id);

        return em.find(Author.class, id);
    }
    private void checkFindById(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
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
    private void checkFind(Author author) {
        if(author.getFirstname() == null && author.getLastname() == null)
            throw new IllegalArgumentException("Must be firstname != null or lastname != null by author");

        if(author.getFirstname() != null) {
            if (author.getFirstname().length() > 32)
                throw new IllegalArgumentException("Must be firstname.length() < 32  by author");
        }
        if(author.getLastname() != null) {
            if (author.getLastname().length() > 32)
                throw new IllegalArgumentException("Must be  lastname.length() < 32  by author");
        }
    }

    @Override
    public List<Book> getBooks(long id) {
        System.out.println("getBooks");
        Author author = em.find(Author.class, id);
        System.out.println(author);
        System.out.println("==========================");
        System.out.println(author.getBooks());
        System.out.println("==========================");
        return null;
    }

    @Override
    @Transactional
    public int update(long id, Author author) {
        checkUpdate(author);
        checkUpdate(id);

        Query query =  em.createQuery("update Author a set a.firstname = :firstname, a.lastname = :lastname where a.id = :id");
        query.setParameter("firstname", author.getFirstname());
        query.setParameter("lastname", author.getLastname());
        query.setParameter("id", id);

        return query.executeUpdate();
    }
    private void checkUpdate(Author author) {
        if(author.getFirstname() == null || author.getLastname() == null)
            throw new IllegalArgumentException("Must be fistname != null and lastname != null by author");

        if(author.getFirstname().length() > 32 || author.getLastname().length() > 32 )
            throw new IllegalArgumentException("Must be firstname.length() < 32  and lastname.length() < 32  by author");

    }
    private void checkUpdate(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }

    @Transactional
    @Override
    public void delete(long id) {
        checkDelete(id);

        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate(); //Todo пока не зная как удалить автора с книгами
    }
    private void checkDelete(long id) {
        if(id <= 0)
            throw new IllegalArgumentException("Must be id > 0");
    }
}
