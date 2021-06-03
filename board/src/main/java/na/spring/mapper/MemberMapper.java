package na.spring.mapper;

import na.spring.domain.MemberVO;

/**
 * Mybatisでsql文をxml形式で使うためのmapperとして、直接的にextendsして使用しない
 */
public interface MemberMapper {
    public MemberVO read(String userid);
}
