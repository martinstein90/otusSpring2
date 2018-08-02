package com.martin;

import com.martin.domain.Author;
import com.martin.service.LibraryService;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.sql.SQLException;


@EnableAspectJAutoProxy
@SpringBootApplication
public class Main {

    public static void main(String[] args) throws SQLException {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class);
        LibraryService service = context.getBean(LibraryService.class);
        Author authorById = null;
        try {
            authorById = service.findAuthorById(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("authorById = " + authorById);

        Console.main(args);
    }
}
