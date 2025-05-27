package Mefo.server.domain.storage.repository;

import Mefo.server.domain.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findById(Long id);
    List<Storage> findAllByUserId(Long id);
}
