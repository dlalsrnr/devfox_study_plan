package na.spring.mapper;

import java.util.List;

import na.spring.domain.BoardAttachVO;

/**
 * Mybatisでsql文をxml形式で使うためのmapperとして、直接的にextendsして使用しない
 */
public interface BoardAttachMapper {
    public void insert(BoardAttachVO vo);

    public void delete(String uuid);

    public List<BoardAttachVO> findByBno(Long bno);

    public void deleteAll(Long bno);

    public List<BoardAttachVO> getOldFiles();
}
