package org.zerock.mreview.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMovieReviews(){

        // 200개의 리뷰를 등록한다.
        IntStream.rangeClosed(1,200).forEach(i -> {

            // 영화 번호
            Long mno = (long)(Math.random()*100) + 1;

            // 리뷰어 번호
            Long mid = ((long)(Math.random()*100) + 1);
            Member member = Member.builder()
                    .mid(mid)
                    .build();

            Review movieReview = Review.builder()
                    .member(member)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random()*5) + 1)
                    .text("이 영화에 대한 리뷰..."+i)
                    .build();

            reviewRepository.save(movieReview);

        });

    }


    @Test
    public void testGetMovieReviews(){

        // Review 클레스의 Member의 fetch가 Lazy면 한번에 하나의 Review객체와 Member 객체를 조회할 수 있어 오류가 발생한다.

        Movie movie = Movie.builder()
                .mno(5L)
                .build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {

            System.out.println("\t"+movieReview.getGrade());
            System.out.println("\t"+movieReview.getText());
            System.out.println("\t"+movieReview.getMember().getEmail());
            System.out.println("---------------------------------");

        });


    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){

        Long mid = 1L; //Member의 mid

        Member member = Member.builder()
                .mid(mid)
                .build();

        // 처음 에러가 나는 코드
        //memberRepository.deleteById(mid);
        //reviewRepository.deleteByMember(member);

        // 순서가 중요하다.
        // 하지만 review 테이블에서 3번 반복실행 후 member 테이블을 삭제하는 비효율 발생
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);

        // 에러를 방지하기 위해서는 FK를 가지는 Review쪽을 먼저 삭제해야 하고 트랜젝션 관련 처리를 해야한다.

    }

}
