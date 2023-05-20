package project.graduation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.dto.*;
import project.graduation.service.FileGroupService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static project.graduation.config.resultform.ResultResponseStatus.REQUEST_ERROR;

@Slf4j
@RequestMapping("/groups")
@RestController
@RequiredArgsConstructor
public class FileGroupController {
    private final FileGroupService fileGroupService;

    @PostMapping
    public ResultResponse<FileGroupDto> uploadFileGroup(@RequestBody @Valid FileGroupRequestDto fileGroupRequestDto,
                                                        BindingResult br) {
        if (br.hasErrors()) {
            throw new ResultException(REQUEST_ERROR);
        }

        return new ResultResponse<>(fileGroupService.uploadResultFile(fileGroupRequestDto), null);
    }

    @GetMapping({"", "/{addressId}"})
    public ResultResponse<List<FileGroupDto>> getGroupByAddress(@PathVariable(required = false) String addressId,
                                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<FileGroupDto> fileGroupList = fileGroupService.getGroupByAddress(addressId, page, size);
        return new ResultResponse<>(null, fileGroupList.getContent(),
                Map.of("totalCount", fileGroupList.getTotalElements(),
                        "totalPage", fileGroupList.getTotalPages()
                ));
    }

    //    @GetMapping("/{fileGroupId}/detail")
//    public ResultResponse<FileGroupDetailDto> getFileGroup(@PathVariable UUID fileGroupId) {
//        return new ResultResponse<>(fileGroupService.getFileGroup(fileGroupId), null);
//    }

    @PatchMapping("/{fileGroupId}")
    public ResultResponse<FileGroupDto> updateFileGroup(@PathVariable UUID fileGroupId,
                                                        @RequestBody @Valid FileGroupRequestDto fileGroupRequestDto,
                                                        BindingResult br) {
        if (br.hasErrors()) {
            throw new ResultException(REQUEST_ERROR);
        }
        return new ResultResponse<>(fileGroupService.updateFileGroup(fileGroupId, fileGroupRequestDto), null);
    }

    @DeleteMapping("/{fileGroupId}")
    public ResultResponse<FileGroupDto> deleteFileGroup(@PathVariable UUID fileGroupId) {
        fileGroupService.deleteFileGroup(fileGroupId);
        return new ResultResponse<>(null, null);
    }
}
