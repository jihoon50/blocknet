package com.example.testing.ether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@RequestMapping("api/ether")
public class EtherSendController {
    private final Web3j web3j = Web3j.build(new HttpService("http://59.22.114.140:8545"));
    //"0xD0aaF45E1d7620A82361F11dd90863be6e31ff69"; 받는사람 공개키
    //"0xb6bd3ded7da77579513e72437885f8aa16ad04abece2c3364e85fec8ee45862a" 받는사람 개인키
    //"0x5de79ea10caeae961f1a6f13efa4ac5912faa8149b1ba057f7c7b118e284fcf3" 보내는사람 개인키



    @PostMapping("/send")
    public String getBalance(
            @RequestParam String receiverPrivateKey,
            @RequestParam  String senderPrivateKey,
            @RequestParam double money
    ) {
        Credentials credentials = Credentials.create(senderPrivateKey);
        BigDecimal amount = BigDecimal.valueOf(money);
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(receiverPrivateKey));
        String walletAddress = Keys.getAddress(ecKeyPair);
        try {
            Transfer.sendFunds(
                    web3j, credentials, walletAddress,
                    amount, Convert.Unit.ETHER
            ).send();
            return "success";
        } catch (Exception e) {
            return "failed";
        }
    }
}
