package na.spring.mapper;

import na.spring.domain.ReplyVO;

public interface ReplyMapper {
    public int insert(ReplyVO vo);

    public ReplyVO read(Long rno);

    public int delete(Long rno);

    public int update(ReplyVO reply);
}
