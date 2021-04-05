package com.vis.server.backend.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInfoController.class);

    @GetMapping(value = "/heartbeat", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> heartbeat() {
        LOGGER.trace("Heartbeat request received");
        return new ResponseEntity<>("alive", HttpStatus.OK);
    }
}
