package de.neuefische.backend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LibraryRepository extends MongoRepository<Book, String> {

    List<Book> findByTitleContaining(String title);

}
