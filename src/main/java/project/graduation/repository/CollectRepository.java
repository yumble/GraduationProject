package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.Collect;
import java.util.Optional;
import java.util.UUID;

public interface CollectRepository extends JpaRepository<Collect, UUID>, CollectRepositoryCustom {
    @Query(value = "select collect from Collect collect join fetch collect.generalFile where collect.generalFile.fileId = :fileId")
    Optional<Collect> findByFileId(UUID fileId);
}