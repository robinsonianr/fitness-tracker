package com.robinsonir.fittrack.s3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
class S3ServiceTest {

    private S3Service s3Service;

    @Mock
    private S3Client s3Client;


    @BeforeEach
    void setUp() {
        s3Client = mock(S3Client.class);
        s3Service = new S3Service(s3Client);
    }

    @Test
    void testPutObject() {
        String bucketName = "example-bucket";
        String key = "example-key";
        byte[] fileData = "Hello, S3!".getBytes();

        // Mock the S3Client behavior
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(PutObjectResponse.builder().build());

        // Call the putObject method
        s3Service.putObject(bucketName, key, fileData);

        ArgumentCaptor<PutObjectRequest> putObjectRequestCaptor = ArgumentCaptor.forClass(PutObjectRequest.class);

        // Verify that the S3Client's putObject method was called with the expected arguments
        verify(s3Client).putObject(putObjectRequestCaptor.capture(), any(RequestBody.class));

        // Retrieve the captured PutObjectRequest
        PutObjectRequest capturedPutObjectRequest = putObjectRequestCaptor.getValue();

        // Verify that the captured request has the expected bucket and key
        assertEquals(bucketName, capturedPutObjectRequest.bucket());
        assertEquals(key, capturedPutObjectRequest.key());
    }

    @Test
    void testGetObject() throws IOException {
        String bucketName = "example-bucket";
        String key = "example-key";
        byte[] expectedData = "Hello, S3!".getBytes();

        // Mock the S3Client behavior for getObject
        @SuppressWarnings("unchecked")
        ResponseInputStream<GetObjectResponse> res = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(res);
        when(res.readAllBytes()).thenReturn(expectedData);

        // Call the getObject method
        byte[] actualData = s3Service.getObject(bucketName, key);

        assertThat(expectedData).isEqualTo(actualData);
    }

}