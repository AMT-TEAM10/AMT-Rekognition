package ch.heigvd.amt.team10.cloud;

public class AWSClient implements ICloudClient {
    private AWSClient instance;

    public AWSClient getInstance() {
        if (instance == null)
            instance = new AWSClient();
        return instance;
    }
}
