package com.martin.service.mongo;

import com.mongodb.DuplicateKeyException;

public class Helper {
    private Helper() {
    }

    static final String ERROR_STRING = "Операция с объектом %s не выполнена";
    static final String DUPLICATE_ERROR_STRING = "Запись %s существует";
    static final String FORMAT_ERROR_STRING = "Формат поля объекта %s не корректнен";  //Todo public - чтоб был виден при тестировании
    static final String EMPTY_RESULT_BY_ID_ERROR_STRING = "Объект %s c id %d не найден";
    static final String ASSOCIATED_ERROR_STRING = "Объект %s не удалить при ссылающего на него %s объектов";

    static void handlerException(Exception exception, String object) throws Exception {
        System.out.println(exception.getClass().getName());
        System.out.println(exception.getMessage());
        if(exception instanceof DuplicateKeyException)
            throw new Exception(String.format(DUPLICATE_ERROR_STRING, object));
//        else if(message.contains("Значение слишком длинное для поля"))  //Todo наверное, такое в монге не реализовать. Размер ничем не ограничен...
//            throw new Exception(String.format(FORMAT_ERROR_STRING, object));
        else
            throw new Exception(String.format(ERROR_STRING, object));
    }
}