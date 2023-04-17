package project.graduation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_ADDRESS_INFO")
public class AddressInfo implements Persistable<String> {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private String addressId;
    @NotNull
    private String addressName;
    @Column(name = "REGION_1DEPTH_NAME")
    private String region1depth;
    @Column(name = "REGION_2DEPTH_NAME")
    private String region2depth;
    @Column(name = "REGION_3DEPTH_NAME")
    private String region3depth;
    private String roadName;
    private String underGroundYn;
    private String mainBuildingNo;
    private String subBuildingNo;
    private String buildingName;
    private String zoneNo;
    private BigDecimal buildingLatitude;
    private BigDecimal buildingLongitude;
    @CreatedBy
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Override
    public String getId() {
        return addressId;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }

}
