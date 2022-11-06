package ch.heigvd.amt.team10.cloud;

import java.io.File;

/**
 * Defines a object storage manager
 *
 * @author Nicolas Crausaz
 * @author Maxime Scharwath
 */
public interface IDataObjectHelper {

    /**
     * Create bucket with unique name
     *
     * @param bucketName bucket unique name,
     */
    void createBucket(String bucketName);

    /**
     * Get an object stored on an object storage manager
     *
     * @param objectName name of object
     * @return object as byte array
     */
    byte[] get(String objectName);

    /**
     * Create object (file) on an object storage manager
     *
     * @param objectName name of object
     * @param file       file to upload
     */
    void create(String objectName, File file);

    /**
     * Create object (string) on an object storage manager
     *
     * @param objectName name of object
     * @param rawContent content to upload
     */
    void create(String objectName, String rawContent);

    /**
     * Update object on an object storage manager
     *
     * @param objectName name of object
     * @param newFile    file to upload
     */
    void update(String objectName, File newFile);

    /**
     * Delete object on an object storage manager
     *
     * @param objectName name of object
     */
    void delete(String objectName);

    /**
     * Get a private URL to an object
     *
     * @param objectName name of object
     * @return URL to access object
     */
    String publish(String objectName);
}
