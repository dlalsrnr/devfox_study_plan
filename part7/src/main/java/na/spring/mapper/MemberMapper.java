package na.spring.mapper;

import na.spring.domain.MemberVO;

public interface MemberMapper {
    public MemberVO read(String userid);
}
