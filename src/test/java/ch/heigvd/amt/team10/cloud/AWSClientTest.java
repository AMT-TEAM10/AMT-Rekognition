package ch.heigvd.amt.team10.cloud;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;

import java.io.File;

public class AWSClientTest {
    @Test
    public void ShouldCreateObject() {
        AWSClient client = AWSClient.getInstance();
        client.setRegion(Region.EU_WEST_2);
        client.dataObject().create("test", new File("./chad.jpg"));
    }

    @Test
    public void ShouldGetObject() {
        // TODO
    }

    @Test
    public void ShouldUpdateObject() {
        // TODO
    }

    @Test
    public void ShouldDeleteObject() {
        // TODO
    }
}
