
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import java.util.List;

/*
 *  Copyright Peadar Grant.
 *  All rights reserved.
 */

public class Consumer {

    public static void main(String args[]) {
        
        String QUrl = args[0];
        
        AmazonSQS sqs = AmazonSQSClient.builder().withRegion(Regions.DEFAULT_REGION).build();
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(QUrl)
                .withWaitTimeSeconds(1)
                .withMaxNumberOfMessages(1);

        while(true) {
            
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
            
            receiveMessageRequest.setWaitTimeSeconds(20);
            
            if ( messages.isEmpty() ) {
                System.out.println("no messages");
            }
            
            messages.forEach((message) -> {
                System.out.println("received: " + message.getBody());
                sqs.deleteMessage(
                        new DeleteMessageRequest()
                                .withQueueUrl(QUrl)
                                .withReceiptHandle(message.getReceiptHandle()))
                        ;
            });
        }
    }
}
