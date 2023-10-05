package com.example.testing.tracker.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class TrackingService {
    private final String BASE_URL = "http://info.sweettracker.co.kr";

    private final RestTemplate restTemplate;

    public ResponseEntity<String> getTrackingInfo(String t_code, String t_invoice){
        URI apiUrl = UriComponentsBuilder
                .fromUriString(BASE_URL)
                .path("/api/v1/trackingInfo")
                .queryParam("t_code", t_code)
                .queryParam("t_invoice",t_invoice)
                .queryParam("t_key","DvjPwRW2gMPfap6GUq2Dtw") // 11/06 key expiration
                .encode()
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        return response;
    }

}
