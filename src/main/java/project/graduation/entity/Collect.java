package project.graduation.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.graduation.dto.RelationDataDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_COLLECT_LOCATION")
public class Collect {

    @Id
    @NotNull
    @Column(name = "COLLECT_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID collectId;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "FILE_ID")
    private GeneralFile generalFile;
    @Column(name = "TOTALPOINTS")
    private Long totalPoints;
    @Column(name = "ROTATION")
    private String rotation;
    @Column(name = "TRANSLATION")
    private String translation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "STAT", referencedColumnName = "PROGRAM_ID")
    private Program program;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_GROUP_ID")
    private FileGroup fileGroup;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "GPS_ID")
    private GPS gps;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FLOOR_ID")
    private Floor floor;

    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    @Builder
    public Collect(GeneralFile generalFile, GPS gps, Floor floor, Long totalPoints) {
        this.generalFile = generalFile;
        this.gps = gps;
        this.floor = floor;
        this.totalPoints = totalPoints;
        //this.ipV4 = ipV4;
        //this.ipV6 = ipV6;
        //this.program = program;
    }

    public void modifyByProgram(Program program) {
        this.program = program;
        this.generalFile.modifyByProcess(program.getExt(), program.getRoutingKey());
    }

    public void updateTotalPoints(Long totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void updateRelationData(RelationDataDto relationDataDto) {
        this.rotation = relationDataDto.getRotation();
        this.translation = relationDataDto.getTranslation();
    }

    public void mappingFileGroup(FileGroup fileGroup) {
        this.fileGroup = fileGroup;
    }
}
