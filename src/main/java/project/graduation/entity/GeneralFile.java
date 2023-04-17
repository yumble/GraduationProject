package project.graduation.entity;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
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
@Table(name = "TB_GENERAL_FILE_INFO")
public class GeneralFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Comment("파일 ID")
    private String fileId;

    @Comment("원본 파일명")
    @Column(name = "ORIGIN_FILENAME")
    private String originFileName;

    @Comment("저장된 파일명")
    @Column(name = "SAVED_FILENAME")
    private String savedFileName;

    @Comment("Content-Type")
    private String contentType;

    @Column(name = "EXT")
    private String ext;

    @Comment("파일 크기 (bytes)")
    @Column(name = "SIZE")
    private Long size;

    @Comment("업로드 디렉토리")
    @Column(name = "UPLOAD_DIR")
    private String uploadDir;

    @CreatedBy
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
