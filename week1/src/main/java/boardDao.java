package boardDao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import boardDto.BoardDto;
import common.DBConnection;

public class BoardDao {

    // 페이지 설정
    public int getTotalCount(String select, String search) {
        int count = 0;
        String query = "select count(*) count from freeboard_board " +
                       "where " + select + " like ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 물음표에 search 값 설정
            ps.setString(1, "%" + search + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            System.out.println("getTotalCount() 오류 : " + query);
            e.printStackTrace();
        }
        return count;
    }

    // 댓글 조회
    public ArrayList<BoardDto> getCommentList(String no) {
        ArrayList<BoardDto> dtos = new ArrayList<>();
        String query = "select reg_id, content, reg_date from freeboard_board " +
                       "where b_comment = ? " +
                       "order by reg_date desc";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 물음표에 no 값 설정
            ps.setString(1, no);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String reg_id = rs.getString("reg_id");
                    String comment = rs.getString("content");
                    BoardDto dto = new BoardDto(comment, reg_id);
                    dtos.add(dto);
                }
            }
        } catch (SQLException e) {
            System.out.println("getCommentList() 오류 : " + query);
            e.printStackTrace();
        }
        return dtos;
    }

    // 댓글 등록
    public int commentSave(BoardDto dto) {
        int result = 0;
        String query = "insert into freeboard_board " +
                       "(no, content, reg_id, reg_date, b_comment) " +
                       "values (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 각 물음표에 DTO의 값을 설정
            ps.setInt(1, dto.getNo());
            ps.setString(2, dto.getComment());
            ps.setString(3, dto.getReg_id());
            ps.setString(4, dto.getReg_date());
            ps.setInt(5, dto.getNo2());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("commentSave() 오류 : " + query);
            e.printStackTrace();
        }
        return result;
    }

    // 세션 ID
    public String getSessionId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String Id = (String) session.getAttribute("sessionId");
        return Id;
    }

    // 세션 Name
    public String getSessionName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("sessionName");
        return name;
    }

    // 로그인
    public String getLoginInfo(String id, String password) {
        String name = "";
        String query = "select name from freeboard_member " +
                       "where id = ? " +
                       "and password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 첫 번째 물음표에 id 값 설정
            ps.setString(1, id);
            // 두 번째 물음표에 password 값 설정
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                }
            }
        } catch (SQLException e) {
            System.out.println("getLoginInfo 오류: " + query);
            e.printStackTrace();
        }
        return name;
    }

    // 회원가입
    public int memberSave(BoardDto dto) {
        int result = 0;
        String query = "insert into freeboard_member values (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 각 물음표에 DTO의 값을 설정
            ps.setString(1, dto.getId());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getPassword());
            ps.setString(4, dto.getEmail());
            ps.setString(5, dto.getReg_date());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("memberSave() 오류 : " + query);
            e.printStackTrace();
        }
        return result;
    }

    // 비밀번호 암호화
    public String encryptSHA256(String value) throws NoSuchAlgorithmException {
        String encryptData = "";

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(value.getBytes());

        byte[] digest = sha.digest();
        for (int i = 0; i < digest.length; i++) {
            encryptData += Integer.toHexString(digest[i] & 0xFF).toUpperCase();
        }

        return encryptData;
    }

    // 댓글 삭제
    public int commentDelete(String cno) {
        int result = 0;
        String query = "delete from freeboard_board where no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, cno);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("commentDelete() 오류 : " + query);
            e.printStackTrace();
        }
        return result;
    }

    // 게시글 삭제
    public int boardDelete(String no) {
        int result = 0;
        String query = "delete from freeboard_board where no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, no);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("boardDelete() 오류 : " + query);
            e.printStackTrace();
        }
        return result;
    }

    // 게시글 업데이트
    public int boardUpdate(BoardDto dto) {
        int result = 0;
        String query = "update freeboard_board " +
                       "set title=?, content=?, reg_id=?, reg_date=? " +
                       "where no=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 첫 번째 물음표에 title 값 설정
            ps.setString(1, dto.getTitle());
            // 두 번째 물음표에 content 값 설정
            ps.setString(2, dto.getContent());
            // 세 번째 물음표에 reg_id 값 설정
            ps.setString(3, dto.getReg_id());
            // 네 번째 물음표에 reg_date 값 설정
            ps.setString(4, dto.getReg_date());
            // 다섯 번째 물음표에 no 값 설정
            ps.setString(5, String.valueOf(dto.getNo()));
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("boardUpdate() 오류 :" + query);
            e.printStackTrace();
        }
        return result;
    }

    // 댓글 업데이트
    public int commentUpdate(BoardDto dto) {
        int result = 0;
        String query = "update freeboard_board " +
                       "set content = ?, reg_date = ? " +
                       "where no=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 첫 번째 물음표에 content 값 설정
            ps.setString(1, dto.getContent());
            // 두 번째 물음표에 reg_date 값 설정
            ps.setString(2, dto.getReg_date());
            // 세 번째 물음표에 no 값 설정
            ps.setInt(3, dto.getNo());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("commentUpdate() 오류 :" + query);
            e.printStackTrace();
        }
        return result;
    }

    // 댓글 수정, 삭제 위한 불러오기
    public BoardDto getCommentView(String sessionName, String cno) {
        BoardDto dto = null;
        String query = "select no, content, reg_id " +
                       "from freeboard_board " +
                       "where reg_id = ? " +
                       "and title is null " +
                       "and no like ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 첫 번째 물음표에 sessionName 값 설정
            ps.setString(1, sessionName);
            // 두 번째 물음표에 cno 값 설정
            ps.setString(2, "%" + cno + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String no = rs.getString("no");
                    String content = rs.getString("content");
                    String reg_id = rs.getString("reg_id");
                    dto = new BoardDto(content, reg_id, Integer.parseInt(no));
                }
            }
        } catch (SQLException e) {
            System.out.println("getCommentView() 오류 : " + query);
            e.printStackTrace();
        }
        return dto;
    }

    // 게시글 상세보기
    public BoardDto getBoardView(String no) {
        BoardDto dto = null;
        String query = "select title, content, reg_id, to_char(reg_date,'yyyy-mm-dd') reg_date " +
                       "from freeboard_board " +
                       "where no = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 물음표에 no 값 설정
            ps.setString(1, no);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getNString("title");
                    String content = rs.getNString("content");
                    String reg_id = rs.getNString("reg_id");
                    String reg_date = rs.getNString("reg_date");
                    dto = new BoardDto(Integer.parseInt(no), title, content, reg_id, reg_date);
                }
            }
        } catch (SQLException e) {
            System.out.println("getBoardView() 오류 : " + query);
            e.printStackTrace();
        }
        return dto;
    }

    // 게시글 조회
    public ArrayList<BoardDto> getBoardList(String select, String search, int start, int end) {
        ArrayList<BoardDto> dtos = new ArrayList<>();
        String query = "select * from(" +
                       "    select rownum as rnum, tbl.*" +
                       "        from(" +
                       "select no, title, content, reg_id, to_char(reg_date,'yyyy-mm-dd') reg_date " +
                       "from freeboard_board " +
                       "where " + select + " like ? " +
                       "and title != 'null' " +
                       "order by no desc" +
                       " ) tbl)" +
                       "where rnum >= ? and rnum <= ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 첫 번째 물음표에 검색어 설정
            ps.setString(1, "%" + search + "%");
            // 두 번째와 세 번째 물음표에 시작과 끝 설정
            ps.setInt(2, start);
            ps.setInt(3, end);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int no = rs.getInt("no");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String reg_id = rs.getString("reg_id");
                    String reg_date = rs.getString("reg_date");
                    BoardDto dto = new BoardDto(no, title, content, reg_id, reg_date);
                    dtos.add(dto);
                }
            }
        } catch (SQLException e) {
            System.out.println("getBoardList() 오류 : " + query);
            e.printStackTrace();
        }
        return dtos;
    }

    // 게시글 저장
    public int boardSave(BoardDto dto) {
        int result = 0;
        String query = "insert into freeboard_board " +
                       "(no, title, content, reg_id, reg_date) " +
                       "values (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            // 각 물음표에 DTO의 값을 설정
            ps.setInt(1, dto.getNo());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, dto.getReg_id());
            ps.setString(5, dto.getReg_date());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("boardSave() 오류 : " + query);
            e.printStackTrace();
        }
        return result;
    }

    // 게시글 번호 생성
    public int getMaxNo() {
        int no = 0;
        String query = "select (max(no)) as no " +
                       "from freeboard_board";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                no = rs.getInt("no");
                no++;
            }
        } catch (SQLException e) {
            System.out.println("getMaxNo() 오류:" + query);
            e.printStackTrace();
        }
        return no;
    }
}
