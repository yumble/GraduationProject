package project.graduation.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.config.resultform.ResultException;
import project.graduation.entity.GeneralFile;
import project.graduation.repository.GeneralFileRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static project.graduation.config.resultform.ResultResponseStatus.NOT_FOUND;
import static project.graduation.config.resultform.ResultResponseStatus.UPLOAD_ERROR;

@Slf4j
@Service
public class GeneralFileService {

    private final GeneralFileRepository generalFileRepository;
    private final Environment env;
    private String STORAGE_ROOT_DIR;
    private String DOWNLOAD_DIR;

    public GeneralFileService(GeneralFileRepository generalFileRepository,
                              Environment env) {
        this.generalFileRepository = generalFileRepository;
        this.env = env;
        STORAGE_ROOT_DIR = env.getProperty("storage.directory.server");
        DOWNLOAD_DIR = env.getProperty("storage.directory.fileGroup");
    }

    public GeneralFile getGeneralFileByFileId(UUID fileId) {
        return generalFileRepository.findById(fileId).orElse(null);
    }

    public File getFile(GeneralFile generalFile) {

        if (generalFile == null) {
            throw new ResultException(NOT_FOUND);
        }

        Path targetPath = Paths.get(STORAGE_ROOT_DIR, DOWNLOAD_DIR, generalFile.getSavedFileName());

        File targetFile = targetPath.toFile();

        if (!targetFile.exists()) {
            log.error("method::getFilepathByFileId, Storage, FILE_NOT_FOUND, targetFile: {}", targetFile.getAbsoluteFile());
            throw new ResultException(NOT_FOUND);
        }

        return targetFile;
    }

    public String getFilePathStr(GeneralFile generalFile) {
        return String.valueOf(Paths.get(STORAGE_ROOT_DIR, DOWNLOAD_DIR, generalFile.getSavedFileName()));
    }

    @Transactional
    public GeneralFile saveFile(String uploadDir, MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        UUID uuid = UUID.randomUUID();
        String savedFileName = String.format("%s.%s", uuid, extension);


        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        try {
            System.out.println("URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8.toString()); = "
                    + URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new ResultException(UPLOAD_ERROR);
        }

        GeneralFile generalFile = GeneralFile.builder()
                .fileId(uuid)
                .contentType(file.getContentType())
                .uploadDir(uploadDir)
                .originFileName(file.getOriginalFilename())
                .savedFileName(savedFileName)
                .ext(extension)
                .size(file.getSize())
                .build();

        File targetDir = Paths.get(STORAGE_ROOT_DIR, uploadDir).toFile();

        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        File savedFile = Paths.get(targetDir.getAbsolutePath(), savedFileName).toFile();

        try {
            InputStream fileStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, savedFile);
        } catch (IOException ioException) {
            FileUtils.deleteQuietly(savedFile);
            throw new ResultException(NOT_FOUND);
        }
        return generalFileRepository.save(generalFile);
    }

    @Transactional
    public void deleteFile(GeneralFile generalFile) throws IOException {
        Path filePath = Path.of(getFilePathStr(generalFile));
        Files.deleteIfExists(filePath);
        //generalFileRepository.delete(generalFile);
    }

}