package na.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import na.spring.Main;

@SpringBootTest(classes = Main.class)
@Slf4j
public class SampleServiceTests {
    @Autowired
    private SampleService sampleService;

    @Test
    public void testClass() throws Exception {
        log.info(sampleService.doAdd("123", "456").toString());
    }

    @Test
    public void testAddError() throws Exception {
        log.info(sampleService.doAdd("123", "ABC").toString());
    }
}
