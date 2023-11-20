package com.ua.eds.service;

import com.ua.eds.dto.FileSignatureDto;
import com.ua.eds.dto.FileVerificationDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileVerificationService {

    FileSignatureDto signFile(MultipartFile file, String privateKey);

    FileVerificationDto verifyFile(MultipartFile file, String signature, String publicKey);
}
