package project.graduation.dto;

import lombok.Data;

@Data
public class AddressDto {
    private String id; //apiId;
    private String placeName;
    private String categoryName;
    private String categoryGroupCode;
    private String categoryGroupName;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x; //buildingLatitude;
    private String y; //buildingLongitude;
    private String placeUrl;
    private String distance;
}
