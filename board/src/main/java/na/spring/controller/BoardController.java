package na.spring.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import na.spring.domain.BoardAttachVO;
import na.spring.domain.BoardVO;
import na.spring.domain.Criteria;
import na.spring.domain.PageDTO;
import na.spring.service.BoardService;

@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
    @Autowired
    private BoardService service;

    @GetMapping("/list")
    public void list(Criteria cri, Model model) {
        log.info("list : " + cri);
        model.addAttribute("list", service.getList(cri));
        int total = service.getTotal(cri);
        log.info("total : " + total);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr) {
        log.info("register : " + board);
        if (board.getAttachList() != null) {
            board.getAttachList().forEach(attach -> log.info(attach.toString()));
        }
        service.register(board);
        rttr.addFlashAttribute("result", board.getBno());
        return "redirect:/board/list";
    }

    @GetMapping("/register")
    public void register() {

    }

    @GetMapping({ "/get", "/modify" })
    public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
        log.info("/get or /modify");
        model.addAttribute("board", service.get(bno));
        model.addAttribute("pageMaker", new PageDTO(cri, service.getTotal(cri)));
    }

    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("modify : " + board);
        if (service.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list" + cri.getListLink();
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("remove : " + bno);
        List<BoardAttachVO> attachList = service.getAttachList(bno);
        if (service.remove(bno)) {
            deleteFiles(attachList);
            rttr.addFlashAttribute("result", "success");
        }
        if (cri.getPageNum() > new PageDTO(cri, service.getTotal(cri)).getEndPage()) {
            cri.setPageNum(cri.getPageNum() - 1);
        } // 削除された後、pageNumが減るべきケースハンドリング
        return "redirect:/board/list" + cri.getListLink();
    }

    @GetMapping("/getAttachList")
    @ResponseBody
    public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {
        log.info("getAttachList" + bno);
        return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
    }

    private void deleteFiles(List<BoardAttachVO> attachList) {
        if (attachList == null || attachList.size() == 0)
            return;

        log.info("delete attach files............");
        log.info(attachList.toString());
        attachList.forEach(attach -> {
            try {
                Path file = Paths.get(
                        "C:/upload/" + attach.getUploadPath() + "/" + attach.getUuid() + "_" + attach.getFileName());
                Files.deleteIfExists(file);
                if (Files.probeContentType(file).startsWith("image")) {
                    Path thumbNail = Paths.get("C:/upload/" + attach.getUploadPath() + "/s_" + attach.getUuid() + "_"
                            + attach.getFileName());
                    Files.delete(thumbNail);
                }
            } catch (Exception e) {
                log.error("delete file error" + e.getMessage());
            }
        });
    }
}
