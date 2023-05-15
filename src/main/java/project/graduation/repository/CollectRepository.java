package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.Collect;
import java.util.Optional;
import java.util.UUID;

import static project.graduation.entity.QAddress.address;
import static project.graduation.entity.QCollect.collect;
import static project.graduation.entity.QFloor.floor1;
import static project.graduation.entity.QGPS.gPS;
import static project.graduation.entity.QGeneralFile.generalFile;
import static project.graduation.entity.QProgram.program;

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

    @Query(value = "SELECT count(c) FROM Collect c join c.floor f " +
            " WHERE f.floorId = (select c1.floor.floorId from Collect c1 where c1.collectId = :collectId )")
    int countByFloor(UUID collectId);
}