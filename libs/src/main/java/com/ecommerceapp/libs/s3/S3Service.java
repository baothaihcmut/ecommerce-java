package com.ecommerceapp.libs.s3;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class S3Service {
    private final S3Properties s3Properties;
    private final S3Presigner s3Presigner;

    public String generatePresignUrlForGet(
            String key,
            int duration,
            MediaType contentType) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(key)
                .responseContentType(contentType.getType())
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(duration))
                .getObjectRequest(getObjectRequest)
                .build();
        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }

    public String generatePresignUrlForPut(
            String key,
            int duration,
            MediaType contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(key)
                .contentType(contentType.getType())
                .build();
        PutObjectPresignRequest presignedPutObjectRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(duration))
                .putObjectRequest(putObjectRequest)
                .build();
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignedPutObjectRequest);
        return presignedRequest.url().toString();

    }
}
