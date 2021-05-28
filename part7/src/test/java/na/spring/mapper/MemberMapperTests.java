package na.spring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;
import na.spring.DatabaseConfiguration;
import na.spring.Main;
import na.spring.domain.MemberVO;

@SpringBootTest(classes = { Main.class, DatabaseConfiguration.class })
@ExtendWith(SpringExtension.class)
@Slf4j
public class MemberMapperTests {
    @Autowired
    private MemberMapper mapper;

    @Test
    public void testRead() {
        MemberVO vo = mapper.read("admin90");
        log.info(vo.toString());
        vo.getAuthList().forEach(authVO -> log.info(authVO.toString()));
    }
}
