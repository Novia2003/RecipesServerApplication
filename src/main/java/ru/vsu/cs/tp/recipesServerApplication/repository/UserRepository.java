package ru.vsu.cs.tp.recipesServerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.tp.recipesServerApplication.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
