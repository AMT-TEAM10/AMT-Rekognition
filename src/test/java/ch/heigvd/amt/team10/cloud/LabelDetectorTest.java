package ch.heigvd.amt.team10.cloud;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

public class LabelDetectorTest {

    // TODO Given-When-Then-ifiez vos tests (utilisez une approche BDD)

    // TODO Factorisez vos tests : utilisez les annotations pour du pré/post
    // traitement ainsi que des variables statiques ou d'environnement pour la
    // configuration de vos tests.

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
        String exampleUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9d/NYC_Montage_2014_4_-_Jleon.jpg";
        var labels = detector.execute(exampleUrl, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (var label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void shouldDetectLabelsFromURL() throws IOException {
        URL exampleUrl = new URL("https://upload.wikimedia.org/wikipedia/commons/9/9d/NYC_Montage_2014_4_-_Jleon.jpg");
        var labels = detector.execute(exampleUrl, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (var label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void shouldDetectLabelsFromBase64() throws IOException {
        ByteBuffer imageBytes;
        try (InputStream inputStream = new FileInputStream("main.jpeg")) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }

        var labels = detector.execute(imageBytes, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (Label label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }

    @Test
    public void shouldDetectLabelsFromPusblishedS3Link() throws IOException {
        client.dataObject().create("main/main.jpeg", new File("main.jpeg"));
        // Get link to bucket file
        String link = client.dataObject().publish("main/main.jpeg");

        var labels = detector.execute(link, 5, 0.7f);
        assertEquals(labels.length, 5);
        for (Label label : labels) {
            assertTrue(label.confidence() >= 0.7f);
        }
    }
}
