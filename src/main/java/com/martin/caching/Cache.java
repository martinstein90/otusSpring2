package com.martin.caching;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.martin.helper.Ansi.*;


@Aspect
@Component
public class Cache {

    private final String WORKING_COLOR = ANSI_GREEN;
    private final String RESET_COLOR = ANSI_RESET;

    private static final Map<Integer, Author> authorCache = new HashMap<>();
    private static final Map<Integer, Genre> genreCache = new HashMap<>();
    private static final Map<Integer, Book> bookCache = new HashMap<>();

    @AfterReturning(pointcut = "execution(public java.util.List com.martin.dao.LibraryJDBCdao.getAll(Class, int, int))", returning = "res")
    public void addToCache(JoinPoint point, Object res) throws Throwable {
        Class cl;
        Map cache = null;

        if((point.getArgs())[0] instanceof Class) {
            cl = (Class)(point.getArgs())[0];
            if(cl == Author.class)
                cache = authorCache;
            else if(cl == Genre.class)
                cache = genreCache;
            else if(cl == Book.class)
                cache = bookCache;
        }
        else
            throw new IllegalArgumentException("Must be point.getArgs())[0] instanceof Class");

        if(res instanceof List) { // Todo Как проверить содержимое листа
            List<Storable> objects = (List<Storable>) res;
            for (Storable object : objects) {
                if (!cache.containsKey(object.getId())) {
                    System.out.printf(WORKING_COLOR + "Объекта %s нет в кеше" + RESET_COLOR, object);
                    cache.put(object.getId(), object);
                    System.out.printf(WORKING_COLOR + " , добавлен в кеш\n" + RESET_COLOR, object);
                }
                else
                    System.out.printf(WORKING_COLOR + "Объект %s есть в кеше\n" + RESET_COLOR, object);
            }
        }
        // Todo Что написать в else???
    }

    @Around("execution(public * com.martin.dao.LibraryJDBCdao.findById(Class, int))")
    public Object findInCacheById(ProceedingJoinPoint point) throws Throwable {
        Object proceed;
        Class cl;
        Map cache = null;

        if((point.getArgs())[0] instanceof Class) {
            cl = (Class)(point.getArgs())[0];
            if(cl == Author.class)
                cache = authorCache;
            else if(cl == Genre.class)
                cache = genreCache;
            else if(cl == Book.class)
                cache = bookCache;
        }
        else
            throw new IllegalArgumentException("Must be point.getArgs())[0] instanceof Class");

        int id;
        if((point.getArgs())[1] instanceof Integer)
            id = (int)(point.getArgs())[1];
        else
            throw new IllegalArgumentException("Must be point.getArgs())[1] instanceof Integer");

        if(!cache.containsKey(id)){
            System.out.printf(WORKING_COLOR + "Объекта %s c id %d нет в кеше\n" + RESET_COLOR, cl.getSimpleName(), id);
            proceed = point.proceed();
            Storable object = (Storable)proceed;
            cache.put(object.getId(), object);
            System.out.printf(WORKING_COLOR + "Объект %s добавлен в кеш\n" + RESET_COLOR, object);
        }
        else {
            System.out.printf(WORKING_COLOR + "Объект %s с id %d есть в кеше\n" + RESET_COLOR, cl.getSimpleName(), id);
            proceed = cache.get(id);
        }
        return proceed;
    }

    public <T extends Storable> T getFromCach(Class<T> cl, int id) {
        if(cl == Author.class)
            return (T) authorCache.get(id);
        else if(cl == Genre.class)
            return (T) genreCache.get(id);
        else if(cl == Book.class)
            return (T) bookCache.get(id);
        else
            throw new IllegalAccessError("Must be cl is a Author or Genre or Book");
    }

}

