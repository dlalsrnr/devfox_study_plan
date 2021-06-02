package na.spring.mapper;

import java.util.List;

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
public class BoardMapperTests {

    @Autowired
    private BoardMapper mapper;

    @Test
    public void testGetList() {
        mapper.getList().forEach(board -> log.info(board.toString()));
    }

    @Test
    public void testPaging() {
        Criteria cri = new Criteria();
        List<BoardVO> list = mapper.getListWithPaging(cri);
        list.forEach(board -> log.info(board.toString()));
    }

    @Test
    public void testInsert() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글");
        board.setContent("새로 작성하는 내용");
        board.setWriter("newbie");

        mapper.insert(board);
        log.info(board.toString());
    }

    @Test
    public void testInsertSelectKey() {
        BoardVO board = new BoardVO();
        board.setTitle("새로 작성하는 글 select key");
        board.setContent("새로 작성하는 내용 select key");
        board.setWriter("newbie");

        mapper.insertSelectKey(board);
        log.info(board.toString());
    }

    @Test
    public void testRead() {
        BoardVO board = mapper.read(5L);
        log.info(board.toString());
    }

    @Test
    public void testDelete() {
        log.info("DELETE COUNT: " + mapper.delete(3L));
    }

    @Test
    public void testUpdate() {
        BoardVO board = new BoardVO();
        int row = 0;
        long bno = 5L; // 입력값으로 수정
        if (mapper.read(bno).getBno() != 0) {
            board.setBno(bno);
            board.setTitle("수정된 제목");
            board.setContent("수정된 내용");
            board.setWriter("user00");
            row = mapper.update(board);
        }
        log.info("UPDATE COUNT: " + row);
    }

    @Test
    public void testSearch() {
        Criteria cri = new Criteria();
        cri.setKeyword("새로");
        cri.setType("TC");
        List<BoardVO> list = mapper.getListWithPaging(cri);
        list.forEach(board -> log.info(board.toString()));
    }
}
