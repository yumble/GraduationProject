package project.graduation.dto;

import lombok.Data;
import project.graduation.entity.Collect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CollectDetailDto {

    private String collectId;
    private String generalFileId;
    private String originFileName;
    private Long totalPoints;
    private Long fileSize;
    private String programId;
    private String gpsId;
    private String latitude;
    private String longitude;
    private String altitude;
    private String addressId;
    private String addressName;
    private String roadAddressName;
    private String placeName;

    private String floorId;
    private Integer floor;

    private String createdDate;
    private String lastModifiedDate;

    public CollectDetailDto(Collect collect) {
        this.collectId = String.valueOf(collect.getCollectId());

        this.generalFileId = String.valueOf(collect.getGeneralFile().getFileId());
        this.fileSize = collect.getGeneralFile().getSize();
        this.originFileName = collect.getGeneralFile().getOriginFileName();
        this.totalPoints = collect.getTotalPoints();

        if(collect.getProgram() != null)
            this.programId = String.valueOf(collect.getProgram().getProgramId());

        this.gpsId = String.valueOf(collect.getGps().getGpsId());
        this.latitude = collect.getGps().getLatitude();
        this.longitude = collect.getGps().getLongitude();
        this.altitude = collect.getGps().getAltitude();

        this.addressId = collect.getFloor().getAddress().getAddressId();
        this.addressName = collect.getFloor().getAddress().getAddressName();
        this.roadAddressName = collect.getFloor().getAddress().getRoadAddressName();
        this.placeName = collect.getFloor().getAddress().getPlaceName();

        this.floorId = String.valueOf(collect.getFloor().getFloorId());
        this.floor = collect.getFloor().getFloor();

        this.createdDate = convertToString(collect.getCreatedDate());
        this.lastModifiedDate = convertToString(collect.getLastModifiedDate());
    }

    public static String convertToString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}