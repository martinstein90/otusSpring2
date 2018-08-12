package com.martin.caching;

import com.martin.domain.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.martin.helper.Ansi.ANSI_BLACK;
import static com.martin.helper.Ansi.ANSI_YELLOW;
import static java.lang.String.format;


@Aspect
@Component
public class Cache {

    private static final  Map<Class<? extends Storable>, Map<Long, ? extends Storable>> caches = new HashMap<>();

    static {
        caches.put(Author.class,  new HashMap<Long, Author>());
        caches.put(Genre.class,   new HashMap<Long, Genre>());
        caches.put(Book.class,    new HashMap<Long, Book>());
        caches.put(Comment.class, new HashMap<Long, Comment>());
    }

    @AfterReturning(pointcut = "@annotation(com.martin.caching.Cachable)", returning = "res")
    public void addToCache(JoinPoint point, Object res) {
        Map cache = null;
        Method[] methods = point.getSignature().getDeclaringType().getMethods();
        for (Method method: methods) {
            Cachable annotation = method.getAnnotation(Cachable.class);
            if(annotation != null) {
                cache = caches.get(annotation.target());
            }
        }

        if(res instanceof List) {
            List<Storable> objects = (List) res;
            for (Storable object : objects) {
                if (!cache.containsKey(object.getId())) {
                    cache.put(object.getId(), object);
                    showMessage(format("Объекта %s нет в кеше, добавлен в кеш\n", object));
                }
                else
                    showMessage(format("Объект %s есть в кеше\n", object));
            }
        }
    }

    // Может такое получится что поставим аннотация CachableGetAll, а на update забудем и будем их кеша брать не обновленные данные.
    // Как проверить? Рефлексией анализировать все классы пакета репозиторий?
    //В данном примере кеш не подключен.
/*
    @Around("@annotation(com.martin.caching.Cachable)")
    public Object findInCacheById(ProceedingJoinPoint point) throws Throwable {  Object proceed;
        Map cache = null;
        Class<?> cl = point.getTarget().getClass();
        System.out.println(cl);

        if(cl.equals(AuthorJpaRepository.class))
            cache = authorCache;
        else if(cl.equals(GenreJpaRepository.class))
            cache = genreCache;
        else if(cl.equals(BookJpaRepository.class))
            cache = bookCache;
        else if(cl.equals(CommentJpaRepository.class))
            cache = commentCache;

        long id;
        if((point.getArgs())[0] instanceof Long)
            id = (long)(point.getArgs())[0];
        else
            throw new IllegalArgumentException("Must be point.getArgs())[0] instanceof Integer");

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

*/
    private void showMessage(String str) {
        System.out.printf(ANSI_YELLOW + "------- " + str  + ANSI_BLACK);
    }

    public static <T extends Storable> Map<Long, ? extends Storable> getCache(Class<T> cl){
        return caches.get(cl);
    }

}

