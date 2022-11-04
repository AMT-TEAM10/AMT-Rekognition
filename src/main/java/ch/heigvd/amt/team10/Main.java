package ch.heigvd.amt.team10;

import ch.heigvd.amt.team10.cloud.AWSClient;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AWSClient aws = AWSClient.getInstance();

        // Push file to bucket
        aws.dataObject().create("main/main.jpeg", new File("main.jpeg"));
        // Get link to bucket file
        String link = aws.dataObject().publish("main/main.jpeg");

        // Analyse
        var labels = aws.labelDetector().executeFromURL(link, 20, 0.5f);

        // TODO: Get raw json response to push to S3

        for (var label : labels) {
            System.out.println(label);
        }

        // push result (as json)
        aws.dataObject().create("main/result.json", "TODO");
    }
}