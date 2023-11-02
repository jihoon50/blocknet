package com.example.testing.review;


import com.example.testing.post.model.PostItemModel;
import com.example.testing.review.model.ReviewModel;
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
@RequestMapping("/api/review")

public class ReviewController {

    private final Web3j web3j = Web3j.build(new HttpService("http://59.19.195.146"));


    Credentials credentials = Credentials.create("0x4f3edf983ac636a65a842ce7c78d9aa706d3b113bce9c46f30d7d21715b23b1d");
    //개인키 첫번째거 고정
    ReviewContract contract = ReviewContract.load("0x5b1869D9A4C187F2EAa108f3062412ecf0526b24",web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT);
    //스마트 컨트랙트 주소


    //Tuple5<String, String, BigInteger, String, String> post = PostContract.getPost(BigInteger.valueOf(1)).send();
    @GetMapping("/get")
    public List<ReviewContract.Post> getReviewList(
            @RequestParam String userId
    ) {
        try {

            Tuple2<List<ReviewContract.Post>,BigInteger> resultList = contract.getReviewsByName(userId).send();
            //게시글 작성자 ID로 조회하는 함수 -> Post List와 포스트 개수 반환 Tuple로

            List<ReviewContract.Post> reviewList = resultList.component1();
            //Tuple 에서 post List만 추출

//            for (PostContract.Post post : postList) {
//                // post를 사용하여 원하는 작업을 수행
//                // post.postId 처럼 각각 요소 접근
//            }

            //postList.get(0) 사용시 게시글 배열의 0번째 게시글 접근

            BigInteger postNum = resultList.component2();
            //반환된 post 개수

            //return postList.get(0).postId.toString();

            return reviewList;
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/post")
    public void reviewPost(
            // @RequestParam String userId
            //@ModelAttribute ReviewModel review
            @RequestBody ReviewModel review
    ) {
        try {
            //게시글 저장할때 전부 String인데 마지막 시간은 BigInteger로 바꿔서 입력
            BigInteger createAt = BigInteger.valueOf(review.getCreatedAt());

            //게시글 블록체인에 저장하는 함수
            TransactionReceipt transactionReceipt = contract.createReview(
                            review.getUserId(),
                            review.getReviewId(),
                            review.getReviewStar(),
                            review.getReviewText(),
                            review.getWriterNickname(),
                            createAt
                    )
                    .send();

        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
