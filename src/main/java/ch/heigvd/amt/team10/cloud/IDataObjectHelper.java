package ch.heigvd.amt.team10.cloud;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;

public interface IDataObjectHelper {
    byte[] get(String objectName);
    void create(String objectName, File file);
    void update(String objectName, File newFile);
    void delete(String objectName);
    String publish(String objectName);
}
