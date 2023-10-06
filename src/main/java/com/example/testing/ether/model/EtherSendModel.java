package com.example.testing.ether.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EtherSendModel {

    private String receiverPrivateKey;
    private String senderPrivateKey;
    private double money;
}
