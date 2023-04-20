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

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_ADDRESS_FLOOR")
public class Floor {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FLOOR_ID")
    private UUID floorId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ADDRESS_ID")
    private AddressInfo addressInfo;
    @NotNull
    @Column(name = "FLOOR")
    private Integer floor;
    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

}
