package com.example.testing.ether;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/ether")
public class EtherBalanceController {

    private final Web3j web3j = Web3j.build(new HttpService("http://59.22.114.140:8545"));


    @GetMapping("/balance")
    public String getBalance(
            @RequestParam String privateKey
    ) {
        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
        String address = Keys.getAddress(ecKeyPair);
        try {
            EthGetBalance balanceResponse = web3j.ethGetBalance(address, null).send();
            BigDecimal weiBalance = new BigDecimal(balanceResponse.getBalance());
            BigDecimal etherBalance = Convert.fromWei(weiBalance, Convert.Unit.ETHER);
            return etherBalance.toPlainString() + " ETH";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error fetching balance";
        }
    }
}
