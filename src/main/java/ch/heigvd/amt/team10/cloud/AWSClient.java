package ch.heigvd.amt.team10.cloud;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.log4j.BasicConfigurator;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.s3.S3Client;

public class AWSClient implements ICloudClient {
    private final AWSDataObjectHelper dataObjectHelper;
    private final AWSLabelDetectorHelper labelDetectorHelper = new AWSLabelDetectorHelper();
    private static AWSClient instance;
    private final Region region;
    private final S3Client s3Client;

    private final RekognitionClient rekognitionClient;
    private final AwsCredentialsProvider credentialsProvider;

    private AWSClient() {
        BasicConfigurator.configure();
        Dotenv dotenv = Dotenv.configure().load();
        this.region = Region.of(dotenv.get("AWS_REGION"));
        this.credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(dotenv.get("AWS_ACCESS_KEY_ID"), dotenv.get("AWS_SECRET_ACCESS_KEY")));
        this.s3Client = S3Client.builder().region(region).build();
        this.rekognitionClient = RekognitionClient.builder().credentialsProvider(this.getCredentials()).region(this.getRegion()).build();
        dataObjectHelper = new AWSDataObjectHelper();
    }

    public static AWSClient getInstance() {
        if (instance == null)
            instance = new AWSClient();
        return instance;
    }

    public Region getRegion() {
        return region;
    }

    public AwsCredentialsProvider getCredentials() {
        return credentialsProvider;
    }

    public AWSDataObjectHelper dataObject() {
        return dataObjectHelper;
    }

    public AWSLabelDetectorHelper labelDetector() {
        return labelDetectorHelper;
    }

    public S3Client getS3Client() {
        return s3Client;
    }

    public RekognitionClient getRekognitionClient() {
        return rekognitionClient;
    }
}
