package com.martin.dao;

import com.martin.domain.Storable;

import java.util.List;

public interface LibraryDao {
    <T extends Storable> void insert(T object);

    <T extends Storable> int getCount(Class<T> cl);
    <T extends Storable> List<T> getAll(Class<T> cl, int page, int amountAuthorsByOnePage);

    <T extends Storable> T findById(Class<T> cl, int id);
    <T extends Storable> List<T> find(T object);

    <T extends Storable> void update(int id, T object);

    <T extends Storable> void delete(Class<T> cl, int id);
}
