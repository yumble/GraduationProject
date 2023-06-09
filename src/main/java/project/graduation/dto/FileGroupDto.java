package project.graduation.dto;

import lombok.Data;
import project.graduation.entity.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class FileGroupDto {

    private UUID fileGroupId;
    private String groupName;
    private List<UUID> collectIdList;
    private UUID floorId;

    public FileGroupDto(FileGroup fileGroup) {
        this.fileGroupId = fileGroup.getFileGroupId();
        this.groupName = fileGroup.getGroupName();
        this.collectIdList = fileGroup.getCollectList()
                .stream()
                .map(Collect::getCollectId)
                .collect(Collectors.toList());
        this.floorId = fileGroup.getFloor().getFloorId();
    }
}
