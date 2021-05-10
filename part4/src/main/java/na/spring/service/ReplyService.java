package na.spring.service;

import java.util.List;

import na.spring.domain.Criteria;
import na.spring.domain.ReplyPageDTO;
import na.spring.domain.ReplyVO;

public interface ReplyService {
    public int register(ReplyVO vo);

    public ReplyVO get(Long rno);

    public int modify(ReplyVO vo);

    public int remove(Long rno);

    public List<ReplyVO> getList(Criteria cri, Long rno);

    public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
