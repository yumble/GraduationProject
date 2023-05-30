package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.Collect;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CollectRepository extends JpaRepository<Collect, UUID>, CollectRepositoryCustom {
    @Query(value = "select collect from Collect collect join fetch collect.generalFile where collect.generalFile.fileId = :fileId")
    Optional<Collect> findByFileId(UUID fileId);

    @Query(value = "select collect from Collect collect " +
            " join fetch collect.generalFile " +
            " join fetch collect.gps " +
            " join fetch collect.program  join fetch collect.floor " +
            " join fetch collect.floor.address " +
            " where collect.collectId = :collectId")
    Optional<Collect> findByCollect(UUID collectId);

    @Query(value = "SELECT count(c.collectId) FROM Collect c join c.floor f " +
            " WHERE f.floorId = (select c1.floor.floorId from Collect c1 where c1.collectId = :collectId )")
    int countByFloor(UUID collectId);

    @Query(value = "select collect from Collect collect where collect.collectId in :collectIdList")
    List<Collect> findAllByCollectId(List<UUID> collectIdList);

    @Modifying
    @Query(value = "update Collect collect set collect.fileGroup.fileGroupId =:fileGroupId where collect in :collectList ")
    void updateFileGroupId(UUID fileGroupId, List<Collect> collectList);

    @Modifying
    @Query(value = "update Collect collect set collect.fileGroup.fileGroupId = null where collect in :collectList ")
    void updateFileGroupIdToNull(List<Collect> collectList);
}