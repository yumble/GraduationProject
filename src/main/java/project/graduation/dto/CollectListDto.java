package project.graduation.dto;

import lombok.Data;
import project.graduation.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static project.graduation.controller.RabbitMQConfig.KEY_COMPLETE;

@Data
public class CollectListDto {

    private String collectId;
    private String generalFileId;
    private String orginFileName;
    private Long fileSize;
    private Boolean isProgramCompleted = false;
    private String gpsId;
    private String addressId;
    private String addressName;
    private String roadAddressName;
    private String placeName;
    private Integer floor;

    private String createdDate;
    private String lastModifiedDate;

    public CollectListDto(Collect collect) {
        this.collectId = String.valueOf(collect.getCollectId());

        this.generalFileId = String.valueOf(collect.getGeneralFile().getFileId());
        this.orginFileName = collect.getGeneralFile().getOriginFileName();
        this.fileSize = collect.getGeneralFile().getSize();

        if(collect.getProgram() != null && collect.getProgram().getRoutingKey().equals(KEY_COMPLETE)) {
            this.isProgramCompleted = true;
        }

        this.gpsId = String.valueOf(collect.getGps().getGpsId());

        this.addressId = collect.getFloor().getAddress().getAddressId();
        this.addressName = collect.getFloor().getAddress().getAddressName();
        this.roadAddressName = collect.getFloor().getAddress().getRoadAddressName();
        this.placeName = collect.getFloor().getAddress().getPlaceName();

        this.floor = collect.getFloor().getFloor();

        this.createdDate = convertToString(collect.getCreatedDate());
        this.lastModifiedDate = convertToString(collect.getLastModifiedDate());
    }

    public static String convertToString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}