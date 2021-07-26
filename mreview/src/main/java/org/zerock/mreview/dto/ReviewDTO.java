package org.zerock.mreview.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    // 화면에 필요한 모든 내용을 가지고 있어야 한다.


    // review num
    private Long reviewnum;

    // Movie mno
    private Long mno;

    // Member id
    private Long mid;

    // Member nickname
    private String nickname;

    // Member email
    private String email;

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;


}
