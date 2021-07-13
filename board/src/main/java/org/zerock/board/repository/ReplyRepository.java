package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // JPQL을 이용해 update, delete를 실행하기 위해서 @Modifying 어노테이션 필요
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno")
    void deleteByBno(Long bno);
    
}
