package na.spring.mapper;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j2;
import na.spring.domain.ReplyVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { na.spring.DatabaseConfiguration.class })
@Log4j2
public class ReplyMapperTests {
    @Autowired
    private ReplyMapper mapper;

    private Long[] bnoArr = { 19L, 20L, 21L, 22L, 23L };

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
        log.info(vo);
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
    public void testMapper() {
        log.info(mapper);
    }
}
