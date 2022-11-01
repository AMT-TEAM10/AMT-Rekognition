package ch.heigvd.amt.team10.cloud;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;

class AWSDataObjectHelper implements IDataObjectHelper {

    @Override
    public void get(String objectName) {
        Dotenv dotenv = Dotenv.configure().load();
        GetObjectRequest objectRequestGet = GetObjectRequest
                .builder()
                .bucket(dotenv.get("NAME"))
                .key(objectName)
                .build();
    }

    @Override
    public void create(String objectName, File file) {
        // HashMap<String, String> metadata = new HashMap<>();
        // metadata.put("Content-Type", "image/png");
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket("amt.team10.diduno.education")
                .key(objectName)
                // .metadata(metadata)
                .build();

        // RequestBody.
        // s3.putObject(objectRequest, RequestBody.fromFile(file));
    }

    @Override
    public void update(String objectName) {

    }

    @Override
    public void delete(String objectName) {

    }
}
