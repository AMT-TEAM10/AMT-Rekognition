package ch.heigvd.amt.team10.cloud;

import java.io.File;

/**
 * Defines a object storage manager
 *
 * @author Nicolas Crausaz
 * @author Maxime Scharwath
 */
public interface IDataObjectHelper {
    void createBucket(String bucketName);

    byte[] get(String objectName);

    void create(String objectName, File file);

    void create(String objectName, String rawContent);

    void update(String objectName, File newFile);

    void delete(String objectName);

    String publish(String objectName);
}
