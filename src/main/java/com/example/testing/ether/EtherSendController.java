package com.example.testing.ether;

import com.example.testing.ether.model.EtherSendModel;
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
    private final Web3j web3j = Web3j.build(new HttpService("http://59.19.195.146:8545"));
    @PostMapping("/send")
    public String getBalance(
            @RequestBody EtherSendModel model
            ) {
        Credentials credentials = Credentials.create(model.getSenderPrivateKey());
        BigDecimal amount = BigDecimal.valueOf(model.getMoney());
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(model.getReceiverPrivateKey()));
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
