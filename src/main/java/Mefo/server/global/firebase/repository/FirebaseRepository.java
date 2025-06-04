package Mefo.server.global.firebase.repository;

import Mefo.server.global.firebase.entity.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FirebaseRepository extends JpaRepository<FirebaseToken, Long> {
    Optional<FirebaseToken> findByUserId(Long id);
}
