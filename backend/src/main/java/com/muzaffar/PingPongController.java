package com.muzaffar;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 12/12/24.
 */

@RestController
public class PingPongController {

    private static int COUNTER = 0;
    record PingPong(String result) {}

    @GetMapping("/ping")
    public PingPong getPIngPOng() {

        return new PingPong("oi, Pong " + ++COUNTER + " damn it, PONG! UGHHH! Last Time! I PROMISE! ");
    }
}
