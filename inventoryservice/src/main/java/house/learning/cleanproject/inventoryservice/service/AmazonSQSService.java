package house.learning.cleanproject.inventoryservice.service;

import house.learning.cleanproject.inventoryservice.configurations.AwsProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AmazonSQSService {

    private final S3Client s3Client;
    private final SqsClient sqsClient;
    private final AwsProperties awsProperties;

    public String sendMessage(String queue, String text) {
        SendMessageRequest messageRequest = SendMessageRequest.builder()
                .queueUrl(queue)
                .messageBody(text)
                .build();
       return  sqsClient.sendMessage(messageRequest).messageId();
    }
    public String createQueue(String bucketName) {
        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(bucketName)
                .build();

        try {
             CreateQueueResponse queueResponde =  sqsClient.createQueue(createQueueRequest);
            log.info("Queue created: {}", createQueueRequest.queueName());
            return queueResponde.queueUrl();
        } catch (S3Exception e) {
            log.error("Error creating Queue: {}", e.awsErrorDetails().errorMessage());
        }
        return null;
    }

    public String receiveMessages(String queueUrl, Boolean withDelete) {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .build();

        List<Message> receivedMessages =  sqsClient.receiveMessage(receiveMessageRequest).messages();

        String messages = "";


            for (Message receivedMessage : receivedMessages) {
                messages += receivedMessage.body() + "\n";
                if (withDelete) {
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(receivedMessage.receiptHandle())
                        .build();
                sqsClient.deleteMessage(deleteMessageRequest);
            }
        }
        return messages;
    }

    public List<String> listQueues() {
        return sqsClient.listQueues().queueUrls();
    }

    public void deleteQueue(String bucketName) {
        DeleteQueueRequest deleteQueueRequest = DeleteQueueRequest.builder()
                .queueUrl(bucketName)
                .build();

        try {
            sqsClient.deleteQueue(deleteQueueRequest);
            log.info("Queue deleted: {}", deleteQueueRequest.queueUrl());
        } catch (S3Exception e) {
            log.error("Error deleting Queue: {}", e.awsErrorDetails().errorMessage());
        }
    }

}