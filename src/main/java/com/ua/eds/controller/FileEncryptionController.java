package com.ua.eds.controller;

import com.ua.eds.service.FileEncryptionService;
import com.ua.eds.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/encrypt")
public class FileEncryptionController {

    private final FileEncryptionService fileEncryptionService;

    @GetMapping("/keys")
    public ResponseEntity<?> generateKeys() {
        return ResponseEntity.ok(EncryptionUtils.generateSecretKey());
    }

    @PostMapping("/encode")
    public ResponseEntity<?> encryptFile(@RequestParam("file") MultipartFile file, String privateKey) {
        return ResponseEntity.ok(fileEncryptionService.encryptFile(file, privateKey));
    }

    @PostMapping("/decode")
    public ResponseEntity<?> decryptFile(@RequestParam("file") MultipartFile file, String privateKey) {
        return ResponseEntity.ok(fileEncryptionService.decryptFile(file, privateKey));
    }
}
