package project.graduation.dto;

import lombok.Data;
import project.graduation.entity.Collect;
import project.graduation.entity.FileGroup;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class FileGroupDetailDto {

    private UUID fileGroupId;
    private String groupName;
    private List<UUID> collectIdList;
    private UUID floorId;

    public FileGroupDetailDto(FileGroup fileGroup) {
        this.fileGroupId = fileGroup.getFileGroupId();
        this.groupName = fileGroup.getGroupName();
        this.collectIdList = fileGroup.getCollectList()
                .stream()
                .map(Collect::getCollectId)
                .collect(Collectors.toList());
        this.floorId = fileGroup.getFloor().getFloorId();
    }
}
