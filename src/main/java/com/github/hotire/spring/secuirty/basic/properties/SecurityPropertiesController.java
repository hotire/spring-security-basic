package com.github.hotire.spring.secuirty.basic.properties;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SecurityPropertiesController {

    @GetMapping("/v1/user")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("hello");
    }

    @PatchMapping("/v1/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("hello");
    }
}
