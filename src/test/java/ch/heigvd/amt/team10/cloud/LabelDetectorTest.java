package ch.heigvd.amt.team10.cloud;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

public class LabelDetectorTest {

    private static ICloudClient client;
    private static ILabelDetector detector;

    @BeforeAll
    public static void init() {
        client = AWSClient.getInstance();
        detector = client.labelDetector();
    }

    @Test
    public void shouldGetLabelDetector() {
        assertNotNull(detector);
    }

    @Test
    public void shouldDetectLabelsFromURLString() throws IOException {
        // Given a valid URL string
        String exampleUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9d/NYC_Montage_2014_4_-_Jleon.jpg";

        // When I ask for image analyse with some criteria
        var labels = detector.execute(exampleUrl, 5, 0.7f);

        // Then I should get labels respecting those criteria
        assertEquals(labels.length, 5);
        for (var label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void shouldDetectLabelsFromURL() throws IOException {
        // Given a valid URL object
        URL exampleUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/9/9d/NYC_Montage_2014_4_-_Jleon.jpg");

        // When I ask for image analyse with some criteria
        var labels = detector.execute(exampleUrl, 5, 0.7f);

        // Then I should get labels respecting those criteria
        assertEquals(labels.length, 5);
        for (var label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void shouldDetectLabelsFromBase64() throws IOException {
        // Given a valid image (as bytes)
        ByteBuffer imageBytes;
        try (InputStream inputStream = new FileInputStream("main.jpeg")) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }

        // When I ask for image analyse with some criteria
        var labels = detector.execute(imageBytes, 5, 0.7f);

        // Then I should get labels respecting those criteria
        assertEquals(labels.length, 5);
        for (Label label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void shouldDetectLabelsFromPublishedLink() throws IOException {
        // Given a valid image, from a published link
        client.dataObject().create("main/main.jpeg", new File("main.jpeg"));
        // Get link to bucket file
        String link = client.dataObject().publish("main/main.jpeg");

        // When I ask for image analyse with some criteria
        var labels = detector.execute(link, 5, 0.7f);

        // Then I should get labels respecting those criteria
        assertEquals(labels.length, 5);
        for (Label label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }
}
