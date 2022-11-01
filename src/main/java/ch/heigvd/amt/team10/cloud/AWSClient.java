package ch.heigvd.amt.team10.cloud;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class AWSClient implements ICloudClient {
    private AWSDataObjectHelper dataObjectHelper;
    private AWSLabelDetectorHelper labelDetectorHelper;
    private static AWSClient instance;
    private Region region;
    private S3Client s3;
    private AwsBasicCredentials creds;

    private AWSClient() {
        Dotenv dotenv = Dotenv.configure().load();
        this.region = Region.of(dotenv.get("AWS_REGION"));
        this.creds = AwsBasicCredentials.create(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_ACCESS_KEY"));
        this.s3 = S3Client.builder().region(region).build();
    }

    public static AWSClient getInstance() {
        if (instance == null)
            instance = new AWSClient();
        return instance;
    }

    public AWSDataObjectHelper dataObject() {
        return dataObjectHelper;
    }

    public AWSLabelDetectorHelper labelDetector() {
        return labelDetectorHelper;
    }
}
