package dev.learnkafka.library_events_producer.controllers;

import dev.learnkafka.library_events_producer.domain.LibraryEvent;
import dev.learnkafka.library_events_producer.producer.LibraryEventsProducer;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;


@RestController
public class LibraryEventsController {


  private final LibraryEventsProducer libraryEventsProducer;

  public LibraryEventsController(LibraryEventsProducer libraryEventsProducer) {
    this.libraryEventsProducer = libraryEventsProducer;
  }

  @PostMapping(path = "/v1/libraryevent")
  public ResponseEntity<LibraryEvent> postLibraryEvent(
      @RequestBody LibraryEvent libraryEvent
      ) throws JsonProcessingException, InterruptedException, TimeoutException, ExecutionException {

    // libraryEventsProducer.sendLibraryEvent(libraryEvent);

    // libraryEventsProducer.sendLibraryEvent_approach2(libraryEvent);
    
    libraryEventsProducer.sendLibraryEvent_approach3(libraryEvent);


    return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
  }

}
