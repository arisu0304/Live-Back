package com.bit.boardappbackend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/ncloud")
public class NcloudChatController {
    private final RestTemplate restTemplate;

    @Value("${ncloud.api.token}")  // application.yml이나 properties에 설정
    private String apiToken;

    public NcloudChatController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<String> getNcloudProjectData(@PathVariable String projectId) {
        String url = "https://dashboard-api.ncloudchat.naverncp.com/v1/api/project/" + projectId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data from Ncloud Chat API");
        }
    }
}
