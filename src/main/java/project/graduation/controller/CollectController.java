package project.graduation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.dto.AddressDto;
import project.graduation.dto.CollectDto;
import project.graduation.dto.CollectResponseDto;
import project.graduation.dto.GPSDto;
import project.graduation.entity.GeneralFile;
import project.graduation.service.CollectService;
import project.graduation.service.GeneralFileService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static project.graduation.config.resultform.ResultResponseStatus.REQUEST_ERROR;

@Slf4j
@RequestMapping("/lidars")
@RestController
@RequiredArgsConstructor
public class CollectController {
    private final CollectService collectService;

    @PostMapping
    public ResultResponse<CollectDto> uploadLidarFile(@RequestPart @Valid AddressDto address,
                                                      @RequestPart @Valid GPSDto location,
                                                      @RequestPart MultipartFile file,
                                                      BindingResult br) {
        if (br.hasErrors() || file.isEmpty()) {
            throw new ResultException(REQUEST_ERROR);
        }

        return new ResultResponse<>(collectService.uploadLidarFile(address, location, file), null);
    }

    @GetMapping("/{addressId}")
    public ResultResponse<List<CollectResponseDto>> getLidarFiles(@PathVariable String addressId,
                                                                  @RequestParam(defaultValue = "1") Integer page,
                                                                  @RequestParam(defaultValue = "10") Integer size) {
        Page<CollectResponseDto> collectList = collectService.getLidarFiles(addressId, page, size);
        return new ResultResponse<>(null, collectList.getContent(),
                Map.of("totalCount", collectList.getTotalElements(),
                        "totalPage", collectList.getTotalPages()
                ));
    }

    @GetMapping("/{collectId}/detail")
    public ResultResponse<CollectResponseDto> getLidarFile(@PathVariable UUID collectId) {
        return new ResultResponse<>(collectService.getLidarFile(collectId), null);
    }
}
