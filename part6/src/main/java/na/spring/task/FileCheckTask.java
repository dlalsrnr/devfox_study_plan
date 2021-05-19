package na.spring.task;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileCheckTask {
    private String getFolederYesterDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now().plusDays(-1);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String str = simpleDateFormat.format(date);
        return str.replace("-", File.separator);
    }

    @Scheduled(cron = "0 0 2 * * *") // 새벽 2시마다 작동
    public void checkFiles() throws Exception {
        log.warn("File Check Task run....................");
        log.warn(new Date().toString());
        // db에서 데이터 받아서 VO에 저장한 리스트 넣어주고
        // 그 리스트에 path, uuid, 이름 추가해주고
        // 이미지면 섬네일 파일까지 찾고
        File targetDir = Paths.get("C:/upload", getFolederYesterDay()).toFile();
        // 경로에 가서 저 vo리스트에 없는 파일 찾아서 새로 배열에 넣어주고
        // for문으로 그 배열에 저장된 파일들 삭제

    }
}
