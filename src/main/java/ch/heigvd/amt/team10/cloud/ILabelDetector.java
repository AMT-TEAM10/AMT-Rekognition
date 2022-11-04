package ch.heigvd.amt.team10.cloud;

import software.amazon.awssdk.services.rekognition.model.Image;

import java.io.IOException;
import java.net.URL;

public interface ILabelDetector {
    /**
     *
     * @param imageUri The URI of the image to analyze
     * @param maxLabels The maximum number of labels to return
     * @param minConfidence The minimum confidence required to return a label (0-1)
     * @return An array of labels
     * @throws IOException If the image cannot be downloaded
     */
    Label[] executeFromURL(String imageUri, int maxLabels, float minConfidence) throws IOException;

    Label[] executeFromURL(URL imageUri, int maxLabels, float minConfidence) throws IOException;

    Label[] executeFromBase64(String base64Image, int maxLabels, float minConfidence) throws IOException;
}
