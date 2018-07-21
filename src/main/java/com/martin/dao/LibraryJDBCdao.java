package com.martin.dao;

import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.domain.Storable;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class LibraryJDBCdao implements LibraryDao {

    public static final Map<Integer, Author> authorCache = new HashMap<>();
    public static final Map<Integer, Genre> genreCache = new HashMap<>();
    public static final Map<Integer, Book> bookCache = new HashMap<>();

    private final JdbcOperations jdbc;

    public LibraryJDBCdao(JdbcOperations jdbcOperations) {
        jdbc = jdbcOperations;
    }

    @Override
    public <T extends Storable> void insert(T object) {
        if(object instanceof Author)
            insertAuthor((Author)object);
        else if(object instanceof Genre)
            insertGenre((Genre)object);
        else if(object instanceof Book)
            insertBook((Book)object);
        else
            throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");
    }
    private void insertGenre(Genre genre) {
        jdbc.update("insert genres (title) values (?)", genre.getTitle());
    }
    private void insertAuthor(Author author) {
        jdbc.update("insert authors (firstname, lastname) values (?, ?)", author.getFirstname(), author.getLastname());
    }
    private void insertBook(Book book) {
    }

    @Override
    public <T extends Storable> int getCount(Class<T> cl) {
        if(cl == Author.class)
            return getAuthorsCount();
        else if(cl == Genre.class)
            return getGenresCount();
        else if(cl == Book.class)
            return getBooksCount();
        else
            throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");
    }
    private int getAuthorsCount() {
        return jdbc.queryForObject("select count(*) from authors;", Integer.class);
    }
    private int getGenresCount() {
        return jdbc.queryForObject("select count(*) from genres;", Integer.class);
    }
    private int getBooksCount() {
        return jdbc.queryForObject("select count(*) from books;", Integer.class);
    }

    @Override
    public <T extends Storable> List<T> getAll(Class<T> cl, int page, int amountByOnePage) {
        if(cl == Author.class)
            return (List<T>)getAllAuthors(page, amountByOnePage);
        else if(cl == Genre.class)
            return (List<T>)getAllGenres(page, amountByOnePage);
        else if(cl == Book.class)
            return (List<T>)getAllBooks(page, amountByOnePage);
        else
            throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");
    }
    private List<Author> getAllAuthors(int page, int authorsAmountByOnePage) {
        if(authorsAmountByOnePage <= 0 || page<=0)
            throw new IllegalArgumentException("Must be authorsAmountByOnePage > 0 and page > 0");

        return jdbc.query(  "select * from authors order by id limit ?, ?",
                                new Object[] {
                                        authorsAmountByOnePage * (page-1),
                                        authorsAmountByOnePage },
                                new AuthorMapper());
    }
    private List<Genre> getAllGenres(int page, int genresAmountByOnePage) {
        if(genresAmountByOnePage <= 0 || page<=0)
            throw new IllegalArgumentException("Must be authorsAmountByOnePage > 0 and page > 0");

        return jdbc.query(  "select * from genres order by id limit ?, ?",
                new Object[] {
                        genresAmountByOnePage * (page-1),
                        genresAmountByOnePage },
                new GenreMapper());
    }
    private List<Book> getAllBooks(int page, int booksAmountByOnePage) {
        if(booksAmountByOnePage <= 0 || page<=0)
            throw new IllegalArgumentException("Must be authorsAmountByOnePage > 0 and page > 0");

        return jdbc.query(  "select * from books order by id limit ?, ?",
                new Object[] {
                        booksAmountByOnePage * (page-1),
                        booksAmountByOnePage },
                new BookMapper());
    }

    @Override
    public <T extends Storable> T findById(Class<T> cl, int id) {
        if(cl == Author.class)
            return (T)findAuthorById(id);
        else if(cl == Genre.class)
            return (T)findGenreById(id);
        else if(cl == Book.class)
            return (T)findBookById(id);
        else
            throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");
    }
    private Author findAuthorById(int id) {
        return jdbc.queryForObject("select * from authors where id = ?",
                new Object[] {id},
                new AuthorMapper());
    }
    private Genre findGenreById(int id) {
        return jdbc.queryForObject("select * from genres where id = ?",
                new Object[] {id},
                new GenreMapper());
    }
    private Book findBookById(int id) {
        return jdbc.queryForObject("select * from books where id = ?",
                new Object[] {id},
                new BookMapper());
    }

    @Override
    public <T extends Storable> List<T> find(T object) {
        if(object instanceof Author)
            return (List<T>)findAuthor((Author)object);
        else if(object instanceof Genre)
            return (List<T>)findGenre((Genre)object);
        else if(object instanceof Book)
            return (List<T>)findBook((Book)object);
        else
            throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");
    }
    private List<Author> findAuthor(Author author) {
        List<Object> args = new ArrayList<>(2);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from authors where ");
        if(author.getFirstname() != null) {
            sql.append("firstname = ? ");
            args.add(author.getFirstname());
        }
        if(author.getLastname() != null) {
            sql.append("and lastname = ? ");
            args.add(author.getLastname());
        }
        return jdbc.query(sql.toString(), args.toArray(), new AuthorMapper());
    }
    private List<Genre> findGenre(Genre genre) {
        List<Object> args = new ArrayList<>(2);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from genres where ");
        if(genre.getTitle() != null) {
            sql.append("title = ? ");
            args.add(genre.getTitle());
        }

        return jdbc.query(sql.toString(), args.toArray(), new GenreMapper());
    }
    private List<Book> findBook(Book book) {
        List<Object> args = new ArrayList<>(2);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from books where ");
        if(book.getTitle() != null) {
            sql.append("title = ? ");
            args.add(book.getTitle());
        }
        return jdbc.query(sql.toString(), args.toArray(), new BookMapper());
    }

    @Override
    public <T extends Storable> void update(int id, T object) {
        if(object instanceof Author)
            updateAuthor(id, (Author)object);
        else if(object instanceof Genre)
            updateGenre(id, (Genre)object);
        else if(object instanceof Book)
            updateBook(id, (Book)object);
        else
            throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");
    }
    private void updateAuthor(int idOldAuthor, Author newAuthor) {
        jdbc.update("update authors set firstname =?,  lastname =? where id=?",
                newAuthor.getFirstname(),
                newAuthor.getLastname(),
                idOldAuthor);
    }
    private void updateGenre(int idOldGenre, Genre newGenre) {
        jdbc.update("update genres set title =?, where id=?",
                newGenre.getTitle(),
                idOldGenre);
    }
    private void updateBook(int idOldBook, Book newBook) {
        jdbc.update("update books set title =?,  lastname =? where id=?",
                newBook.getTitle(),
                idOldBook);
    }

    @Override
    public <T extends Storable> void delete(Class<T> cl, int id) {
        if(cl == Author.class)
            deleteAuthor(id);
        else if(cl == Genre.class)
            deleteGenre(id);
        else if(cl == Book.class)
            deleteBook(id);
        else
            throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");
    }
    private void deleteAuthor(int idDeletedAuthor) {
        jdbc.update("delete from authors where id = ?", idDeletedAuthor);
    }
    private void deleteGenre(int idDeletedGenre) {
        jdbc.update("delete from genres where id = ?", idDeletedGenre);
    }
    private void deleteBook(int idDeletedBook) {
        jdbc.update("delete from books where id = ?", idDeletedBook);
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

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            Genre genre =new Genre(title);
            genre.setId(id);
            return genre;
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");

            return null;
        }
    }
}
