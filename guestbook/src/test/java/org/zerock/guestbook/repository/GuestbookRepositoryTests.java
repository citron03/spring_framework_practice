package org.zerock.guestbook.repository;


import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.entity.QGuestBook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,300).forEach(i->{
            GuestBook guestbook = GuestBook.builder()
                    .title("Title...."+i)
                    .content("Content...."+i)
                    .writer("user"+(i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });

    }


    @Test
    public void updateTest(){
        Optional<GuestBook> result = guestbookRepository.findById(300L);
        // 존재하는 번호로 테스트

        if(result.isPresent()){
            GuestBook guestBook = result.get();

            guestBook.changeTitle("Change Title.......");
            guestBook.changeContent("Change Content........");

            guestbookRepository.save(guestBook);

        }
    }


    @Test
    public void testQuery1(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook; // 1

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); // 2

        BooleanExpression expression = qGuestBook.title.contains(keyword); // 3

        builder.and(expression); // 4

        Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });

    }

    @Test
    public void testQuery2(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestBook.title.contains(keyword);

        BooleanExpression exContent = qGuestBook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent); // 1

        builder.and(exAll); // 2

        builder.and(qGuestBook.gno.gt(0L)); // 3

        Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestBook -> {
            System.out.println(guestBook);
        });

    }

}
