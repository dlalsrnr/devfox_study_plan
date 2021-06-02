package na.spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import na.spring.domain.Criteria;
import na.spring.domain.ReplyVO;

/**
 * Mybatisでsql文をxml形式で使うためのmapperとして、直接的にextendsして使用しない
 */
public interface ReplyMapper {
    public int insert(ReplyVO vo);

    public ReplyVO read(Long rno);

    public int delete(Long rno);

    public int update(ReplyVO reply);

    public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);

    public int getCountByBno(Long bno);
}
