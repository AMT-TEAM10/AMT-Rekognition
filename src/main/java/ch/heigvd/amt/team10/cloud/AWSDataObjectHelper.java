package ch.heigvd.amt.team10.cloud;

import ch.heigvd.amt.team10.Env;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.File;
import java.time.Duration;

public class AWSDataObjectHelper implements IDataObjectHelper {

    @Override
    public byte[] get(String objectName) {
        GetObjectRequest objectRequestGet = GetObjectRequest
                .builder()
                .bucket(Env.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();

        ResponseBytes<GetObjectResponse> result;

        try {
            result = AWSClient.getInstance().getS3Client().getObjectAsBytes(objectRequestGet);
        } catch (Exception e) {
            throw new RuntimeException("Key not found");
        }

        return result.asByteArray();
    }

    @Override
    public void create(String objectName, File file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(Env.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();
        AWSClient.getInstance().getS3Client().putObject(objectRequest, RequestBody.fromFile(file));
    }

    @Override
    public void create(String objectName, String rawContent) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(Env.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();
        AWSClient.getInstance().getS3Client().putObject(objectRequest, RequestBody.fromString(rawContent));
    }

    @Override
    public void update(String objectName, File newFile) {
        create(objectName, newFile);
    }

    @Override
    public void delete(String objectName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(Env.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();

        AWSClient.getInstance().getS3Client().deleteObject(request);
    }

    @Override
    public String publish(String objectName) {
        S3Presigner presigner = S3Presigner.create();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(Env.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(getObjectPresignRequest);
        return presignedGetObjectRequest.url().toString();
    }
}
