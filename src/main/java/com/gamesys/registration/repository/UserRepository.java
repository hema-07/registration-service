package com.gamesys.registration.repository;

import com.gamesys.registration.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT a FROM User a WHERE a.userEmail = ?1 and a.userDateOfBirth = ?2")
    User findByuserEmail(String userEmail, String userDateOfBirth);
}
