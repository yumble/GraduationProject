package project.graduation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.graduation.dto.ProgramDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TB_PROGRAM_INFO")
public class Program {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PROGRAM_ID")
    private UUID programId;
    @NotNull
    @Column(name = "PRIORITY")
    private Integer priority;
    @NotNull
    @Column(name = "ROUTING_KEY")
    private String routingKey;
    @Column(name = "COMMAND_PATH")
    private String commandPath;
    @Column(name = "DIR")
    private String dir;
    @Column(name = "EXT")
    private String ext;
    @CreatedDate
    @Column(name = "CREATED_DATE", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null")
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    public Program(ProgramDto programDto) {
        this.priority = programDto.getPriority();
        this.routingKey = programDto.getRoutingKey();
        this.commandPath = programDto.getCommandPath();
        this.dir = programDto.getDir();
        this.ext = programDto.getExt();
    }
}