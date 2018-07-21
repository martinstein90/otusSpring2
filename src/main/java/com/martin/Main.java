package com.martin;

import com.martin.dao.LibraryDao;
import com.martin.dao.LibraryJDBCdao;
import com.martin.domain.Author;
import com.martin.domain.Genre;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
        LibraryDao dao = context.getBean(LibraryDao.class);


        getAllGenres(dao);
        finGenreById(dao);

        System.out.println("Author:");
        System.out.println(LibraryJDBCdao.authorCache);
        System.out.println("Genre:");
        System.out.println(LibraryJDBCdao.genreCache);
        System.out.println("Book:");
        System.out.println(LibraryJDBCdao.bookCache);
    }

    public static void test1(LibraryDao dao){
        //        addAuthor(dao);
//        addGenre(dao);

//        getCountAuthor(dao);
//        getCountGenre(dao);


//        getAllAuthors(dao);
//        getAllGenres(dao);


//        findAuthor(dao);
//        findGenre(dao);



//        finAuthorById(dao);
//        finGenreById(dao);

//        updateAuthor(dao);
//        updateGenre(dao);



        deleteAuthor(dao);
        deleteGenre(dao);
    }


    public static void addAuthor(LibraryDao dao) {
        int res = 0;
        Author author = new Author("Константин", "Сидоров1");
        try{
            dao.insert(author);
            res = 1;
        }
        catch(DataAccessException exception) {
            System.out.println("Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage());
        }
        if(res == 1) {
            System.out.printf("%s обавлен в бд!\n", author);

    }

    public static void addGenre(LibraryDao dao) {
        int res = 0;
        Genre genre = new Genre("Ужасы");
        try{
            dao.insert(genre);
            res = 1;
        }
        catch(DataAccessException exception) {
            System.out.println("Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage());
        }
        if(res == 1) {
            System.out.printf("%s обавлен в бд!\n", genre);
        }
    }

    public static void getCountAuthor(LibraryDao dao) {
        System.out.println("count authors: " + dao.getCount(Author.class));
    }

    public static void getCountGenre(LibraryDao dao) {
        System.out.println("count genres: " + dao.getCount(Genre.class));
    }

    public static void getAllAuthors(LibraryDao dao) {
        List<Author> authors = dao.getAll(Author.class, 1, 5);
        System.out.println("Authors 1");
        for (Author a:authors ) {
            System.out.println(a);
        }
        authors = dao.getAll(Author.class, 2, 5);
        System.out.println("Authors 2");
        for (Author a:authors ) {
            System.out.println(a);
        }
        authors = dao.getAll(Author.class, 3, 5);
        System.out.println("Authors 3");
        for (Author a:authors ) {
            System.out.println(a);
        }
    }

    public static void getAllGenres(LibraryDao dao) {
        List<Genre> genres = dao.getAll(Genre.class, 1, 5);
        System.out.println("Authors 1");
        for (Genre a : genres) {
            System.out.println(a);
        }
        genres = dao.getAll(Genre.class, 1, 5);
        System.out.println("Authors 1");
        for (Genre a : genres) {
            System.out.println(a);
        }
    }

    public static void findAuthor(LibraryDao dao) {
        List<Author> authors = dao.find(new Author("Федор", null));
        for (Author a:authors ) {
            System.out.println(a);
        }
    }

    public static void findGenre(LibraryDao dao) {
        List<Genre> genres = dao.find(new Genre("Ужасы"));
        for (Genre a: genres ) {
            System.out.println(a);
        }
    }

    public static void finAuthorById(LibraryDao dao) {
        System.out.println("Author by id = 7: " + dao.findById(Author.class, 7));
    }

    public static void finGenreById(LibraryDao dao) {
        System.out.println("Genre by id = 10: " + dao.findById(Genre.class, 10));
    }

    public static void updateAuthor(LibraryDao dao) {
        dao.update(23, new Author("Кон", "Пет!!!!!"));
    }

    public static void updateGenre(LibraryDao dao) {
        dao.update(7, new Genre("Ужасы!!!!"));

    }

    public static void deleteAuthor(LibraryDao dao) {
        dao.delete(Author.class, 23);
    }

    public static void deleteGenre(LibraryDao dao) {
        dao.delete(Genre.class, 7);
    }
}
