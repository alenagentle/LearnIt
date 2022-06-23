package ru.irlix.learnit.util.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.File;

@FeignClient(name = "s3", url = "${s3.url}/s3/${s3.bucket}", configuration = FeignConfig.class)
public interface S3Client {

    @RequestMapping(method = RequestMethod.POST, value = "/files", consumes = "multipart/form-data")
    void uploadFile(@RequestPart File file);

    @RequestMapping(method = RequestMethod.DELETE, value = "/files/{key}")
    void deleteImage(@PathVariable String key);
}
