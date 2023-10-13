package com.example.testing.ether;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@RequestMapping("/api/ether")
public class EtherBalanceController {

    private final Web3j web3j = Web3j.build(new HttpService("http://61.79.41.171:8545"));


    @GetMapping("/balance")
    public String getBalance(
            @RequestParam String privateKey

    ) {
        try {

            Credentials credentials = Credentials.create(privateKey);

            String address = credentials.getAddress();

            EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            BigInteger weiBalance = balance.getBalance();

            BigDecimal etherBalance = Convert.fromWei(new BigDecimal(weiBalance), Convert.Unit.ETHER);

            return etherBalance.toString() + "ETH";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
