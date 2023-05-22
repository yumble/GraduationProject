//package project.graduation.entity;
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@DynamicInsert
//@DynamicUpdate
//@EntityListeners(AuditingEntityListener.class)
//@Table(name = "TB_COLLECT_GROUP_MAPPING")
//public class GroupMapping {
//
//    @Id
//    @NotNull
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "MAPPING_ID")
//    private Integer mappingId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FILE_GROUP_ID")
//    private FileGroup fileGroup;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ANNOTATION_ID")
//    private Collect collect;
//
//    public void mappingFileGroup(FileGroup fileGroup){
//        this.fileGroup = fileGroup;
//    }
//}
