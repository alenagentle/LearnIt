package ru.irlix.learnit.util.image;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageConverter {

    public byte[] convertFromPngToJpeg(byte[] input) {
        try (InputStream inputStream = new ByteArrayInputStream(input);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream)) {
            BufferedImage image = ImageIO.read(inputStream);
            final BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
            ImageIO.write(bufferedImage, "jpg", ios);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
