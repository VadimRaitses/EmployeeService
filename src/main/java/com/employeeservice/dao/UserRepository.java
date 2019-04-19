package com.employeeservice.dao;

import com.employeeservice.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Account, Long> {
    Account findByEmailAndPassword(String email, String password);

    Account findByEmail(String email);
}
