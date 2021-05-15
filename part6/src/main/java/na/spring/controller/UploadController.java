package na.spring.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {
    @GetMapping("/uploadForm")
    public void uploadForm() {
        log.info("upload form");
    }

    @PostMapping(value = "/uploadFormAction")
    public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
        String uploadFolder = "C:/upload"; // 경로는 / or \\ 둘 다 사용가능
        for (MultipartFile multipartFile : uploadFile) {
            log.info("------------------------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload File Size : " + multipartFile.getSize());
            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @GetMapping("/uploadAjax")
    public void uploadAjax() {
        log.info("upload ajax");
    }

    @PostMapping("/uploadAjaxAction")
    public void uploadAjaxPost(MultipartFile[] uploadFile) {
        String uploadFolder = "C:/upload"; // 경로는 / or \\ 둘 다 사용가능
        File uploadPath = new File(uploadFolder, getFolder());
        log.info("upload path : " + uploadPath);
        if (!uploadPath.exists()) // 경로에 폴더가 없으면 만듬
            uploadPath.mkdirs();
        for (MultipartFile multipartFile : uploadFile) {
            log.info("------------------------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload File Size : " + multipartFile.getSize());
            UUID uuid = UUID.randomUUID();
            File saveFile = new File(uploadPath, (uuid.toString() + "_" + multipartFile.getOriginalFilename()));
            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private String getFolder() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sFormat.format(date);
        return str.replace("-", File.separator);
    }
}
