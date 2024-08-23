package TestFileUpload.service;


import TestFileUpload.Image;
import TestFileUpload.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {
    private final UploadS3 uploadS3;
    private final ImageMapper imageMapper;

    public Image uploadFile(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();

        String fullPath = uploadS3.upload(file, uuid);

        Image image = new Image(fullPath, uuid, file.getOriginalFilename());

        log.info("fullPath={} \nuuid={} \nfileName = {} ", fullPath, uuid, file.getOriginalFilename());
        imageMapper.insertImageInfo(image);

        return image;
    }

    public void deleteImage(String id) {
        uploadS3.delete(imageMapper.findImageByUuid(id));
        log.info("deleteImage={}", imageMapper.findImageByUuid(id));
        imageMapper.deleteImage(id);

    }
}
