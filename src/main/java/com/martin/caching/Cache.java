package com.martin.caching;

import com.martin.domain.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.martin.caching.Cachable.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.martin.caching.Cachable.Operation.ADD;
import static com.martin.caching.Cachable.Operation.GET;
import static com.martin.helper.Ansi.ANSI_BLACK;
import static com.martin.helper.Ansi.ANSI_YELLOW;


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

    @Around("@annotation(com.martin.caching.Cachable)")
    public Object cache(ProceedingJoinPoint point) throws Throwable {
        Object result;
        Class<?> cl = point.getTarget().getClass();
        Map<Long, ? extends Storable> cache = getCacheByClass(cl);
        Class<? extends Storable> target = getTargetByClass(cl);
        Operation operation = getOperationByClass(cl);
        boolean disable = getDisableByClass(cl);

        if(disable)
            return point.proceed();

        if (operation == ADD) {
            //Todo предполагается что пользователь будет вешать эту аннотацию с данными параметрами
            //на методы типа List<Author/Genre/Book> getAll(), List<Author> find().
            result = point.proceed();
            if(result instanceof List) {
                addToCache(cache, target, (List<? extends Storable>)result);
            }
            else
                throw new IllegalArgumentException("Return value must be a List");
        }
        else if(operation == GET) {
            Object[] args = point.getArgs();
            if(args.length !=0) {
                if(args[0] instanceof Integer) {
                    Storable byIdFromCache = findByIdFromCache(cache, target, (int) args[0]);
                    if(byIdFromCache != null)
                        return byIdFromCache;
                    else
                        result = point.proceed();
                }
                else
                    throw new IllegalArgumentException("args[0] must be instance of Integer");
            }
            else
                throw new IllegalArgumentException("Agrs length must be != 0");
        }
        return null;
    }


    private void addToCache(Map<Long, ? extends Storable> cache,
                           Class<? extends Storable> target,
                           List<? extends Storable> result) {
        for(Storable storable :  result) {
            if (!cache.containsKey(storable.getId())) {
                showMessage(String.format("Объект %s c id %d добавлен в кеш!\n", target, storable.getId()));
                //cache.put(storable.getId(), (Author)storable); //Todo не компелируется.
            }
        }
    }

    private Storable findByIdFromCache(Map<Long, ? extends Storable> cache,
                                       Class<? extends Storable> target,
                                       int id) throws Throwable {
        Storable result = null;
        if(cache.containsKey(id)) {
            showMessage(String.format("Объект %s с id %d взят из кеша\n", target, id));
            result = cache.get(id);
        }
        return result;
    }


    private Map<Long, ? extends Storable> getCacheByClass(Class cl) {
        Method[] methods = cl.getMethods();
        for (Method method: methods) {
            Cachable annotation = method.getAnnotation(Cachable.class);
            if(annotation != null) {
                return caches.get(annotation.target());
            }
        }
        return null;
    }

    private Class< ? extends Storable> getTargetByClass(Class cl) {
        Method[] methods = cl.getMethods();
        for (Method method: methods) {
            Cachable annotation = method.getAnnotation(Cachable.class);
            if(annotation != null) {
                return annotation.target();
            }
        }
        return null;
    }

    private Operation getOperationByClass(Class cl) {
        Method[] methods = cl.getMethods();
        for (Method method: methods) {
            Cachable annotation = method.getAnnotation(Cachable.class);
            if(annotation != null) {
                return annotation.operation();
            }
        }
        return null;
    }

    private boolean getDisableByClass(Class cl) {
        Method[] methods = cl.getMethods();
        for (Method method: methods) {
            Cachable annotation = method.getAnnotation(Cachable.class);
            if(annotation != null) {
                return annotation.disable();
            }
        }
        return false;
    }

    private void showMessage(String str) {
        System.out.printf(ANSI_YELLOW + "------- " + str  + ANSI_BLACK);
    }

    public static <T extends Storable> Map<Long, ? extends Storable> getCache(Class<T> cl){
        return caches.get(cl);
    }
}

