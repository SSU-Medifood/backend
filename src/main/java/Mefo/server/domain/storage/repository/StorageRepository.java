package Mefo.server.domain.storage.repository;

import Mefo.server.domain.storage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
