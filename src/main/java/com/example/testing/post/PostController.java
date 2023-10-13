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

    private final Web3j web3j = Web3j.build(new HttpService("http://61.79.41.171:8545"));

    ObjectMapper objectMapper = new ObjectMapper();

    Credentials credentials = Credentials.create("0x4f3edf983ac636a65a842ce7c78d9aa706d3b113bce9c46f30d7d21715b23b1d");
    //개인키 첫번째거 고정
    PostContract contract = PostContract.load("0xe78A0F7E598Cc8b0Bb87894B0F60dD2a88d6a8Ab",web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT);
    //스마트 컨트랙트 주소


    //Tuple5<String, String, BigInteger, String, String> post = PostContract.getPost(BigInteger.valueOf(1)).send();
    @GetMapping("/get")
    public List<PostContract.Post> getItemList(
            @RequestParam String userId
    ) {
        try {

            Tuple2<List<PostContract.Post>,BigInteger> resultList = contract.getPostsByName(userId).send();
            //게시글 작성자 ID로 조회하는 함수 -> Post List와 포스트 개수 반환 Tuple로

            List<PostContract.Post> postList = resultList.component1();
            //Tuple 에서 post List만 추출

            for (PostContract.Post post : postList) {
                // post를 사용하여 원하는 작업을 수행
                // post.postId 처럼 각각 요소 접근

            }

            //postList.get(0) 사용시 게시글 배열의 0번째 게시글 접근

            BigInteger postNum = resultList.component2();
            //반환된 post 개수
            System.out.println();
            //return postList.get(0).postId.toString();

            return postList;
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/post")
    public void itemPost(
           // @RequestParam String userId
           //@ModelAttribute PostItemModel item
           @RequestBody PostItemModel item
    ) {
        try {
            //게시글 저장할때 전부 String인데 마지막 시간은 BigInteger로 바꿔서 입력
            BigInteger createAt = BigInteger.valueOf(item.getCreatedAt());

            //게시글 블록체인에 저장하는 함수
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

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
