package project.graduation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void downloadFile(HttpServletResponse res,
                                @PathVariable(value = "fileId") String fileId) throws IOException {

        UUID fileUUID = UUID.fromString(fileId);
        GeneralFile fileInfo = generalFileService.getGeneralFileByFileId(fileUUID);


        File file = generalFileService.getFile(fileInfo);

        res.setContentType(fileInfo.getContentType());
        res.setContentLength((int) file.length());
        res.setHeader("Content-Disposition", "attachment;filename=\"" + fileInfo.getOriginFileName() + "\"");
        // res 객체를 통해서 서버로부터 파일 다운로드
        OutputStream os = res.getOutputStream();
        // 파일 입력 객체 생성
        FileInputStream fis = new FileInputStream(file);
        FileCopyUtils.copy(fis, os);
        fis.close();
        os.close();
    }
}
