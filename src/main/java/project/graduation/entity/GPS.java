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
import project.graduation.dto.GPSDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_GPS_INFO")
public class GPS {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "GPS_ID")
    private UUID gpsId;
    @NotNull
    @Column(name = "LATITUDE")
    private String latitude;
    @NotNull
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "ALTITUDE")
    private String altitude;
    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    public GPS(GPSDto location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.altitude = location.getAltitude();
//        this.latitude = BigDecimal.valueOf(Long.parseLong(location.getLatitude()));
//        this.longitude = BigDecimal.valueOf(Long.parseLong(location.getLongitude()));
//        this.altitude = BigDecimal.valueOf(Long.parseLong(location.getAltitude()));
    }
}
