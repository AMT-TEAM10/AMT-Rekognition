package ch.heigvd.amt.team10.cloud;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
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
        var labels = labelDetector.execute(exampleUrl, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (var label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void ShouldDetectLabelsFromBase64() throws IOException {
        AWSClient client = AWSClient.getInstance();
        AWSLabelDetectorHelper labelDetector = client.labelDetector();

        ByteBuffer imageBytes;
        try (InputStream inputStream = new FileInputStream("main.jpeg")) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }

        var labels = labelDetector.execute(imageBytes, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (Label label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void shouldDetectLabelsFromPusblishedS3Link() throws IOException {
        AWSClient client = AWSClient.getInstance();
        AWSLabelDetectorHelper labelDetector = client.labelDetector();

        client.dataObject().create("main/main.jpeg", new File("main.jpeg"));
        // Get link to bucket file
        String link = client.dataObject().publish("main/main.jpeg");

        var labels = labelDetector.execute(link, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (Label label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }
}
