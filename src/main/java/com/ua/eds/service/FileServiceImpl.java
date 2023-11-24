package com.ua.eds.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final String UPLOAD_FOLDER = "uploads/";

    @Override
    @SneakyThrows
    public Path saveFile(MultipartFile file) {
        byte[] fileBytes = file.getBytes();

        String fileId = createFileName();
        Path filePath = Paths.get(UPLOAD_FOLDER + fileId);
        Files.write(filePath, fileBytes);

        return filePath;
    }

    @Override
    @SneakyThrows
    public Path saveFile(List<String> lines) {
        String resultFileName = createFileName();

        Path path = Paths.get(resultFileName);
        Files.write(path, lines);

        return path;
    }

    @Override
    public Path getFile(String name) {
        return Paths.get(UPLOAD_FOLDER + name);
    }

    private String createFileName() {
        return UUID.randomUUID().toString();
    }
}
