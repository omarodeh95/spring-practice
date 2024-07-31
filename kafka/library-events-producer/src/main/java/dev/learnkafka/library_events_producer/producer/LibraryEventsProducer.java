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

  public CompletableFuture<SendResult<Integer, String>> sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {
    var key =  libraryEvent.libraryEventId();
    var value = objectMapper.writeValueAsString(libraryEvent);
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


