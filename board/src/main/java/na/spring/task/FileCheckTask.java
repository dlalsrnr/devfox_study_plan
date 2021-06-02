package na.spring.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import na.spring.domain.BoardAttachVO;
import na.spring.mapper.BoardAttachMapper;

@Component
@Slf4j
public class FileCheckTask {
    @Autowired
    private BoardAttachMapper attachMapper;

    private String getFolederYesterDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now().plusDays(-1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String str = simpleDateFormat.format(date);
        return str.replace("-", File.separator);
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void checkFiles() throws Exception {
        log.warn("File Check Task run....................");
        log.warn(new Date().toString());
        List<BoardAttachVO> fileList = attachMapper.getOldFiles();
        List<Path> fileListPaths = fileList.stream()
                .map(vo -> Paths.get("C:/upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName()))
                .collect(Collectors.toList());
        fileList.stream().filter(vo -> vo.getFileType().equals("1"))
                .map(vo -> Paths.get("C:/upload", vo.getUploadPath(), "s_" + vo.getUuid() + "_" + vo.getFileName()))
                .forEach(p -> fileListPaths.add(p));
        fileListPaths.forEach(p -> log.warn(p.toString()));
        File targetDir = Paths.get("C:/upload", getFolederYesterDay()).toFile();
        File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
        for (File file : removeFiles) {
            log.warn(file.getAbsolutePath());
            file.delete();
        }
    }
}
