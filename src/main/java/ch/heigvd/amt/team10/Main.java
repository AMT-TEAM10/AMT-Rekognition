package ch.heigvd.amt.team10;

import ch.heigvd.amt.team10.cloud.AWSClient;
import ch.heigvd.amt.team10.cloud.Label;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Application entrypoint
 *
 * @author Nicolas Crausaz
 * @author Maxime Scharwath
 */
public class Main {
    /**
     * Sample code for API usage
     * Gets a local image, perform a Rekognition analysis and push image with result on S3 bucket.
     */
    public static void main(String[] args) throws IOException {
        AWSClient aws = AWSClient.getInstance();

        // Push file to bucket
        aws.dataObject().create("main/main.jpeg", new File("main.jpeg"));

        ByteBuffer imageBytes;
        try (InputStream inputStream = new FileInputStream("main.jpeg")) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }

        // Analysis
        var labels = aws.labelDetector().execute(imageBytes, 20, 0.5f);

        // Push analysis result as JSON
        aws.dataObject().create("main/result.json", Label.toJSONArray(labels));
    }
}