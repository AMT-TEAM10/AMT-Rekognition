package ch.heigvd.amt.team10.cloud;

import java.io.File;

public interface IDataObjectHelper {
    void get(String objectName);
    void create(String objectName, File file);
    void update(String objectName, File newFile);
    void delete(String objectName);
    String publish(String objectName);
}
