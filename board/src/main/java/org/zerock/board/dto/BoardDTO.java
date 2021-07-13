package org.zerock.board.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;
    private String content;

    // 엔티티와 다르게 멤버를 참조하지 않는다.
    private String writerEmail; // 작성자의 이메일
    private String writerName; // 작성자의 이름

    private LocalDateTime regDate;
    private  LocalDateTime modDate;

    private int replyCount; // 해당 게시글의 댓글 수

}
