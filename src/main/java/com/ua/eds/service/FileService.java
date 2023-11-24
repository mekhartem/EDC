package com.ua.eds.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface FileService {

    Path saveFile(MultipartFile file);

    Path saveFile(List<String> lines);

    Path getFile(String name);

}
