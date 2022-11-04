package ch.heigvd.amt.team10.cloud;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class AWSRekTest {
    @Test
    public void ShouldGetLabelDetector() {
        AWSClient client = AWSClient.getInstance();
        assertNotNull(client.labelDetector());
    }

    @Test
    public void ShouldDetectLabelsFromURL() throws IOException {
        AWSClient client = AWSClient.getInstance();
        var labelDetector = client.labelDetector();
        String exampleUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9d/NYC_Montage_2014_4_-_Jleon.jpg";
        var labels = labelDetector.executeFromURL(exampleUrl, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (var label : labels) {
            assertTrue(label.confidence() >= 0.5f);
        }
    }

    @Test
    public void ShouldDetectLabelsFromBase64() throws IOException {
//        AWSClient client = AWSClient.getInstance();
//        var labelDetector = client.labelDetector();
//        String exampleUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9d/NYC_Montage_2014_4_-_Jleon.jpg";
//        var labels = labelDetector.executeFromURL(exampleUrl, 5, 0.7f);
//        assertEquals(labels.length, 5);
//        for (var label : labels) {
//            assertTrue(label.confidence() >= 0.5f);
//        }
    }
}
