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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import static project.graduation.config.resultform.ResultResponseStatus.NOT_FOUND;

@Slf4j
@Service
public class GeneralFileService {

    private final GeneralFileRepository generalFileRepository;

    private final Environment env;

    private String STORAGE_ROOT_DIR;

    public GeneralFileService(GeneralFileRepository generalFileRepository,
                              Environment env) {
        this.generalFileRepository = generalFileRepository;
        this.env = env;
        STORAGE_ROOT_DIR = env.getProperty("storage.directory.ply");
    }

    public GeneralFile getFileById(UUID fileId) {
        return generalFileRepository.findById(fileId).orElse(null);
    }

    public Path getFilepathByFileId(UUID fileId) {
        Optional<GeneralFile> optionalGeneralFile = generalFileRepository.findById(fileId);
        GeneralFile file = optionalGeneralFile.orElse(null);

        if (file == null) {
            throw new ResultException(NOT_FOUND);
        }

        String filename = file.getSavedFileName();
        String uploadDir = file.getUploadDir();

        Path targetFilepath = Paths.get(STORAGE_ROOT_DIR, uploadDir, filename);
        File targetFile = targetFilepath.toFile();
        if (!targetFile.exists()) {
            log.error("method::getFilepathByFileId, Storage, FILE_NOT_FOUND, targetFile: {}", targetFile.getAbsoluteFile());
            throw new ResultException(NOT_FOUND);
        }

        return targetFilepath;
    }

    @Transactional
    public GeneralFile saveFile(String uploadDir, MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        UUID uuid = UUID.randomUUID();
        String savedFileName = String.format("%s.%s", uuid, extension);

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
        generalFileRepository.save(generalFile);

        return generalFile;
    }
    public void deleteFile(UUID fileId) throws IOException {

        Path filePath = getFilepathByFileId(fileId);

        File file = new File(filePath.toString());
        if (file.exists()) {
            Files.delete(filePath);
        }
    }
}