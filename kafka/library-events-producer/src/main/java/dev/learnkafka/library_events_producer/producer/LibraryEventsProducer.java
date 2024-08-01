package dev.learnkafka.library_events_producer.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import dev.learnkafka.library_events_producer.domain.LibraryEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


@Component
@Slf4j
public class LibraryEventsProducer {

  private KafkaTemplate<Integer, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Value("${spring.kafka.topic}")
  public String topic;
  // Kafka is injected
  public LibraryEventsProducer (KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  // Approach 2
  public SendResult<Integer, String> sendLibraryEvent_approach2(LibraryEvent libraryEvent) throws ExecutionException, InterruptedException, JsonProcessingException, TimeoutException {
    var key =  libraryEvent.libraryEventId();
    var value = objectMapper.writeValueAsString(libraryEvent);

    // Synchronously
    // first a blocking call happens only for the first time to get meta data about the cluster
    // block and wait until the message is sent to kafka
    var sendResult = kafkaTemplate.send(topic, key, value).get(3, TimeUnit.SECONDS);

    handleSuccess(key, value, sendResult);

    return sendResult;

  }

  // Approach 1
  public CompletableFuture<SendResult<Integer, String>> sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
    var key =  libraryEvent.libraryEventId();
    var value = objectMapper.writeValueAsString(libraryEvent);

    // Asynchronously
    // first a blocking call happens only for the first time to get meta data about the cluster
    // second  it send the message
    var completableFuture = kafkaTemplate.send(topic, key, value);

    return completableFuture.whenComplete((sendResult, throwable ) -> {
      if (throwable != null) {
        handleFailure(key, value, throwable);
      } else {
        handleSuccess(key, value, sendResult);
      }
    });
  }

  private void handleFailure(Integer key, String value, Throwable throwable) {
    // Use the SLF4J logger for logging
    log.error("Error sending the message with key {} and value {}: {}", key, value, throwable.getMessage(), throwable);
  }

  private void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {
    // Use the SLF4J logger for logging
    log.info("Message sent successfully with key {} and value {}: partition is {}", key, value, sendResult.getRecordMetadata().partition());
  }
}


