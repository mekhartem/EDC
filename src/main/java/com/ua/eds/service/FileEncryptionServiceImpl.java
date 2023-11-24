package com.ua.eds.service;

import com.ua.eds.dto.FileEncryptionDto;
import com.ua.eds.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileEncryptionServiceImpl implements FileEncryptionService {

    private final FileService fileService;

    @Override
    @SneakyThrows
    public FileEncryptionDto encryptFile(MultipartFile file, String privateKey) {
        var filePath = fileService.saveFile(file);

        List<String> lines = Files.readAllLines(filePath);
        lines.forEach(line -> {
            String encodedLine = EncryptionUtils.encrypt(line, privateKey);
            lines.set(lines.indexOf(line), encodedLine);
        });

        var resultPath = fileService.saveFile(lines);
        return new FileEncryptionDto(filePath.getFileName().toString(), resultPath.getFileName().toString());
    }

    @Override
    @SneakyThrows
    public FileEncryptionDto decryptFile(MultipartFile file, String privateKey) {
        var filePath = fileService.saveFile(file);

        List<String> lines = Files.readAllLines(filePath);
        lines.forEach(line -> {
            String decodedLine = EncryptionUtils.decrypt(line, privateKey);
            lines.set(lines.indexOf(line), decodedLine);
        });

        var resultPath = fileService.saveFile(lines);
        return new FileEncryptionDto(filePath.getFileName().toString(), resultPath.getFileName().toString());
    }
}
