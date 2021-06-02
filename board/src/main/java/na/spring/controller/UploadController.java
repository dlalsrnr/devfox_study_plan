package na.spring.controller;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import na.spring.domain.AttachFileDTO;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Slf4j
public class UploadController {
    @PostMapping(value = "/uploadFormAction")
    public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
        String uploadFolder = "C:/upload";
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

    @PostMapping("/uploadAjaxAction")
    @ResponseBody
    public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
        List<AttachFileDTO> list = new ArrayList<>();
        String uploadFolder = "C:/upload";
        File uploadPath = new File(uploadFolder, getFolder());
        if (!uploadPath.exists())
            uploadPath.mkdirs();
        for (MultipartFile multipartFile : uploadFile) {
            AttachFileDTO attachFileDTO = new AttachFileDTO();
            UUID uuid = UUID.randomUUID();
            String uploadFileName = uuid.toString() + "_" + multipartFile.getOriginalFilename();
            attachFileDTO.setFileName(multipartFile.getOriginalFilename());
            try {
                File saveFile = new File(uploadPath, uploadFileName);
                multipartFile.transferTo(saveFile);
                attachFileDTO.setUuid(uuid.toString());
                attachFileDTO.setUploadPath(getFolder());
                if (checkImageType(saveFile)) {
                    attachFileDTO.setImage(true);
                    Thumbnailator.createThumbnail(saveFile, new File(uploadPath, "s_" + uploadFileName), 100, 100);
                }
                list.add(attachFileDTO);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName) {
        log.info("fileName : " + fileName);
        File file = new File("C:/upload/" + fileName);

        log.info("file : " + file);
        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(String fileName) {
        log.info("download file : " + fileName);
        Resource resource = new FileSystemResource("c:/upload/" + fileName);
        if (!resource.exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        log.info("resource : " + resource);
        String resourceName = resource.getFilename();
        String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1); // remove uuid
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Disposition",
                    "attachment; filename=" + new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String type) {
        log.info("deleteFile : " + fileName);
        File file;

        try {
            file = new File("C:/upload/" + URLDecoder.decode(fileName, "UTF-8"));
            file.delete();
            if (type.equals("image")) {
                String largeFileName = file.getAbsolutePath().replace("s_", "");
                log.info("largeFileName : " + largeFileName);

                file = new File(largeFileName);
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    private String getFolder() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sFormat.format(date);
        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
