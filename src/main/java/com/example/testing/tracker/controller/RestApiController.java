package com.example.testing.tracker.controller;


import com.example.testing.tracker.service.TrackingService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("open-api/rest")
@Slf4j
public class RestApiController {
    private final TrackingService trackingService;

    @GetMapping("/tracking/state")
    public String currentState(
            @RequestParam String t_code,
            @RequestParam String t_invoice

    ){
        var response = trackingService.getTrackingInfo(t_code, t_invoice);
        var resBody = response.getBody();
        JsonObject jsonObject = new JsonParser().parse(resBody).getAsJsonObject();
        var curTrackingState = jsonObject
                .getAsJsonObject("lastStateDetail")
                .get("kind")
                .getAsString();
        System.out.println(curTrackingState);
        return curTrackingState;

    }
    @GetMapping("/tracking")
    public ResponseEntity<String> tracking(
            @RequestParam String t_code,
            @RequestParam String t_invoice
    ){
        var response = trackingService.getTrackingInfo(t_code, t_invoice);

        return response;

    }




}
