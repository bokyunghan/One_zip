package com.sh.onezip.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sh.onezip.member.dto.MemberProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3FileServices {
    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public MemberProfileDto upload(MultipartFile upfile) throws IOException {
        String profileKey = UUID.randomUUID().toString();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(upfile.getContentType());
        objectMetadata.setContentLength(upfile.getSize());

        amazonS3Client.putObject(bucket, profileKey, upfile.getInputStream(), objectMetadata);

        String profileUrl = amazonS3Client.getUrl(bucket, profileKey).toString();

        return new MemberProfileDto(profileUrl, profileKey);
    }
}
