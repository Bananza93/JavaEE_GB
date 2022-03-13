package ru.geekbrains.lesson7.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${location}")
    private String location;
    private Path rootLocation;

    @PostConstruct
    void init() {
        rootLocation = Paths.get(location);
    }

    @Override
    public void store(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new RuntimeException("File is empty");

        try {
            Path destinationPath = rootLocation
                    .resolve(Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize()
                    .toAbsolutePath();
            if (!destinationPath.getParent().equals(rootLocation.toAbsolutePath()))
                throw new RuntimeException("Incorrect storage directory");

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while saving file " + file.getOriginalFilename() + ": " + e.getCause() + " | " + e.getLocalizedMessage());
        }
    }
}
