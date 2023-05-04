package project.graduation.entity;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_GENERAL_FILE_INFO")
public class GeneralFile {

    @Id
    @Column(name = "FILE_ID")
    @Comment("파일 ID")
    private UUID fileId;

    @Comment("원본 파일명")
    @Column(name = "ORIGIN_FILENAME")
    private String originFileName;

    @Comment("저장된 파일명")
    @Column(name = "SAVED_FILENAME")
    private String savedFileName;

    @Column(name = "CONTENT_TYPE")
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

    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    public void modifyByProcess(String ext, String uploadDir) {
        this.originFileName = originFileName.replaceFirst("\\.\\w+$", "." + ext);
        this.savedFileName = savedFileName.replaceFirst("\\.\\w+$", "." + ext);
        this.ext = ext;
        this.uploadDir = uploadDir;
    }
}
