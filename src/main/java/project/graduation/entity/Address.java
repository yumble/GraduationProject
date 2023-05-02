package project.graduation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.graduation.dto.AddressDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_ADDRESS_INFO")
public class Address {
    //
    @Id
    @NotNull
    @Column(name = "ADDRESS_ID")
    private String addressId;
    @Column(name = "PLACE_NAME")
    private String placeName;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Column(name = "CATEGORY_GROUP_CODE")
    private String categoryGroupCode;
    @Column(name = "CATEGORY_GROUP_NAME")
    private String categoryGroupName;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "ADDRESS_NAME")
    private String addressName;
    @Column(name = "ROAD_ADDRESS_NAME")
    private String roadAddressName;
    @Column(name = "BUILDING_LATITUDE")
    private String buildingLatitude;
    @Column(name = "BUILDING_LONGITUDE")
    private String buildingLongitude;
    @Column(name = "PLACE_URL")
    private String placeUrl;
    @Column(name = "DISTANCE")
    private String distance;
    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    @OneToMany(mappedBy = "address")
    private List<Floor> floors = new ArrayList<>();

    public Address(AddressDto addressDto) {
        this.addressId = addressDto.getId();
        this.placeName = addressDto.getPlace_name();
        this.categoryName = addressDto.getCategory_name();
        this.categoryGroupCode = addressDto.getCategory_group_code();
        this.categoryGroupName = addressDto.getCategory_group_name();
        this.phone = addressDto.getPhone();
        this.addressName = addressDto.getAddress_name();
        this.roadAddressName = addressDto.getRoad_address_name();
        this.buildingLatitude = addressDto.getX();
        this.buildingLongitude = addressDto.getY();
        this.placeUrl = addressDto.getPlace_url();
        this.distance = addressDto.getDistance();
    }
}
