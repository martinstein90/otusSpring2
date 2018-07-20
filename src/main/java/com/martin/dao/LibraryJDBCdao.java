package com.martin.dao;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class LibraryJDBCdao implements LibraryDao {

    public static final Map<Integer, Author> cacheAuthor = new HashMap<>();

    private final JdbcOperations jdbc;

    public LibraryJDBCdao(JdbcOperations jdbcOperations) {
        jdbc = jdbcOperations;
    }

    @Override
    public void insertAuthor(Author author) {
        jdbc.update("insert authors (firstname, lastname) values (?, ?)", author.getFirstname(), author.getLastname());
    }

    @Override
    public int getAuthorCount() {
        return jdbc .queryForObject("select count(*) from authors;", Integer.class);
    }

    @Override
    public List<Author> getAllAuthors(int page, int authorsAmountByOnePage) {
        if(authorsAmountByOnePage <= 0 || page<=0)
            throw new IllegalArgumentException("Must be authorsAmountByOnePage > 0 and page > 0");

        return jdbc.query(  "select * from authors order by id limit ?, ?",
                                new Object[] {
                                        authorsAmountByOnePage * (page-1),
                                        authorsAmountByOnePage },
                                new AuthorMapper());
    }

    @Override
    public Author findAuthorById(int id) {
        return jdbc.queryForObject("select * from authors where id = ?",
                                        new Object[] {id},
                                        new AuthorMapper());
    }

    @Override
    public List<Author> findAuthorByNames(String firstname, String lastname) {
        List<Object> args = new ArrayList<>(2);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from authors where ");
        if(firstname != null) {
            sql.append("firstname = ? ");
            args.add(firstname);
        }
        if(lastname != null) {
            sql.append("and lastname = ? ");
            args.add(lastname);
        }
        return jdbc.query(sql.toString(), args.toArray(), new AuthorMapper());
    }

    @Override
    public void updateAuthor(int idOldAuthor, Author newAuthor) {
        jdbc.update("update authors set firstname =?,  lastname =? where id=?",
                newAuthor.getFirstname(),
                newAuthor.getLastname(),
                idOldAuthor);
    }

    @Override
    public void deleteAuthor(int idDeletedAuthor) {
        jdbc.update("delete from authors where id = ?", idDeletedAuthor);
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            int id              = resultSet.getInt("id");
            String firstname    = resultSet.getString("firstname");
            String lastname     = resultSet.getString("lastname");
            Author author = new Author(firstname, lastname);
            author.setId(id);
            return author;
        }
    }






//    private static class GenreMapper implements RowMapper<Genre> {
//
//        @Override
//        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
//            int id = resultSet.getInt("id");
//            String title = resultSet.getString("title");
//            return new Genre(id, title);
//        }
//    }
//
//    private static class BookMapper implements RowMapper<Book> {
//
//        @Override
//        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
//            int id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            return null;
//        }
//    }
}
