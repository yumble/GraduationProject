package project.graduation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.graduation.entity.Collect;

@Data
public class CollectDto {
    private String collectId;
    private String fileId;
    private String gpsId;
    private String floorId;
    private String addressId;

    public CollectDto(Collect collect) {
        this.collectId = String.valueOf(collect.getCollectId());
        this.fileId = String.valueOf(collect.getGeneralFile().getFileId());
        this.gpsId = String.valueOf(collect.getGps().getGpsId());
        this.floorId = String.valueOf(collect.getFloor().getFloorId());
        this.addressId = collect.getFloor().getAddress().getAddressId();
    }
}