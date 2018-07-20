package com.martin;

import com.martin.dao.LibraryDao;
import com.martin.domain.Author;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DataAccessException;

import java.util.List;

@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
        LibraryDao dao = context.getBean(LibraryDao.class);

//        int res = 0;
//        Author author = new Author("Константин", "Петров");
//        try{
//            dao.insertAuthor(author);
//            res = 1;
//        }
//        catch(DataAccessException exception) {
//            System.out.println("Exception: " + exception.getClass().getSimpleName() + " Msg: " + exception.getMessage());
//        }
//        if(res == 1) {
//            System.out.printf("%s обавлен в бд!\n", author);
//        }


//        System.out.println("count: " + dao.getAuthorCount());



//        List<Author> authors = dao.getAllAuthors(1, 5);
//        System.out.println("Page 1");
//        for (Author a:authors ) {
//            System.out.println(a);
//        }



        List<Author> authors = dao.findAuthorByNames("Федор", null);
        for (Author a:authors ) {
            System.out.println(a);
        }







//       System.out.println("Author by id = 7: " + dao.findAuthorById(7));



//         dao.updateAuthor(19, new Author("Кон", "Пет"));

//      dao.deleteAuthor(19);
    }
}
