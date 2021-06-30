package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.guestbook.entity.GuestBook;

public interface GuestbookRepository extends JpaRepository<GuestBook, Long>
        , QuerydslPredicateExecutor<GuestBook> {

}
