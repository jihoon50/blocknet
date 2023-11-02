package com.example.testing.post;


import com.example.testing.post.model.PostItemModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;

import java.math.BigInteger;
import java.util.List;
import org.web3j.crypto.Credentials;

@RestController
@RequestMapping("/api/item")
public class PostController {
    private final Web3j web3j = Web3j.build(new HttpService("http://59.19.195.146"));
    Credentials credentials = Credentials.create("0x4f3edf983ac636a65a842ce7c78d9aa706d3b113bce9c46f30d7d21715b23b1d");
    PostContract contract = PostContract.load("0xe78A0F7E598Cc8b0Bb87894B0F60dD2a88d6a8Ab", web3j, credentials, Contract.GAS_PRICE, Contract.GAS_LIMIT);
    @PostMapping("/post")
    public void itemPost(
            @RequestBody PostItemModel item
    ) {
        try {
            BigInteger createAt = BigInteger.valueOf(item.getCreatedAt());
            TransactionReceipt transactionReceipt = contract.createPost(
                            item.getUserId(),
                            item.getUserNickname(),
                            item.getUserPhone(),
                            item.getPostId(),
                            item.getPostImageUrl(),
                            item.getPostTitle(),
                            item.getPostPrice(),
                            item.getPostContent(),
                            createAt
                    )
                    .send();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/get")
    public List<PostContract.Post> getItemList(
            @RequestParam String userId
    ) {
        try {
            Tuple2<List<PostContract.Post>, BigInteger> resultList = contract.getPostsByName(userId).send();
            List<PostContract.Post> postList = resultList.component1();
            BigInteger postNum = resultList.component2();
            return postList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}