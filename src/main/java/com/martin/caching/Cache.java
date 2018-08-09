package com.martin.caching;

import com.martin.domain.*;
import com.martin.repository.AuthorRepository;
import com.martin.repository.BookRepository;
import com.martin.repository.CommentRepository;
import com.martin.repository.GenreRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.martin.helper.Ansi.*;
import static java.lang.String.format;


@Aspect
@Component
public class Cache {

    private final String WORKING_COLOR = ANSI_GREEN;
    private final String RESET_COLOR = ANSI_RESET;

    private static final Map<Integer, Author> authorCache = new HashMap<>();
    private static final Map<Integer, Genre> genreCache = new HashMap<>();
    private static final Map<Integer, Book> bookCache = new HashMap<>();
    private static final Map<Integer, Comment> commentCache = new HashMap<>();


    @AfterReturning(pointcut = "@annotation(com.martin.caching.CachableGetAll)", returning = "res")
    public void addToCache(JoinPoint point, Object res) {
        Map cache = null;
        Class<?> cl = point.getTarget().getClass();
        if(cl == AuthorRepository.class)
            cache = authorCache;
        else if(cl == GenreRepository.class)
            cache = genreCache;
        else if(cl == BookRepository.class)
            cache = bookCache;
        else if(cl == CommentRepository.class)
            cache = commentCache;

        if(res instanceof List) {  //Todo Как проверить содержимое листа????
            List<Storable> objects = (List) res;
            for (Storable object : objects) {
                if (!cache.containsKey(object.getId())) {
                    showMessage(format("Объекта %s нет в кеше", object));
                    cache.put(object.getId(), object);
                    showMessage(format(" , добавлен в кеш\n", object));
                }
                else
                    showMessage(format("Объект %s есть в кеше\n", object));
            }
        }
    }



    @Around("@annotation(com.martin.caching.CachableFindById)")
    public Object findInCacheById(ProceedingJoinPoint point) throws Throwable {  //Todo ПОлучается для каждой CRUD писать свою аннотацию CachableFindGetAll,  CachableFindById,///
                                                                                // Может такое получится что поставим аннотация CachableGetAll
                                //а на update забудем и будем их кеша брать не обновленные данные. Как проверить? Рефлексией анализировать все классы пакета репозиторий?
        Object proceed;
        Map cache = null;
        Class<?> cl = point.getTarget().getClass();
            if(cl == AuthorRepository.class)
                cache = authorCache;
            else if(cl == GenreRepository.class)
                cache = genreCache;
            else if(cl == BookRepository.class)
                cache = bookCache;
            else if(cl == CommentRepository.class)
                cache = commentCache;

        int id;
        if((point.getArgs())[0] instanceof Integer)
            id = (int)(point.getArgs())[0];
        else
            throw new IllegalArgumentException("Must be point.getArgs())[1] instanceof Integer");

        if(!cache.containsKey(id)){
            showMessage(format("Объекта %s c id %d нет в кеше\n", cl.getSimpleName(), id));
            proceed = point.proceed();
            Storable object = (Storable)proceed;
            cache.put(object.getId(), object);
            showMessage(format("Объект %s добавлен в кеш\n", object));
        }
        else {
            showMessage(format("Объект %s с id %d есть в кеше\n", cl.getSimpleName(), id));
            proceed = cache.get(id);
        }
        return proceed;
    }


    private void showMessage(String str) {
        System.out.println(WORKING_COLOR + str + RESET_COLOR);
    }
}

