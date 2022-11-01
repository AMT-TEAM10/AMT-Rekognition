package ch.heigvd.amt.team10.cloud;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AWSLabelDetectorHelper implements ILabelDetector {
    @Override
    public Label[] execute(String imageUri, int maxLabels, float minConfidence) throws IOException {
        var client = AWSClient.getInstance().getRekognitionClient();

        SdkBytes sourceBytes = SdkBytes.fromInputStream(downloadImage(imageUri));
        Image srcImage = Image.builder()
                .bytes(sourceBytes)
                .build();

        DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
                .image(srcImage)
                .maxLabels(maxLabels)
                .minConfidence(minConfidence * 100f)
                .build();

        DetectLabelsResponse labelsResponse = client.detectLabels(detectLabelsRequest);
        var awsLabels = labelsResponse.labels();

        Label[] result = new Label[awsLabels.size()];
        for (int i = 0; i < awsLabels.size(); i++) {
            result[i] = new Label(awsLabels.get(i).name(), awsLabels.get(i).confidence() / 100.0f);
        }
        return result;
    }

    private InputStream downloadImage(String imageUri) throws IOException {
        URL url = new URL(imageUri);
        return new BufferedInputStream(url.openStream());
    }
}
