package org.zerock.guestbook.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor // 자동 주입을 위한 annotation
public class GuestbookController {

    private final GuestbookService service; //final로 선언

    @GetMapping("/")
    public String index(){

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        // Model로 결과 데이터를 화면에 전달

        log.info("list................." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

    }

    //등록 페이지와 등록 처리

    @GetMapping("/register")
    public void register(){
        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..."+ dto);

        //새로 추가된 엔티티의 번호

        long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg",gno);
        //redirectAttributes로 한 화면에서 msg라는 이름의 변수를 사용할 수 있도록 처리한다. 글이 등록된 후 모달창이 보이게 처리한다.
        //addFlashAttribute는 단 한번만 데이터를 전달하는 용도로 사용한다.

        return "redirect:/guestbook/list";

    }

    //@GetMapping("/read")

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){

        log.info("gno: "+gno);

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto",dto);

    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes
                         ){

        log.info("post modify..................................");
        log.info("dto: "+dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";

    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){

        log.info("gno: "+ gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";

    }

}
