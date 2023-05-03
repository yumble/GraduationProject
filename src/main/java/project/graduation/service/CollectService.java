package project.graduation.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.dto.*;
import project.graduation.entity.*;
import project.graduation.repository.CollectRepository;

import java.util.UUID;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class CollectService {
    private final CollectRepository collectRepository;

    private final GeneralFileService generalFileService;
    private final FloorService floorService;

    @Transactional
    public CollectDto uploadLidarFile(AddressDto addressDto, GPSDto location, MultipartFile file) {

        GeneralFile savedFile = generalFileService.saveFile("lidar", file);

        Floor floor = floorService.saveFloor(addressDto, location.getFloor());

        GPS gps = new GPS(location);

        Collect collect = Collect.builder()
                .generalFile(savedFile)
                .gps(gps)
                .floor(floor)
                .build();

        collect = collectRepository.save(collect);

        return new CollectDto(collect);
    }

    public Page<CollectListDto> getLidarFiles(String addressId, Integer page, Integer size){
        PageRequest pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return collectRepository.findAllByAddressId(addressId, pageable);
    }
    public CollectDetailDto getLidarFile(UUID collectId){
        return collectRepository.findByCollectId(collectId);
    }
}
