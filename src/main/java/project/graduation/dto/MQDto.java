package project.graduation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MQDto {
    private String routingKey;
    private String fileId;

    public MQDto(String routingKey, String fileId) {
        this.routingKey = routingKey;
        this.fileId = fileId;
    }
}
