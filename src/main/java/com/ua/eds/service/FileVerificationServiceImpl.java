package com.ua.eds.service;

import com.ua.eds.dto.FileSignatureDto;
import com.ua.eds.dto.FileVerificationDto;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.ua.eds.utils.*;

@Service
public class FileVerificationServiceImpl implements FileVerificationService {

    private static final String UPLOAD_FOLDER = "uploads/";

    @Override
    @SneakyThrows
    public FileSignatureDto signFile(MultipartFile file, String privateKey) {
        byte[] fileBytes = file.getBytes();

        String fileId = UUID.randomUUID().toString();
        Path filePath = Paths.get(UPLOAD_FOLDER + fileId);
        Files.write(filePath, fileBytes);

        byte[] fileData = Files.readAllBytes(filePath);

        return new FileSignatureDto(fileId, DigitalSignatureUtils.sign(fileData, privateKey));
    }

    @Override
    @SneakyThrows
    public FileVerificationDto verifyFile(MultipartFile file, String signature, String publicKey) {
        String fileId = file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_FOLDER + fileId);

        byte[] fileData = Files.readAllBytes(filePath);

        return new FileVerificationDto(fileId, DigitalSignatureUtils.verify(fileData, signature, publicKey));
    }
}
