package Mefo.server.global.firebase.repository;

import Mefo.server.global.firebase.entity.FirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FirebaseRepository extends JpaRepository<FirebaseToken, Long> {
    List<FirebaseToken> findAllByUserId(Long id);

    Optional<FirebaseToken> findByUserIdAndDevice(Long id, String device);
}
