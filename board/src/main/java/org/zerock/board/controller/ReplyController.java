package org.zerock.board.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies/")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    // 조회 화면에서 Ajax로 댓글을 표시한다.

    private final ReplyService replyService; // 자동주입을 위한 final

    @GetMapping(value = "/board/{bno}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReplyDTO>> getListByBoard(@PathVariable("bno") Long bno){
        // HTTP의 상태 코드등을 같이 전달할 수 있다. 이를 통해서 /replies/board/100 에서 100같은 정보를 전달할 수 있다.

        log.info("bno: "+bno);

        return new ResponseEntity<>( replyService.getList(bno), HttpStatus.OK );

    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        Long rno = replyService.register(replyDTO);

        return new ResponseEntity<>(rno,  HttpStatus.OK);

    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno){

        log.info("RNO:"+rno);

        replyService.remove(rno);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        replyService.modify(replyDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }

}
