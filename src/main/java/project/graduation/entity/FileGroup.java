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
@Table(name = "TB_FILE_GROUP_INFO")
public class FileGroup {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "FILE_GROUP_ID")
    private UUID fileGruopId;
    @Column(name = "GROUP_NAME")
    private String groupName;
    @Column(name = "RELATION_DATA")
    private String relationData;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_ID")
    private GeneralFile generalFile;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANNOTATION_ID")
    private Annotation annotation;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLOOR_ID")
    private Floor floor;
//    @OneToMany(mappedBy = "fileGroup", cascade = CascadeType.ALL)
//    private List<GroupMapping> groupMappingList = new ArrayList<>();
    @OneToMany(mappedBy = "fileGroup")
    private List<Collect> collectList = new ArrayList<>();
    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    @Builder
    public FileGroup(String groupName, List<Collect> collectList, Floor floor) {
        this.groupName = groupName;
        this.floor = floor;
        this.collectList.addAll(collectList);
        //collectList.forEach(collect -> collect.mappingFileGroup(this));
    }
    public void updateGroupList(String groupName, Floor floor, List<Collect> collectList){
        this.groupName = groupName;
        this.floor = floor;
        this.collectList = collectList;
    }
}
