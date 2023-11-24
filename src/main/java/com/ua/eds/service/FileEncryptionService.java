package com.ua.eds.service;

import com.ua.eds.dto.FileEncryptionDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileEncryptionService {

    FileEncryptionDto encryptFile(MultipartFile file, String privateKey);

    FileEncryptionDto decryptFile(MultipartFile file, String privateKey);

}
