package ch.heigvd.amt.team10.cloud;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataObjectHelperTest {
    //TODO REVIEW Test only one thing in your test case
    //TODO Add ObjectDoesExist in you code, without this it's complicate to validate your cases.

    private static AWSClient client;

    @BeforeAll
    public static void init() {
        client = AWSClient.getInstance();
    }

    @Test
    public void shouldCreateAndGetObject() throws IOException {
        File originFile = new File("chad.jpg");
        client.dataObject().create("test.jpg", originFile);

        File outputFile = new File("outputFile.jpg");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(client.dataObject().get("test.jpg"));
        outputStream.close();

        assertEquals(Files.mismatch(originFile.toPath(), outputFile.toPath()), -1L);
    }

    @Test
    public void shouldUpdateObject() throws IOException {
        client.dataObject().create("test.jpg", new File("chad.jpg"));
        client.dataObject().update("test.jpg", new File("test.jpg"));

        File originFile = new File("test.jpg");
        client.dataObject().create("test.jpg", originFile);

        File outputFile = new File("outputFile.jpg");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(client.dataObject().get("test.jpg"));
        outputStream.close();

        assertEquals(Files.mismatch(originFile.toPath(), outputFile.toPath()), -1L);
    }

    @Test
    public void shouldDeleteObject() {
        client.dataObject().delete("test.jpg");
        assertThrows(RuntimeException.class, () -> client.dataObject().get("test.jpg"));
    }

    @Test
    public void shouldGetAnUrlWithPublish() throws IOException {
        URL url = new URL(client.dataObject().publish("test.jpg"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        assertEquals(con.getResponseCode(), 200);
    }

    @AfterAll
    static void cleanup() {
        final File toDelete = new File("outputFile.jpg");
        if (toDelete.exists()) {
            toDelete.delete();
        }
    }
}
