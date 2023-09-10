package com.example.testing.ether;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/gana")
public class GanaBalanceController {

    private final Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/21fe937c5dd94d52b4b3954992cfd18c")); // 가나슈 URL로 대체


    @GetMapping("/balance")
    public String getBalance(
            @RequestParam String ganaaddr
    ) {
        String address = ganaaddr; // 가나슈 계정 주소

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
