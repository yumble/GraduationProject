package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.FileGroup;

import java.util.Optional;
import java.util.UUID;

public interface FileGroupRepository extends JpaRepository<FileGroup, UUID>, FileGroupRepositoryCustom {
    @Query(value = "select fileGroup from FileGroup fileGroup " +
            " join fetch fileGroup.collectList " +
            " join fetch fileGroup.floor " +
            " join fetch fileGroup.floor.address " +
            " where fileGroup.fileGroupId = :fileGroupId")
    Optional<FileGroup> findByFileGroupId(UUID fileGroupId);
}