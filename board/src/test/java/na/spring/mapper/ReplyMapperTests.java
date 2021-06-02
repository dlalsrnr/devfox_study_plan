package na.spring.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import na.spring.domain.Criteria;
import na.spring.domain.ReplyVO;

@SpringBootTest
@MapperScan(basePackages = "na.spring.mapper")
@Slf4j
public class ReplyMapperTests {
    @Autowired
    private ReplyMapper mapper;

    private Long[] bnoArr = { 5L, 19L, 20L, 21L, 22L };

    @Test
    public void testCreate() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ReplyVO vo = new ReplyVO();
            vo.setBno(bnoArr[i % 5]);
            vo.setReply("댓글 테스트 " + i);
            vo.setReplyer("replyer" + i);
            mapper.insert(vo);
        });
    }

    @Test
    public void testRead() {
        Long targetRno = 5L;
        ReplyVO vo = mapper.read(targetRno);
        log.info(vo.toString());
    }

    @Test
    public void testDelete() {
        Long targetRno = 1L;
        mapper.delete(targetRno);
    }

    @Test
    public void testUpdate() {
        Long targetRno = 10L;
        ReplyVO vo = mapper.read(targetRno);
        vo.setReply("Update Reply ");
        int count = mapper.update(vo);
        log.info("UPDATE COUNT : " + count);
    }

    @Test
    public void testList() {
        Criteria cri = new Criteria();
        List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
        replies.forEach(reply -> log.info(reply.toString()));
    }

    @Test
    public void testList2() {
        Criteria cri = new Criteria(1, 10);
        List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
        replies.forEach(reply -> log.info(reply.toString()));
    }

    @Test
    public void testMapper() {
        log.info(mapper.toString());
    }
}
