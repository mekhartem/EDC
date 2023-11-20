package com.ua.eds.controller;

import com.ua.eds.dto.SignatureKeyDto;
import com.ua.eds.service.FileVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ua.eds.utils.*;

@RestController
@RequiredArgsConstructor
public class FileVerificationController {

    private final FileVerificationService fileVerificationService;

    @GetMapping("/keys")
    public ResponseEntity<?> generateKeys() {
        return ResponseEntity.ok(DigitalSignatureUtils.generateKeys());
    }

    @PostMapping("/sign")
    public ResponseEntity<?> signFile(@RequestParam("file") MultipartFile file, String privateKey) {
        return ResponseEntity.ok(fileVerificationService.signFile(file, privateKey));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyFile(@RequestParam("file") MultipartFile file, String signature, String publicKey) {
        return ResponseEntity.ok(fileVerificationService.verifyFile(file, signature, publicKey));
    }

}
