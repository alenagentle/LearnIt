package ru.irlix.learnit.util.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.irlix.learnit.exception.FileUploadException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCompressor {

    private final ImageConverter converter;
    private static final int MEGABYTE = 1024 * 1024;

    public File compress(MultipartFile file, String fileName) {
        String originalFileName = file.getOriginalFilename();
        String fileFormat = originalFileName.substring(originalFileName.lastIndexOf("."));
        try {
            byte[] bytes = compressByFileFormat(file, fileFormat);
            return saveFile(fileName, bytes);
        } catch (IOException e) {
            String message = "Error while file saving";
            log.error(message);
            throw new FileUploadException(message);
        }
    }

    private byte[] compressJpg(byte[] bytes) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(bytes);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(byteArrayOutputStream)) {
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
            if (bytes.length > MEGABYTE && writers.hasNext()) {
                ImageWriter writer = writers.next();
                writer.setOutput(imageOutputStream);
                ImageWriteParam param = writer.getDefaultWriteParam();
                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.30f);
                }
                BufferedImage image = ImageIO.read(inputStream);
                writer.write(null, new IIOImage(image, null, null), param);
                writer.dispose();
            } else {
                IOUtils.copy(inputStream, byteArrayOutputStream);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    private byte[] compressByFileFormat(MultipartFile file, String fileFormat) throws IOException {
        return switch (fileFormat) {
            case ".jpg", ".jpeg" -> compressJpg(file.getBytes());
            case ".png" -> compressPng(file);
            default -> file.getBytes();
        };
    }

    private File saveFile(String fileName, byte[] bytes) throws IOException {
        File outputFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(bytes);
            return outputFile;
        }
    }

    private byte[] compressPng(MultipartFile file) throws IOException {
        byte[] jpgBytes = converter.convertFromPngToJpeg(file.getBytes());
        return compressJpg(jpgBytes);
    }
}
