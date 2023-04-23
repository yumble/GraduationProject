package project.graduation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {
    private String id; //apiId;
    @NotNull @NotBlank
    private String place_name;
    private String category_name;
    private String category_group_code;
    private String category_group_name;
    private String phone;
    @NotNull @NotBlank
    private String address_name;
    @NotNull @NotBlank
    private String road_address_name;
    @NotNull @NotBlank
    private String x; //buildingLatitude;
    @NotNull @NotBlank
    private String y; //buildingLongitude;
    @NotNull @NotBlank
    private String place_url;
    private String distance;

}