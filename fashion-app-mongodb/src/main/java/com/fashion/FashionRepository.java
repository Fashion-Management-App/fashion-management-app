package com.fashion;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FashionRepository extends MongoRepository<Fashion, String> {
}
