package ru.irlix.learnit.service.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.irlix.learnit.entity.Image;
import ru.irlix.learnit.exception.FileException;
import ru.irlix.learnit.util.client.S3Client;
import ru.irlix.learnit.util.image.ImageCompressor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileHelper {

    private final S3Client s3Client;

    private final ImageCompressor compressor;

    private final List<String> supportedTypes = List.of("svg", "png", "jpg", "jpeg");

    public Image saveImageOnS3(MultipartFile file) {
        if (file == null) {
            return null;
        }

        validateFileExists(file);

        String originFileName = file.getOriginalFilename();
        String format = originFileName.substring(originFileName.lastIndexOf("."));
        validateFileType(format);

        String fileName = UUID.randomUUID() + format;
        File compressedImage = compressor.compress(file, fileName);
        s3Client.uploadFile(compressedImage);
        log.info(String.format("Image with key %s saved in S3", fileName));
        Image image = new Image();
        image.setKey(fileName);
        return image;
    }

    private void validateFileExists(MultipartFile file) {
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new FileException("File is null");
        }
    }

    private void validateFileType(String format) {
        String typeName = format.substring(1);
        if (!supportedTypes.contains(typeName)) {
            throw new FileException("Unsupported file type");
        }
    }

    public void deleteImageFromS3(String key) {
        if (key == null) {
            return;
        }
        s3Client.deleteImage(key);
        log.info(String.format("Image with key %s deleted from S3", key));
    }

    public File saveFile(String fileName, byte[] bytes) throws IOException {
        File outputFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(bytes);
            return outputFile;
        }
    }
}
