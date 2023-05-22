package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.Floor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FloorRepository extends JpaRepository<Floor, UUID> {
    @Query(value = "SELECT f FROM Floor f WHERE f.address.addressId = :addressId and f.floor = :floor")
    Optional<Floor> findByAddress(String addressId, Integer floor);
    @Query(value = "SELECT count(f) FROM Floor f JOIN f.address " +
            " WHERE f.address.addressId = (select f1.address.addressId from Floor f1 where f1.floorId = :floorId )")
    Integer countByAddress(UUID floorId);
}