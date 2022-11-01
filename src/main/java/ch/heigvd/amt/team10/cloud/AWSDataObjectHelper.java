package ch.heigvd.amt.team10.cloud;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;

class AWSDataObjectHelper implements IDataObjectHelper {

    final Dotenv config = Dotenv.configure().load();

    @Override
    public byte[] get(String objectName) {
        GetObjectRequest objectRequestGet = GetObjectRequest
                .builder()
                .bucket(config.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();

        ResponseBytes<GetObjectResponse> result;

        try {
            result = AWSClient.getInstance().getClient().getObjectAsBytes(objectRequestGet);
        } catch (Exception e) {
            throw new RuntimeException("Key not found");
        }

        return result.asByteArray();
    }

    @Override
    public void create(String objectName, File file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(config.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();
        AWSClient.getInstance().getClient().putObject(objectRequest, RequestBody.fromFile(file));
    }

    @Override
    public void update(String objectName, File newFile) {
        create(objectName, newFile);
    }

    @Override
    public void delete(String objectName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(config.get("AWS_BUCKET_NAME"))
                .key(objectName)
                .build();

        AWSClient.getInstance().getClient().deleteObject(request);
    }

    @Override
    public String publish(String objectName) {
        return null;
    }
}
