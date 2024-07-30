package learnkafka.controllers;

import dev.learnkafka.domain.LibraryEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class LibraryEventsController {

  @PostMapping("/v1/libraryevents")
  public ResponseEntity<LibraryEvent> postLibraryEvent(
      @RequestBody LibraryEvent libraryEvent
      ) {

    // invoke kafka producer


    return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
  }
}
