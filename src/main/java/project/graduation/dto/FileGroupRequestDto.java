package project.graduation.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class FileGroupRequestDto {
    private List<UUID> collectIdList;
    private String groupName;
    private UUID floorId;
}