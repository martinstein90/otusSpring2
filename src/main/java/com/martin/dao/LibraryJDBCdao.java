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
import java.sql.Types;
import java.util.*;

@Repository
public class LibraryJDBCdao implements LibraryDao {

    private final JdbcOperations jdbc;

    public LibraryJDBCdao(JdbcOperations jdbcOperations) {
        jdbc = jdbcOperations;
    }

    @Override
    public <T extends Storable> int insert(T object) {
        if(object instanceof Author)
            return insertAuthor((Author)object);
        else if(object instanceof Genre)
            return insertGenre((Genre)object);
        else if(object instanceof Book)
            return insertBook((Book)object);
        else
            throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");
    }
    private int insertAuthor(Author author) {
        if(author.getFirstname() == null || author.getLastname() == null)
            throw new IllegalArgumentException("Must be firstname != null and lastname != null by author");

        if(author.getFirstname().length() > 32 || author.getLastname().length() > 32 )
            throw new IllegalArgumentException("Must be firstname.length() < 32  and lastname.length() < 32  by author");

        return jdbc.update("insert authors (firstname, lastname) values (?, ?)", author.getFirstname(), author.getLastname());
    }
    private int insertGenre(Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32)
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");

        return jdbc.update("insert genres (title) values (?)", genre.getTitle());
    }
    private int insertBook(Book book) {
        if(book.getTitle() == null )
            throw new IllegalArgumentException("Must be title != null by book");

        if(book.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by book");

        StringBuilder sql = new StringBuilder();
        List<Object> args = new ArrayList<>(10);
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<Integer> types = new ArrayList<>();

        sql.append("insert books ");
        fields.append("(title ");
        values.append("values (? ");
        args.add(book.getTitle());
        types.add(Types.NVARCHAR);

        if(book.getAuthor() != null) {
            if(book.getAuthor().getId() != 0) {
                fields.append(", author_id ");
                values.append(", ? ");
                args.add(book.getAuthor().getId());
                types.add(Types.INTEGER);
            }
        }

        if(book.getGenre() != null) {
            if(book.getGenre().getId() != 0) {
                fields.append(", genre_id ");
                values.append(", ? ");
                args.add(book.getGenre().getId());
                types.add(Types.INTEGER);
            }
        }

        fields.append(") ");
        values.append(") ");

        sql.append(fields);
        sql.append(values);

        return jdbc.update(sql.toString(), args.toArray(), types.toArray(new Integer[types.size()]));
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
    private List<Author>    getAllAuthors   (int page, int authorsAmountByOnePage) {
        if(authorsAmountByOnePage <= 0 || page <=0)
            throw new IllegalArgumentException("Must be authorsAmountByOnePage > 0 and page > 0");

        return jdbc.query(  "select * from authors order by id limit ?, ?",
                                new Object[] {
                                        authorsAmountByOnePage * (page-1),
                                        authorsAmountByOnePage },
                                new AuthorMapper());
    }
    private List<Genre>     getAllGenres    (int page, int genresAmountByOnePage) {
        if(genresAmountByOnePage <= 0 || page <= 0)
            throw new IllegalArgumentException("Must be authorsAmountByOnePage > 0 and page > 0");

        return jdbc.query(  "select * from genres order by id limit ?, ?",
                new Object[] {
                        genresAmountByOnePage * (page-1),
                        genresAmountByOnePage },
                new GenreMapper());
    }
    private List<Book>      getAllBooks     (int page, int booksAmountByOnePage) {
        if(booksAmountByOnePage <= 0 || page <= 0)
            throw new IllegalArgumentException("Must be authorsAmountByOnePage > 0 and page > 0");

        return jdbc.query(  "select * from books order by id limit ?, ?",
                new Object[] {
                        booksAmountByOnePage * (page-1),
                        booksAmountByOnePage },
                new BookMapper(this));
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
    private Author  findAuthorById  (int id) {
        return jdbc.queryForObject("select * from authors where id = ?",
                new Object[] {id},
                new AuthorMapper());
    }
    private Genre   findGenreById   (int id) {
        return jdbc.queryForObject("select * from genres where id = ?",
                new Object[] {id},
                new GenreMapper());
    }
    private Book    findBookById    (int id) {
        return jdbc.queryForObject("select * from books where id = ?",
                new Object[] {id},
                new BookMapper(this));
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
    private List<Author>    findAuthor  (Author author) {
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
        List<Object> args = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from authors where ");
        if(author.getFirstname() != null) {
            sql.append("firstname = ? ");
            args.add(author.getFirstname());
        }
        if(author.getLastname() != null) {
            if(!args.isEmpty())
                sql.append("and ");
            sql.append("lastname = ? ");
            args.add(author.getLastname());
        }

        return jdbc.query(sql.toString(), args.toArray(), new AuthorMapper());
    }
    private List<Genre>     findGenre   (Genre genre) {
        if(genre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(genre.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");

        List<Object> args = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from genres where ");
        sql.append("title = ? ");
        args.add(genre.getTitle());

        return jdbc.query(sql.toString(), args.toArray(), new GenreMapper());
    }
    private List<Book>      findBook    (Book book) {
        if(book.getTitle() == null )
            throw new IllegalArgumentException("Must be title != null by book");

        if(book.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by book");

        List<Object> args = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from books where ");
        if(book.getTitle() != null) {
            sql.append("title = ? ");
            args.add(book.getTitle());
        }
        if(book.getAuthor() != null) {
            if(book.getAuthor().getId() != 0) {
                if (!args.isEmpty())
                    sql.append("and ");
                sql.append("author_id = ? ");
                args.add(book.getAuthor().getId());
            }
        }
        if(book.getGenre() != null) {
            if(book.getGenre().getId() != 0) {
                if (!args.isEmpty())
                    sql.append("and ");
                sql.append("genre_id = ? ");
                args.add(book.getGenre().getId());
            }
        }


        return jdbc.query(sql.toString(), args.toArray(), new BookMapper(this));
    }

    @Override
    public <T extends Storable> int update(int id, T object) {
        if(object instanceof Author)
            return updateAuthor(id, (Author)object);
        else if(object instanceof Genre)
            return updateGenre(id, (Genre)object);
        else if(object instanceof Book)
            return updateBook(id, (Book)object);
        else
            throw new IllegalArgumentException("Must be object instanceof Author or Genre or Book");
    }
    private int updateAuthor   (int oldAuthorId, Author newAuthor) {
        if(newAuthor.getFirstname() == null || newAuthor.getLastname() == null)
            throw new IllegalArgumentException("Must be fistname != null and lastname != null by author");

        if(newAuthor.getFirstname().length() > 32 || newAuthor.getLastname().length() > 32 )
            throw new IllegalArgumentException("Must be firstname.length() < 32  and lastname.length() < 32  by author");

        return jdbc.update("update authors set firstname = ?,  lastname = ? where id = ?",
                    newAuthor.getFirstname(),
                    newAuthor.getLastname(),
                    oldAuthorId);
    }
    private int updateGenre    (int oldGenreId, Genre newGenre) {
        if(newGenre.getTitle() == null)
            throw new IllegalArgumentException("Must be title != null by genre");

        if(newGenre.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by genre");

        return jdbc.update("update genres set title = ? where id = ?",
                    newGenre.getTitle(),
                    oldGenreId);
    }
    private int updateBook     (int oldBookId, Book newBook) {
        if(newBook.getTitle() == null || newBook.getAuthor() == null || newBook.getGenre() == null)
            throw new IllegalArgumentException("Must be title != null and author != null and genre != null by book");

        if(newBook.getTitle().length() > 32 )
            throw new IllegalArgumentException("Must be title.length() < 32 by book");

        if(newBook.getAuthor().getId() == 0 || newBook.getGenre().getId() == 0)
            throw new IllegalArgumentException("Must be newBook.getAuthor().getId() != 0 and newBook.getGenre().getId() != 0 by book");

        return jdbc.update("update books set title = ?, author_id = ?, genre_id = ? where id = ?",
                    newBook.getTitle(),
                    newBook.getAuthor().getId(),
                    newBook.getGenre().getId(),
                    oldBookId);
    }

    @Override
    public <T extends Storable> int delete(Class<T> cl, int id) {
        if(cl == Author.class)
            return deleteAuthor(id);
        else if(cl == Genre.class)
            return deleteGenre(id);
        else if(cl == Book.class)
            return deleteBook(id);
        else
            throw new IllegalArgumentException("Must be cl is a Author.class or Genre.class or Book.class");
    }
    private int deleteAuthor   (int deletedAuthorId) {
        return jdbc.update("delete from authors where id = ?", deletedAuthorId);
    }
    private int deleteGenre    (int deletedGenreId) {
        return jdbc.update("delete from genres where id = ?", deletedGenreId);
    }
    private int deleteBook     (int deletedBookId) {
        return jdbc.update("delete from books where id = ?", deletedBookId);
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
            Genre genre = new Genre(title);
            genre.setId(id);
            return genre;
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        private final LibraryJDBCdao dao;

        public BookMapper(LibraryJDBCdao dao) {
            this.dao = dao;
        }

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("title");
            int author_id = resultSet.getInt("author_id");
            int genre_id = resultSet.getInt("genre_id");

            Author author = dao.findById(Author.class, author_id);
            Genre genre = dao.findById(Genre.class, genre_id);
            Book book = new Book(name, author, genre);
            book.setId(id);

            return book;
        }
    }
}
