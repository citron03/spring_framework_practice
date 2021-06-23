package org.zerock.ex2.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){

        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){

        IntStream.rangeClosed(1, 100).forEach( i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
                });

    }

    @Transactional
    @Test
    public void testSelect(){

        // 데이터 베이스에 존재하는 mno
        Long mno = 100L;

        //Optional<Memo> result = memoRepository.findById(mno);
        Memo memo = memoRepository.getOne(mno);

        System.out.println("============================");

        /*
        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
        */
        System.out.println(memo);

    }


    @Test
    public void testUpdate(){

        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));

    }

    @Test
    public void testDelete(){

        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){

        //1페이지 10개
        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        System.out.println("----------------------------------");
        
        // 총 몇 페이지
        System.out.println("Total Pages:"+result.getTotalPages());

        // 전체 개수
        System.out.println("Total Count"+result.getTotalElements());

        // 현재 페이지 번호 0부터 시작
        System.out.println("Page Number"+result.getNumber());

        // 페이지당 데이터 개수
        System.out.println("Page Site"+result.getSize());

        // 다음 페이지 존재 여부
        System.out.println("has next page?"+result.hasNext());

        // 시작페이지(0) 여부
        System.out.println("first page?"+result.isFirst());


        System.out.println("----------------------------------");

        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
        
    }


    @Test
    public void testSort(){

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

        //Pageable pageable = PageRequest.of(0,10,sort1);
        Pageable pageable = PageRequest.of(0,10,sortAll); // 결합된 정렬조건 사용

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(
                memo -> {
                    System.out.println(memo);
                }
        );

    }

    @Test
    public void testQueryMethods(){

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L,80L);

        for(Memo memo : list){

            System.out.println(memo);

        }

    }

    @Test
    public void testQueryMethodsWithPageable(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);

        result.get().forEach(
                memo -> {
                    System.out.println(memo);
                }
        );

    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

}
