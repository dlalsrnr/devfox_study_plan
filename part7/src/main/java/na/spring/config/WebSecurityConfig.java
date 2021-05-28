package na.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/sample/member").hasRole("MEMBER").antMatchers("/sample/admin").hasRole("ADMIN")
                .antMatchers("/sample/all").permitAll().and()
                // 로그인 페이지 설정
                .formLogin().loginPage("/customLogin").loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler()).and()
                // 로그아웃 페이지 설정
                .logout().logoutUrl("/customLogout").invalidateHttpSession(true)
                .deleteCookies("remember-me", "JSESSION_ID").and()
                // 예외 설정
                .exceptionHandling().accessDeniedPage("/accessError");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        String queryUser = "select userid, userpw, enabled from tbl_member where userid = ?";
        String queryDetails = "select userid, auth from tbl_member_auth where userid = ?";

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(queryUser).authoritiesByUsernameQuery(queryDetails);

    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
}
