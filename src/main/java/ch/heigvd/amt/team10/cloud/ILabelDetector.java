package ch.heigvd.amt.team10.cloud;

import java.io.IOException;

public interface ILabelDetector {
    /**
     *
     * @param imageUri The URI of the image to analyze
     * @param maxLabels The maximum number of labels to return
     * @param minConfidence The minimum confidence required to return a label (0-1)
     * @return An array of labels
     * @throws IOException If the image cannot be downloaded
     */
    Label[] execute(String imageUri, int maxLabels, float minConfidence) throws IOException;
}
