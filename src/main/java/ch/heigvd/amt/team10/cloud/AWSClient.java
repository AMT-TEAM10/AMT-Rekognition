package ch.heigvd.amt.team10.cloud;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class AWSClient implements ICloudClient {
    private final AWSDataObjectHelper dataObjectHelper;
    private AWSLabelDetectorHelper labelDetectorHelper;
    private static AWSClient instance;
    private final Region region;
    private final S3Client s3;
    private AwsBasicCredentials creds;

    private AWSClient() {
        Dotenv dotenv = Dotenv.configure().load();
        this.region = Region.of(dotenv.get("AWS_REGION"));
        this.creds = AwsBasicCredentials.create(dotenv.get("AWS_ACCESS_KEY_ID"), dotenv.get("AWS_SECRET_ACCESS_KEY"));
        this.s3 = S3Client.builder().region(region).build();
        dataObjectHelper = new AWSDataObjectHelper();
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

    public S3Client getClient() {
        return s3;
    }
}
