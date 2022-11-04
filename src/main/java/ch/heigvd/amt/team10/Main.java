package ch.heigvd.amt.team10;

import ch.heigvd.amt.team10.cloud.AWSClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var aws = AWSClient.getInstance();
        var labels = aws.labelDetector().execute("https://www.pbs.org/independentlens/wp-content/uploads/2020/09/pepe-playing-game-FGM.jpg", 50 , 0f);
        for (var label : labels) {
            System.out.println(label);
        }
    }
}