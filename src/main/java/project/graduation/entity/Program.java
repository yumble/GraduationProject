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
@Table(name = "TB_PROGRAM_INFO")
public class Program implements Persistable<String> {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    private String programId;
    @NotNull
    private Integer priority;
    @NotNull
    private String type;
    private String commandPath;
    private String arguments;
    @CreatedBy
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Override
    public String getId() {
        return programId;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }

}