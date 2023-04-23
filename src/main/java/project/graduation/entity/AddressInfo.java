package project.graduation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_ADDRESS_INFO2")
public class AddressInfo {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ADDRESS_ID")
    private UUID addressId;
    @NotNull
    @Column(name = "ADDRESS_NAME")
    private String addressName;
    @Column(name = "REGION_1DEPTH_NAME")
    private String region1depth;
    @Column(name = "REGION_2DEPTH_NAME")
    private String region2depth;
    @Column(name = "REGION_3DEPTH_NAME")
    private String region3depth;
    @Column(name = "ROAD_NAME")
    private String roadName;
    @Column(name = "UNDER_GROUND_YN")
    private String underGroundYn;
    @Column(name = "MAIN_BUILDING_NO")
    private String mainBuildingNo;
    @Column(name = "SUB_BUILDING_NO")
    private String subBuildingNo;
    @Column(name = "BUILDING_NAME")
    private String buildingName;
    @Column(name = "ZONE_NO")
    private String zoneNo;
    @Column(name = "BUILDING_LATITUDE")
    private String buildingLatitude;
    @Column(name = "BUILDING_LONGITUDE")
    private String buildingLongitude;
    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

}
