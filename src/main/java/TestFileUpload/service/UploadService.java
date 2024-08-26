package TestFileUpload.service;


import TestFileUpload.Image;
import TestFileUpload.ImageMapper;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {

    private final AmazonS3 amazonS3;
    //private final UploadS3 uploadS3;
    private final ImageMapper imageMapper;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Image uploadFile(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(file.getInputStream().available());

        // putObject(버킷명, 파일명, 파일데이터, 메타데이터)로 S3에 객체 등록
        amazonS3.putObject(bucket, uuid, file.getInputStream(), objMeta);
        // 등록된 객체의 url 반환 (decoder: url 안의 한글or특수문자 깨짐 방지)
        String fullPath = URLDecoder.decode(amazonS3.getUrl(bucket, uuid).toString(), "utf-8");

        Image image = new Image(fullPath, uuid, file.getOriginalFilename());

        log.info("fullPath={} \nuuid={} \nfileName = {} ", fullPath, uuid, file.getOriginalFilename());
        imageMapper.insertImageInfo(image);

        return image;
    }

    public void deleteImage(String id) {

        try {
            // deleteObject(버킷명, 키값)으로 객체 삭제
            amazonS3.deleteObject(bucket, imageMapper.findImageByUuid(id));
        } catch (AmazonServiceException e) {
            log.error(e.toString());
        }

        log.info("deleteImage={}", imageMapper.findImageByUuid(id));
        imageMapper.deleteImage(id);

    }
}
