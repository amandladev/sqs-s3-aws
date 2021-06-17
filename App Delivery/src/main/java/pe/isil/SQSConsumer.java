package pe.isil;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.google.gson.Gson;
import pe.isil.model.Orden;

import java.io.FileWriter;
import java.util.List;
import java.io.IOException;

import java.io.File;

public class SQSConsumer {

    private static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/022747143274/UserQueue";
    public static void main(String[] args)  throws IOException{
        AWSCredentials credentials = new BasicAWSCredentials("AKIAQKS6V3RVDYFFTNLZ",
                "hYDk+VNIeqrb83VnIlyHD46l01lcpfaEVexzgvIO");

        AmazonSQS sqsClient = AmazonSQSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        ReceiveMessageRequest message = new ReceiveMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(3);

        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();


        final String BUCKET_NAME = "financieraohhg3";

        while (true){

            ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(message);

            if (receiveMessageResult.getSdkHttpMetadata().getHttpStatusCode() != 200){
                System.out.println("Ocurrió un error en la cola "+QUEUE_URL+" -> "+receiveMessageResult.getSdkHttpMetadata());
                return;
            }

            System.out.println("Obteniendo "+receiveMessageResult.getMessages().size()+" msj desde la cola "+QUEUE_URL);



            List<Message> messages = receiveMessageResult.getMessages();
            for (Message msg : messages){
                String body = msg.getBody();
                System.out.println("body = " + body);

                final Gson gson = new Gson();
                final Orden orden = gson.fromJson(body, Orden.class);
                FileWriter fileWriter = new FileWriter("D:/Sergio/CloudEP3 2021/App Delivery/src/main/resources/input/ordenventas.txt");
                fileWriter.write("Nombre: " + orden.getNombre()+ "\r\n") ;
                fileWriter.write("Apellido: " + orden.getApellido() + "\r\n");
                fileWriter.write("Monto: "+ orden.getMonto());
                fileWriter.close();
                System.out.println(orden);
                s3client.putObject(BUCKET_NAME, "ordenVenta/orden.txt", new File("D:/Sergio/CloudEP3 2021/App Delivery/src/main/resources/input/ordenventas.txt"));
                System.out.println("Se ha subido el archivo al bucket " + BUCKET_NAME);

                sqsClient.deleteMessage(QUEUE_URL, msg.getReceiptHandle());
            }

        }


    }

}
