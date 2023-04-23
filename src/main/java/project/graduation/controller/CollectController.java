package project.graduation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.dto.AddressDto;
import project.graduation.dto.CollectDto;
import project.graduation.dto.GPSDto;
import project.graduation.entity.GeneralFile;
import project.graduation.service.CollectService;
import project.graduation.service.GeneralFileService;

import static project.graduation.config.resultform.ResultResponseStatus.REQUEST_ERROR;

@Slf4j
@RequestMapping("/lidar")
@RestController
@RequiredArgsConstructor
public class CollectController {
    private final CollectService collectService;

    @PostMapping
    public ResultResponse<CollectDto> saveFile(@RequestPart @Valid AddressDto address,
                                               @RequestPart @Valid GPSDto location,
                                               @RequestPart MultipartFile file,
                                               BindingResult br) {
        if (br.hasErrors() || file.isEmpty()) {
            throw new ResultException(REQUEST_ERROR);
        }

        return new ResultResponse<>(collectService.saveFile(address, location, file), null);
    }
}
