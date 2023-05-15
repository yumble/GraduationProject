package project.graduation.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.graduation.dto.AddressDto;
import project.graduation.entity.Address;
import project.graduation.entity.Floor;
import project.graduation.repository.FloorRepository;
import project.graduation.repository.AddressRepository;

import java.util.Optional;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class FloorService {
    private final AddressRepository addressRepository;
    private final FloorRepository floorRepository;

    @Transactional
    public Floor saveFloor(AddressDto addressDto, Integer buildingFloor) {
        return floorRepository.findByAddress(addressDto.getId(), buildingFloor)
                .orElseGet(() -> {
                    Optional<Address> optionalAddress = addressRepository.findByAddressId(addressDto.getId());
                    Address address = optionalAddress.orElse(new Address(addressDto));
                    return floorRepository.save(Floor.builder()
                            .address(address)
                            .floor(buildingFloor)
                            .build());
                });
    }

    @Transactional
    public void deleteFloor(Floor floor){
        floorRepository.delete(floor);
        if( floorRepository.countByAddress(floor.getFloorId()) == 0) {
            addressRepository.delete(floor.getAddress());
        }
    }

}
