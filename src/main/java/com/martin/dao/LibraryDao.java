package com.martin.dao;

import com.martin.domain.Storable;

import java.util.List;

public interface LibraryDao {

    <T extends Storable> int insert(T object);

    <T extends Storable> int getCount(Class<T> cl);
    <T extends Storable> List<T> getAll(Class<T> cl, int page, int amountByOnePage);

    <T extends Storable> T findById(Class<T> cl, int id);
    <T extends Storable> List<T> find(T object);

    <T extends Storable> int update(int id, T object);

    <T extends Storable> int delete(Class<T> cl, int id);
}
