package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * CompensationRepository is a {@linkplain Compensation compensation}
 * MongoRepository.
 */
@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    // compensationId is the employeeId
    Compensation findByCompensationId(String compensationId);
}
