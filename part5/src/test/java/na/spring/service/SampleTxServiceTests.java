package na.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;
import na.spring.DatabaseConfiguration;
import na.spring.Main;

@SpringBootTest(classes = { Main.class, DatabaseConfiguration.class })
@ExtendWith(SpringExtension.class)
@Slf4j
public class SampleTxServiceTests {
    @Autowired
    private SampleTxService service;

    @Test
    public void testLong() {
        String str = "Starry\r\n" + "Starry night\r\n" + "Paint your palette blue and grey\r\n"
                + "Look out on a summer's day";
        log.info(Integer.toString(str.getBytes().length));
        service.addData(str);
    }
}
