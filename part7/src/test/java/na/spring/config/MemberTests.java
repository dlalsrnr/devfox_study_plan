package na.spring.config;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import na.spring.DatabaseConfiguration;
import na.spring.Main;

@SpringBootTest(classes = { Main.class, DatabaseConfiguration.class, WebSecurityConfig.class })
@ExtendWith(SpringExtension.class)
public class MemberTests {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DataSource dataSource;

    @Test
    public void testInsertMember() {
        String sql = "insert into tbl_member(userid, userpw, username) values (?, ?, ?)";
        for (int i = 0; i < 100; i++) {
            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                con = dataSource.getConnection();
                pstmt = con.prepareStatement(sql);

                pstmt.setString(2, passwordEncoder.encode("pw" + i));
                if (i < 80) {
                    pstmt.setString(1, "user" + i);
                    pstmt.setString(3, "일반사용자" + i);
                } else if (i < 90) {
                    pstmt.setString(1, "manager" + i);
                    pstmt.setString(3, "운영자" + i);
                } else {
                    pstmt.setString(1, "admin" + i);
                    pstmt.setString(3, "관리자" + i);
                }
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null)
                        pstmt.close();
                    if (con != null)
                        con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testInsertAuth() {
        String sql = "insert into tbl_member_auth(userid, auth) values (?, ?)";
        for (int i = 0; i < 100; i++) {
            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                con = dataSource.getConnection();
                pstmt = con.prepareStatement(sql);
                if (i < 80) {
                    pstmt.setString(1, "user" + i);
                    pstmt.setString(2, "ROLE_USER");
                } else if (i < 90) {
                    pstmt.setString(1, "manager" + i);
                    pstmt.setString(2, "ROLE_MEMBER");
                } else {
                    pstmt.setString(1, "admin" + i);
                    pstmt.setString(2, "ROLE_ADMIN");
                }
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null)
                        pstmt.close();
                    if (con != null)
                        con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
