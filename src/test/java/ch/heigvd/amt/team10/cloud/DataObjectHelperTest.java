package ch.heigvd.amt.team10.cloud;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class DataObjectHelperTest {
    //TODO REVIEW Test only one thing in your test case
    //TODO Add ObjectDoesExist in you code, without this it's complicate to validate your cases.

    private static AWSClient client;

    private static String EXISTING_OBJECT_KEY = "existingObject";
    private static String OBJECT_CAN_BE_CREATED_KEY = "objectCanBeCreated";

    @BeforeAll
    public static void init() {
        client = AWSClient.getInstance();
    }

    @BeforeEach
    public void setup() {
        if (!client.dataObject().objectExists(EXISTING_OBJECT_KEY)) {
            client.dataObject().create(EXISTING_OBJECT_KEY, "existingObject");
        }
        if (client.dataObject().objectExists(OBJECT_CAN_BE_CREATED_KEY)) {
            client.dataObject().delete(OBJECT_CAN_BE_CREATED_KEY);
        }
    }

    @Test
    public void shouldVerifyIfObjectExist() {
        assertTrue(client.dataObject().objectExists(EXISTING_OBJECT_KEY));
    }

    @Test
    public void shouldVerifyIfObjectDoesNotExist() {
        assertFalse(client.dataObject().objectExists("thisObjectDoesNotExist"));
    }

    @Test
    public void shouldCreateAndGetObject() throws IOException {
        File originFile = new File("chad.jpg");
        client.dataObject().create(OBJECT_CAN_BE_CREATED_KEY, originFile);

        File outputFile = new File("outputFile.jpg");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(client.dataObject().get(OBJECT_CAN_BE_CREATED_KEY));
        outputStream.close();

        assertEquals(Files.mismatch(originFile.toPath(), outputFile.toPath()), -1L);
    }

    @Test
    public void shouldUpdateObject() throws IOException {
        client.dataObject().create(OBJECT_CAN_BE_CREATED_KEY, new File("chad.jpg"));
        client.dataObject().update(OBJECT_CAN_BE_CREATED_KEY, new File("test.jpg"));

        File originFile = new File("test.jpg");
        client.dataObject().create(OBJECT_CAN_BE_CREATED_KEY, originFile);

        File outputFile = new File("outputFile.jpg");
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(client.dataObject().get(OBJECT_CAN_BE_CREATED_KEY));
        outputStream.close();

        assertEquals(Files.mismatch(originFile.toPath(), outputFile.toPath()), -1L);
    }

    @Test
    public void shouldDeleteObject() {
        client.dataObject().delete(EXISTING_OBJECT_KEY);
        assertThrows(RuntimeException.class, () -> client.dataObject().get(OBJECT_CAN_BE_CREATED_KEY));
    }

    @Test
    public void shouldThrowWhenDeleteInexistantObject() {
        assertThrows(RuntimeException.class, () -> client.dataObject().delete("thisObjectDoesNotExist.jpg"));
    }

    @Test
    public void shouldGetAnUrlWithPublish() throws IOException {
        URL url = new URL(client.dataObject().publish(EXISTING_OBJECT_KEY));
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
