package com.ua.eds.service;

import com.ua.eds.dto.FileSignatureDto;
import com.ua.eds.dto.FileVerificationDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;

import com.ua.eds.utils.*;

@Service
@RequiredArgsConstructor
public class FileVerificationServiceImpl implements FileVerificationService {

    private final FileService fileService;

    @Override
    @SneakyThrows
    public FileSignatureDto signFile(MultipartFile file, String privateKey) {
        var filePath = fileService.saveFile(file);

        byte[] fileData = Files.readAllBytes(filePath);

        return new FileSignatureDto(filePath.getFileName().toString(), DigitalSignatureUtils.sign(fileData, privateKey));
    }

    @Override
    @SneakyThrows
    public FileVerificationDto verifyFile(MultipartFile file, String signature, String publicKey) {
        var filePath = fileService.getFile(file.getOriginalFilename());

        byte[] fileData = Files.readAllBytes(filePath);

        return new FileVerificationDto(filePath.getFileName().toString(), DigitalSignatureUtils.verify(fileData, signature, publicKey));
    }
}
