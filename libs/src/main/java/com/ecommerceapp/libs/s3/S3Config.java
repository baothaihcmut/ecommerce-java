package com.ecommerceapp.libs.s3;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
@ConditionalOnProperty(name = "aws.s3.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class S3Config {
        private final S3Properties s3Properties;

        @Bean
        S3Client s3Client() {
                return S3Client.builder()
                                .region(Region.of(s3Properties.getRegion()))
                                .credentialsProvider(StaticCredentialsProvider
                                                .create(AwsBasicCredentials.create(s3Properties.getAccessKey(),
                                                                s3Properties.getSecretKey())))
                                .build();
        }

        @Bean
        S3Presigner s3Presigner() {
                return S3Presigner.builder()
                                .region(Region.of(s3Properties.getRegion()))
                                .credentialsProvider(StaticCredentialsProvider
                                                .create(AwsBasicCredentials.create(s3Properties.getAccessKey(),
                                                                s3Properties.getSecretKey())))
                                .build();
        }
}
