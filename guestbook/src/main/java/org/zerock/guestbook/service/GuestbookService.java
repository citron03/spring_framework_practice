package org.zerock.guestbook.service;


import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.GuestBook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    default GuestBook dtoToEntity(GuestbookDTO dto){
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
