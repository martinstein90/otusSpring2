package com.martin.caching;

import com.martin.dao.LibraryJDBCdao;
import com.martin.domain.Author;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;


@Aspect
@Component
public class AuthorCache {

    @AfterReturning(pointcut = "execution(public java.util.List<com.martin.domain.Author> com.martin.dao.LibraryJDBCdao.*(..))", returning = "res")
    public void addAuthorsToCache(JoinPoint point, Object res) throws Throwable {
        if (res instanceof List) {
            List authors = (List) res;
            for (Object author : authors) {
                if (author instanceof Author) {
                    if (!LibraryJDBCdao.cacheAuthor.containsKey(((Author) author).getId())) {
                        System.out.printf("Объекта %s нет в кеше", author);
                        LibraryJDBCdao.cacheAuthor.put(((Author) author).getId(), (Author) author);
                        System.out.printf(" , добавлен в кеш\n", author);
                    } else
                        System.out.printf("Объект %s есть в кеше\n", author);
                }
            }
        }
    }


    @Around("execution(public com.martin.domain.Author com.martin.dao.LibraryJDBCdao.findAuthorById(int))")
    public Object findAuthorCacheById(ProceedingJoinPoint point) throws Throwable {
        Object proceed = null;
        if((point.getArgs())[0] instanceof Integer) {
            int id = (int)(point.getArgs())[0];
            if(!LibraryJDBCdao.cacheAuthor.containsKey(id)){
                System.out.printf("Объекта c id %d нет в кеше\n", id);
                proceed = point.proceed();
                Author author = (Author)proceed;
                LibraryJDBCdao.cacheAuthor.put(author.getId(), author);
                System.out.printf("Объект %s добавлен в кеш\n", author);
            }
            else {
                System.out.printf("Объект с id %d есть в кеше\n", id);
                proceed =  LibraryJDBCdao.cacheAuthor.get(id);
            }
        }
        return proceed;
    }



}

