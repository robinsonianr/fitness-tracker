package com.robinsonir.fitnesstracker.s3;

import com.robinsonir.fitnesstracker.decrypt.EnvFileDecrypter;
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

    private final EnvFileDecrypter fileDecrypter = new EnvFileDecrypter();

    @Value("${aws_access_key_id}")
    private String backupKeyId;

    @Value("${aws_secret_access_key}")
    private String backupSecretKey;

    @Value("${s3.region.name}")
    private String s3RegionName;

    @Bean
    public S3Client s3Client() throws Exception {
        fileDecrypter.decryptFile();
        String accessKeyId = fileDecrypter.variables.get("AWS_ACCESS_KEY_ID") == null ? backupKeyId : fileDecrypter.variables.get("AWS_ACCESS_KEY_ID");
        String accessKeySecret = fileDecrypter.variables.get("AWS_SECRET_ACCESS_KEY") == null ? backupSecretKey : fileDecrypter.variables.get("AWS_SECRET_ACCESS_KEY");

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
