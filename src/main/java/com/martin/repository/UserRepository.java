package com.martin.repository;

import com.martin.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> findByUsername(String username);
}
