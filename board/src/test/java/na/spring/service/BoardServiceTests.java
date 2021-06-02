package na.spring.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import na.spring.domain.BoardVO;
import na.spring.domain.Criteria;

@SpringBootTest
@MapperScan(basePackages = "na.spring.mapper")
@Slf4j
public class BoardServiceTests {
    @Autowired
    private BoardService service;

    @Test
    public void testExist() {
        log.info(service.toString());
        assertNotNull(service);
    }

    @Test
    public void testRegister() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");
        service.register(board);
        log.info("생성된 게시물의 번호 : " + board.getBno());
    }

    @Test
    public void testGetList() {
        service.getList(new Criteria(2, 10)).forEach(board -> log.info(board.toString()));
    }

    @Test
    public void testGet() {
        log.info(service.get(12L).toString());
    }

    @Test
    public void testDelete() {
        log.info("REMOVE RESULT : " + service.remove(2L));
    }

    @Test
    public void testUpdate() {
        BoardVO board = service.get(5L);
        if (board == null)
            return;
        board.setTitle("제목 수정");
        log.info("MODIF RESULT : " + service.modify(board));
    }
}
