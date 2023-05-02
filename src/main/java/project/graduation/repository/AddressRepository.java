package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.Address;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, String> {
    @Query(value = "SELECT a FROM Address a WHERE a.addressId = :addressId")
    Optional<Address> findByAddressId(String addressId);

    @Query(value = "SELECT a FROM Address a join fetch a.floors Where a.addressId = :addressId")
    Address findByBuilding(String addressId);
}