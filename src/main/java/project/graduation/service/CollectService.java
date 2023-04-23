package project.graduation.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.dto.AddressDto;
import project.graduation.dto.CollectDto;
import project.graduation.dto.GPSDto;
import project.graduation.entity.*;
import project.graduation.repository.CollectRepository;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class CollectService {
    private final CollectRepository collectRepository;

    private final GeneralFileService generalFileService;
    private final FloorService floorService;

    @Transactional
    public CollectDto saveFile(AddressDto addressDto, GPSDto location, MultipartFile file) {

        GeneralFile savedFile = generalFileService.saveFile("ply", file);

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
}
