package ch.heigvd.amt.team10.cloud;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;

import java.io.File;

public class AWSClientTest {
    @Test
    public void ShouldCreateObject() {
        AWSClient client = AWSClient.getInstance();
        client.dataObject().create("test", new File("./chad.jpg"));
    }

    @Test
    public void ShouldGetObject() {
        AWSClient client = AWSClient.getInstance();
        client.dataObject().get("test");
    }

    @Test
    public void ShouldUpdateObject() {
        AWSClient client = AWSClient.getInstance();
        client.dataObject().update("test", new File("./chad.jpg"));
    }

    @Test
    public void ShouldDeleteObject() {
        AWSClient client = AWSClient.getInstance();
        client.dataObject().delete("test");
    }
}
