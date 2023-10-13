package de.neuefische.backend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LibraryRepository extends MongoRepository<Book, String> {

    @Query("{ 'title': { '$regex': ?0, '$options': 'i' } }")
    List<Book> findByTitleRegexIgnoreCase(String title);

}
