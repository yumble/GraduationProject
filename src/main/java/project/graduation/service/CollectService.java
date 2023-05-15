package project.graduation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponseStatus;
import project.graduation.dto.*;
import project.graduation.entity.*;
import project.graduation.repository.CollectRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static project.graduation.controller.RabbitMQConfig.*;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class CollectService {

    private final RabbitTemplate rabbitTemplate;
    private final CollectRepository collectRepository;
    private final GeneralFileService generalFileService;
    private final FloorService floorService;

    @Transactional
    public CollectDto uploadLidarFile(AddressDto addressDto, GPSDto location, Long totalPoints, MultipartFile file) {

        GeneralFile savedFile = generalFileService.saveFile("lidar", file);
        Floor floor = floorService.saveFloor(addressDto, location.getFloor());
        GPS gps = new GPS(location);
        Collect collect = Collect.builder()
                .generalFile(savedFile)
                .gps(gps)
                .floor(floor)
                .totalPoints(totalPoints)
                .build();

        collect = collectRepository.save(collect);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, KEY_PLY2PCD, new MQDto(KEY_PLY2PCD, savedFile.getFileId().toString()));
        return new CollectDto(collect);
    }

    public Page<CollectListDto> getLidarFiles(String addressId, Integer page, Integer size){
        PageRequest pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return collectRepository.findAllByAddressId(addressId, pageable);
    }
    public CollectDetailDto getLidarFile(UUID collectId){
        return collectRepository.findByCollectId(collectId);
    }
    @Transactional
    public void deleteLidarFile(UUID collectId) throws IOException {

        Optional<Collect> optionalCollect = collectRepository.findByCollect(collectId);
        if(optionalCollect.isEmpty()) {
            throw new ResultException(ResultResponseStatus.NOT_FOUND);
        }
        Collect collect = optionalCollect.get();

        collectRepository.delete(collect); //GPS 도 같이 삭제되는 지 확인
        generalFileService.deleteFile(collect.getGeneralFile());

        if(collectRepository.countByFloor(collect.getCollectId()) == 0) {
            floorService.deleteFloor(collect.getFloor());
        }

    }
}
