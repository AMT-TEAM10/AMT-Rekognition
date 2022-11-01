package ch.heigvd.amt.team10.cloud;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class AWSS3Test {
    @Test
    public void shouldCreateAndGetObject() throws IOException {
        AWSClient client = AWSClient.getInstance();
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
        AWSClient client = AWSClient.getInstance();
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
        AWSClient client = AWSClient.getInstance();
        client.dataObject().delete("test.jpg");
        assertThrows(RuntimeException.class, () -> client.dataObject().get("test.jpg"));
    }

    @Test
    public void shouldGetAnUrlWithPublish() {
        AWSClient client = AWSClient.getInstance();
        System.out.println(client.dataObject().publish("test.jpg"));
    }

    @AfterAll
    static void cleanup(){
        new File("outputFile.jpg").delete();
    }
}
