package na.spring.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
    private String uuid;
    private String uploadPath;
    private String fileName;
    private String fileType;

    private Long bno;
}