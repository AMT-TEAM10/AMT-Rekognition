package ch.heigvd.amt.team10.cloud;

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
        this.creds = AwsBasicCredentials.create(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_ACCESS_KEY"));
    }

    public static AWSClient getInstance() {
        if (instance == null)
            instance = new AWSClient();
        return instance;
    }

    public AWSDataObjectHelper dataObject() {
        // TODO: throw if s3 client not init
        return dataObjectHelper;
    }

    public AWSLabelDetectorHelper labelDetector() {
        // TODO: throw if s3 client not init
        return labelDetectorHelper;
    }

    public void setRegion(Region region) {
        this.region = region;
        this.s3 = S3Client.builder().region(region).build();
    }
}
