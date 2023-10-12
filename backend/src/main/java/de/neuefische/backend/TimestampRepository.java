package de.neuefische.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimestampRepository extends MongoRepository<Timestamp, String> {
}
