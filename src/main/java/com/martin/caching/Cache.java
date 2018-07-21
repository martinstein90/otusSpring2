package com.martin.caching;

import com.martin.dao.LibraryJDBCdao;
import com.martin.domain.Author;
import com.martin.domain.Book;
import com.martin.domain.Genre;
import com.martin.domain.Storable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Aspect
@Component
public class Cache {

    @AfterReturning(pointcut = "execution(public java.util.List com.martin.dao.LibraryJDBCdao.getAll(Class, int, int))", returning = "res")
    public void addToCache(JoinPoint point, Object res) throws Throwable {
        Class cl;
        Map cache = null;

        if((point.getArgs())[0] instanceof Class) {
            cl = (Class)(point.getArgs())[0];
            if(cl == Author.class)
                cache =  LibraryJDBCdao.authorCache;
            else if(cl == Genre.class)
                cache =  LibraryJDBCdao.genreCache;
            else if(cl == Book.class)
                cache =  LibraryJDBCdao.bookCache;
        }
        else
            return;

        List<Storable> objects = (List<Storable>) res;
            for (Storable object : objects) {
                if (!cache.containsKey(object.getId())) {
                    System.out.printf("Объекта %s нет в кеше", object);
                    cache.put(object.getId(), object);
                    System.out.printf(" , добавлен в кеш\n", object);
                } else
                    System.out.printf("Объект %s есть в кеше\n", object);
            }
    }

    // как делать с T ?
    @Around("execution(public * com.martin.dao.LibraryJDBCdao.findById(Class, int))")
    public Object findInCacheById(ProceedingJoinPoint point) throws Throwable {
        if(point.getArgs().length != 2)
            return null;

        Object proceed;
        Class cl;
        Map cache = null;

        if((point.getArgs())[0] instanceof Class) {
            cl = (Class)(point.getArgs())[0];
            if(cl == Author.class)
                cache =  LibraryJDBCdao.authorCache;
            else if(cl == Genre.class)
                cache =  LibraryJDBCdao.genreCache;
            else if(cl == Book.class)
                cache =  LibraryJDBCdao.bookCache;
        }
        else
            return null;

        int id;
        if((point.getArgs())[1] instanceof Integer)
            id = (int)(point.getArgs())[1];
        else
            return null;

        if(!cache.containsKey(id)){
            System.out.printf("Объекта c id %d нет в кеше\n", id);
            proceed = point.proceed();
            Storable object = (Storable)proceed;
            cache.put(object.getId(), object);
            System.out.printf("Объект %s добавлен в кеш\n", object);
        }
        else {
            System.out.printf("Объект с id %d есть в кеше\n", id);
            proceed = cache.get(id);
        }

        return proceed;
    }



}

