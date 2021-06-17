package service.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class SQSManager {

    private static final String SQS_URL = "https://sqs.us-east-1.amazonaws.com/022747143274/UserQueue";
    private final AWSCredentials credentials;
    private final AmazonSQS sqsClient;

    public SQSManager(){
        credentials = new BasicAWSCredentials("AKIAQKS6V3RVDYFFTNLZ",
                "hYDk+VNIeqrb83VnIlyHD46l01lcpfaEVexzgvIO");

        sqsClient = AmazonSQSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public void sendOrdenMessage(String message){
        if(!sendMessage(SQS_URL, message)){
            System.err.println("Ocurrió un error al obtener el mensaje de la cola "+ SQS_URL);
        }
        System.out.println("Se envió el mensaje satisfactoriamente a la cola "+SQS_URL);
    }

    private boolean sendMessage(String queueURL, String message){

        SendMessageRequest smrq = new SendMessageRequest(SQS_URL, message);
        SendMessageResult smrs = sqsClient.sendMessage(smrq);

        return smrs.getSdkHttpMetadata().getHttpStatusCode() == 200;
    }

}
