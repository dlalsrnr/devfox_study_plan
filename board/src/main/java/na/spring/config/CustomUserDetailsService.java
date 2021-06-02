package na.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.extern.slf4j.Slf4j;
import na.spring.domain.CustomUser;
import na.spring.domain.MemberVO;
import na.spring.mapper.MemberMapper;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("Load User By UserName : " + username);
        MemberVO vo = memberMapper.read(username);
        log.warn("queried by member mapper : " + vo);
        return vo == null ? null : new CustomUser(vo);
    }
}
