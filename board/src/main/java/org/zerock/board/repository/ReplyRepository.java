package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // JPQL을 이용해 update, delete를 실행하기 위해서 @Modifying 어노테이션 필요
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno")
    void deleteByBno(Long bno);

    // Board 객체를 파라미터로 받고 모든 댓글을 순번대로 가져온다.
    List<Reply> getRepliesByBoardOrderByRno(Board board);
    
}
