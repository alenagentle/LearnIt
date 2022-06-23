package ru.irlix.learnit.mapper;

import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import ru.irlix.learnit.config.S3Config;
import ru.irlix.learnit.entity.Image;
import ru.irlix.learnit.service.helper.FileHelper;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private S3Config s3Config;

    public Image mapToEntity(MultipartFile file) {
        return fileHelper.saveImageOnS3(file);
    }

    public String mapToString(Image image) {
        if (image == null) {
            return null;
        }
        return String.format("%s/s3/%s/%s", s3Config.getUrl(), s3Config.getBucket(), image.getKey());
    }
}
