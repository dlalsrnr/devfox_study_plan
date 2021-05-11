package na.spring.mapper;

import java.util.List;

import na.spring.domain.BoardVO;
import na.spring.domain.Criteria;

public interface BoardMapper {
    public List<BoardVO> getList();

    public List<BoardVO> getListWithPaging(Criteria cri);

    public void insert(BoardVO board);

    public void insertSelectKey(BoardVO board);

    public BoardVO read(Long bno);

    public int delete(Long bno);

    public int update(BoardVO vo);

    public int getTotalCount(Criteria cri);
}