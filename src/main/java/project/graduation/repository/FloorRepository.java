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
}