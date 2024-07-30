package dev.learnkafka.library_events_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class LibraryEventsProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryEventsProducerApplication.class, args);
	}

  // FIXME: This following could shoe be auto scaned when loaded from the AuthorCreateConfiguration file, but for some reason it wasn't wokring
  @Value("${spring.kafka.topic}")
  private String topic;

  @Bean
  public NewTopic libraryEvents() {
    return TopicBuilder.name(topic).partitions(3).replicas(3).build();
  }

}
