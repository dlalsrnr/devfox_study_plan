package na.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
// @AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/sample/admin").hasRole("ADMIN").antMatchers("/sample/member").hasRole("MEMBER")
                .antMatchers("/sample/all").permitAll().and()
                // 로그인 페이지 설정
                .formLogin().loginPage("/customLogin").successHandler(new CustomLoginSuccessHandler()).permitAll().and()
                // 로그아웃 페이지 설정
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/customLogout"))
                .logoutSuccessUrl("/customLogin").invalidateHttpSession(true).and()
                // 예외 설정
                .exceptionHandling().accessDeniedPage("/accessError");
    }

    // @Override
    // public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    // }
}
