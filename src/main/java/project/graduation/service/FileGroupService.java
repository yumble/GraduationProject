package project.graduation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponseStatus;
import project.graduation.dto.*;
import project.graduation.entity.Collect;
import project.graduation.entity.FileGroup;
import project.graduation.entity.Floor;
import project.graduation.repository.CollectRepository;
import project.graduation.repository.FloorRepository;
import project.graduation.repository.FileGroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class FileGroupService {

    private FileGroupRepository fileGroupRepository;
    private FloorRepository floorRepository;
    private CollectRepository collectRepository;

    @Transactional
    public FileGroupDto uploadResultFile(FileGroupRequestDto fileGroupRequestDto) {

        Floor floor = floorRepository.findById(fileGroupRequestDto.getFloorId())
                .orElseThrow(() -> new ResultException(ResultResponseStatus.NOT_FOUND));

        List<Collect> collectList = collectRepository.findAllByCollectId(fileGroupRequestDto.getCollectIdList());
        isFileGroupIdNotNull(collectList);

        FileGroup fileGroup = FileGroup.builder()
                .groupName(fileGroupRequestDto.getGroupName())
                .collectList(collectList)
                .floor(floor)
                .build();

        fileGroup = fileGroupRepository.save(fileGroup);

        collectRepository.updateFileGroupId(fileGroup.getFileGruopId(), collectList);

        return new FileGroupDto(fileGroup);
    }

    public Page<FileGroupDto> getGroupByAddress(String addressId, Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return fileGroupRepository.findAllByAddress(addressId, pageable);
    }

    @Transactional
    public FileGroupDto updateFileGroup(UUID fileGroupId, FileGroupRequestDto fileGroupRequestDto) {
        FileGroup fileGroup = fileGroupRepository.findByFileGroupId(fileGroupId)
                .orElseThrow(() -> new ResultException(ResultResponseStatus.NOT_FOUND));

        Floor floor = fileGroup.getFloor();
        if (!fileGroupRequestDto.getFloorId().equals(fileGroup.getFloor().getFloorId())) {
            floor = floorRepository.findById(fileGroupRequestDto.getFloorId())
                    .orElseThrow(() -> new ResultException(ResultResponseStatus.NOT_FOUND));
        }

        List<Collect> originList = fileGroup.getCollectList();
        List<Collect> newGroupList = collectRepository.findAllByCollectId(fileGroupRequestDto.getCollectIdList());

        // 삭제되어야 할 Collect 식별
        List<Collect> deletedCollects = new ArrayList<>(originList);
        deletedCollects.removeAll(newGroupList);

        // 추가되는 Collect 식별
        List<Collect> addedCollects = new ArrayList<>(newGroupList);
        addedCollects.removeAll(originList);
        isFileGroupIdNotNull(addedCollects);

        collectRepository.updateFileGroupIdToNull(deletedCollects);
        collectRepository.updateFileGroupId(fileGroup.getFileGruopId(), addedCollects);

        fileGroup.updateGroupList(fileGroupRequestDto.getGroupName(), floor, newGroupList);
        return new FileGroupDto(fileGroup);
    }

    @Transactional
    public void deleteFileGroup(UUID fileGroupId) {

        FileGroup fileGroup = fileGroupRepository.findByFileGroupId(fileGroupId)
                .orElseThrow(() -> new ResultException(ResultResponseStatus.NOT_FOUND));

        List<Collect> collectList = fileGroup.getCollectList();
        collectRepository.updateFileGroupIdToNull(collectList);

        fileGroupRepository.delete(fileGroup);
    }

    public void isFileGroupIdNotNull(List<Collect> collectList) {
        for (Collect collect : collectList){
            if(collect.getFileGroup() != null) {
                throw new ResultException(ResultResponseStatus.EXIST_GROUP);
            }
        }
    }
}
