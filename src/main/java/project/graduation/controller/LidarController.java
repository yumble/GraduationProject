package project.graduation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.dto.AddressDto;
import project.graduation.dto.GPSDto;
import project.graduation.entity.GeneralFile;
import project.graduation.service.GeneralFileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static project.graduation.config.resultform.ResultResponseStatus.REQUEST_ERROR;

@Slf4j
@RequestMapping("/lidar")
@RestController
@RequiredArgsConstructor
public class LidarController {

    private final GeneralFileService generalFileService;
    private final LidarService lidarService;

    @PostMapping
    public ResultResponse<GeneralFile> saveFile(@RequestPart @Valid AddressDto address,
                                                @RequestPart @Valid GPSDto location,
                                                @RequestPart MultipartFile file,
                                                BindingResult br) {
        if (br.hasErrors()) {
            throw new ResultException(REQUEST_ERROR);
        }

        lidarService.saveFile(address, location, file);

        return new ResultResponse<>(generalFile, null);
    }
}ㅡ
