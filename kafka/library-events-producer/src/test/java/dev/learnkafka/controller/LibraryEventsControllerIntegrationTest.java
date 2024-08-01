package dev.learnkafka.controller;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import dev.learnkafka.library_events_producer.domain.LibraryEvent;
import dev.learnkafka.util.TestUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LibraryEventsControllerIntegrationTest {


  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void postLibraryEvent() {
    // given

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Contenty-Type", MediaType.APPLICATION_JSON.toString());
    var httpEntity = new HttpEntity<>(TestUtil.libraryEventRecord(), httpHeaders);

    // when
    ResponseEntity<LibraryEvent> resposneEntity = restTemplate.exchange("/v1/libraryevents", HttpMethod.POST, httpEntity, LibraryEvent.class);

    // then
    assertEquals(HttpStatus.CREATED, resposneEntity.getStatusCode());
  }

}
