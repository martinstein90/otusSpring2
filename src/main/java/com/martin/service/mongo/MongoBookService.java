package com.martin.service.mongo;


        import com.martin.domain.mongo.MongoBook;
        import org.bson.types.ObjectId;

        import java.util.List;

public interface MongoBookService {

    MongoBook add(String title, ObjectId authorId) throws Exception;

    MongoBook add(String authorTitle, String authorFirsname, String authorLastname) throws Exception;

    long getCount();

    List<MongoBook> getAll(int page, int amountByOnePage);

    List<MongoBook> findByTitle(String title) throws Exception;

    List<MongoBook> findByAuthor(ObjectId authorId) throws Exception;


    MongoBook findById(ObjectId id) throws Exception;

    MongoBook update(ObjectId id, String title) throws Exception;

    MongoBook update(ObjectId id, String bookTitle, ObjectId authorId) throws Exception;

    void delete(ObjectId id) throws Exception;

    void deleteAll();
}