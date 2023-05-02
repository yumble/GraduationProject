package project.graduation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.graduation.entity.Address;

@Data
@NoArgsConstructor
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

    public AddressDto(Address address) {
        this.id = address.getAddressId();
        this.place_name = address.getPlaceName();
        this.category_name = address.getCategoryName();
        this.category_group_code = address.getCategoryGroupCode();
        this.category_group_name = address.getCategoryGroupName();
        this.phone = address.getPhone();
        this.address_name = address.getAddressName();
        this.road_address_name = address.getRoadAddressName();
        this.x = address.getBuildingLatitude();
        this.y = address.getBuildingLongitude();
        this.place_url = address.getPlaceUrl();
        this.distance = address.getDistance();
    }
}