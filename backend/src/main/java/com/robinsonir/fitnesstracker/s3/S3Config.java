package com.robinsonir.fitnesstracker.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws_access_key_id}")
    private String accessKeyId;

    @Value("${aws_secret_access_key}")
    private String accessKeySecret;

    @Value("${s3.region.name}")
    private String s3RegionName;

    @Bean
    public S3Client s3Client() {
        AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(
                awsCredentials(accessKeyId, accessKeySecret)
        );

        return S3Client.builder()
                .region(Region.of(s3RegionName))
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }

    private AwsBasicCredentials awsCredentials(String accessKeyId, String accessKeySecret) {
        return AwsBasicCredentials.create(accessKeyId, accessKeySecret);

    }

}
