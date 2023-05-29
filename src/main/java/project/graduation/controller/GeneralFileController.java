package project.graduation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.entity.GeneralFile;
import project.graduation.service.GeneralFileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/files")
@RestController
@RequiredArgsConstructor
public class GeneralFileController {

    private final GeneralFileService generalFileService;

    @GetMapping("/download/{fileId}")
    public ResponseEntity<InputStreamResource> downloadFile(HttpServletResponse res,
                                                            @PathVariable(value = "fileId") String fileId) throws IOException {

        UUID fileUUID = UUID.fromString(fileId);
        GeneralFile fileInfo = generalFileService.getGeneralFileByFileId(fileUUID);


        File file = generalFileService.getFile(fileInfo);

        res.setContentType(fileInfo.getContentType());
        res.setContentLength((int) file.length());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", fileInfo.getContentType());
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileInfo.getOriginFileName(), StandardCharsets.UTF_8));
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(file);

        return new ResponseEntity<>(new InputStreamResource(fis), headers, HttpStatus.OK);
    }
}
