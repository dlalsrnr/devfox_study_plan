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
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	
	
	
   // 페이지 설정 
   public int getTotalCount(String select,String search){
      int count = 0;
      String query ="select count(*) count from freeboard_board\r\n" + 
      			    "where "+select+" like '%"+search+"%'";
      try {
         con = DBConnection.getConnection();
         ps  = con.prepareStatement(query);
         rs  = ps.executeQuery();
         if(rs.next()) {
            count = rs.getInt("count");
         }
      }catch(Exception e) {
         System.out.println("getTotalCount()오류 :"+query);
         e.printStackTrace();
      }finally {
         DBConnection.DBClose(con, ps, rs);
      }         
      return count;
   }
	
	
	//댓글 조회
	public ArrayList<BoardDto> getCommentList(String no){
		ArrayList<BoardDto> dtos = new ArrayList<>();
		String query = "select reg_id, content, reg_date from freeboard_board\r\n" + 
					   "where b_comment = '"+no+"'\r\n" + 
					   "order by reg_date desc";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				String reg_id = rs.getString("reg_id");
				String comment = rs.getString("content");
				BoardDto dto = new BoardDto(comment,reg_id);
				dtos.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("getCommentList() 오류 : "+query);
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return dtos;
	}
	
	
	//댓글등록
	public int commentSave(BoardDto dto) {
		int result = 0;
		String query = "insert into freeboard_board\r\n" + 
					   "(no,content,reg_id,reg_date,b_comment)\r\n" + 
					   "values\r\n" + 
					   "("+dto.getNo()+",'"+dto.getComment()+"','"+dto.getReg_id()+"','"+dto.getReg_date()+"',"+dto.getNo2()+")";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println("commentSave() 오류 : "+ query );
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return result;
	}
	
	
	
	
	//세션ID
	public String getSessionId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String Id = (String)session.getAttribute("sessionId");
		return Id;
	}
	
	//세션Name
	public String getSessionName(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("sessionName");
		return name;
	}
	
	//로그인
	public String getLoginInfo(String id, String password) {
		String name="";
		String query="select name from freeboard_member\r\n" + 
					 "where id = '"+id+"'\r\n" + 
					 "and password = '"+password+"'";
		try {
			con=DBConnection.getConnection();
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next()) {
				name=rs.getString("name");
			}
		} catch (SQLException e) {
			System.out.println("getLoginInfo오류:"+query);
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		 return name;
	}
	
	
	
	//회원가입
	public int memberSave(BoardDto dto) {
		int result = 0;
		String query = "insert into freeboard_member values\r\n" + 
					   "('"+dto.getId()+"','"+dto.getName()+"','"+dto.getPassword()+"','"+dto.getEmail()+"','"+dto.getReg_date()+"')";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println("memberSave() 오류 : "+ query );
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return result;
	}
		
	//비밀번호 암호화
    public String encryptSHA256(String value) throws NoSuchAlgorithmException{
        String encryptData ="";
        
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(value.getBytes());
 
        byte[] digest = sha.digest();
        for (int i=0; i<digest.length; i++) {
            encryptData += Integer.toHexString(digest[i] &0xFF).toUpperCase();
        }
         
        return encryptData;
    }
	
	
	//게시글 삭제
	public int boardDelete(String no) {
		int result = 0;
		String query="delete from freeboard_board where no = '"+no+"'";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("boardDelete() 오류 : "+query);
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return result;
	}
	
	//게시글 업데이트
	public int boardUpdate(BoardDto dto) {
		int result = 0;
		String query = "update freeboard_board\r\n" + 
					   "set title='"+dto.getTitle()+"', content='"+dto.getContent()+"', reg_id='"+dto.getReg_id()+"', reg_date='"+dto.getReg_date()+"'\r\n" + 
					   "where no='"+dto.getNo()+"'";
		try {
			con =  DBConnection.getConnection();
			ps = con.prepareStatement(query);
			result = ps.executeUpdate();
		}catch (SQLException e) {
			System.out.println("boardUpdate() 오류 :"+query);
			e.printStackTrace();
		}finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return result;
	}
	
	//게시글 상세보기
	public BoardDto getBoardView(String no) {
		BoardDto dto = null;
		String query="select title, content, reg_id, to_char(reg_date,'yyyy-mm-dd') reg_date\r\n" + 
					 "from freeboard_board\r\n" + 
					 "where no = '"+no+"'";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			if(rs.next()) {
				String title = rs.getNString("title");
				String content = rs.getNString("content");
				String reg_id = rs.getNString("reg_id");
				String reg_date = rs.getNString("reg_date");
				dto = new BoardDto(Integer.parseInt(no), title, content, reg_id, reg_date);
			}
		} catch (SQLException e) {
			System.out.println("getBoardView() 오류 : "+query);
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return dto;
	}
	//게시글 조회
	public ArrayList<BoardDto> getBoardList(String select, String search, int start, int end){
		ArrayList<BoardDto> dtos = new ArrayList<>();
		String query = "select * from(\r\n" + 
				   	   "    select rownum as rnum, tbl.*\r\n" + 
				   	   "        from("+
					   "select no, title, content, reg_id, to_char(reg_date,'yyyy-mm-dd') reg_date\r\n" + 
					   "from freeboard_board\r\n" + 
					   "where "+select+" like '%"+search+"%'\r\n" + 
					   "and title != 'null'\r\n" +
					   "order by no desc"+
				       " ) tbl)\r\n" + 
				       "where rnum >="+start+" and rnum <= "+end+"";
		System.out.println(query);
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String reg_id = rs.getString("reg_id");
				String reg_date = rs.getString("reg_date");
				BoardDto dto = new BoardDto(no, title, content, reg_id, reg_date);
				dtos.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("getBoardList() 오류 : "+query);
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return dtos;
	}
	
	//게시글 저장
	public int boardSave(BoardDto dto) {
		int result = 0;
		String query = "insert into freeboard_board\r\n" + 
					   "(no, title, content, reg_id, reg_date)\r\n" +
					   "values\r\n" + 
					   "("+dto.getNo()+",'"+dto.getTitle()+"','"+dto.getContent()+"','"+dto.getReg_id()+"','"+dto.getReg_date()+"')";
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(query);
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println("boardSave() 오류 : "+ query );
			e.printStackTrace();
		} finally {
			DBConnection.DBClose(con, ps, rs);
		}
		return result;
	}
	
	//게시글 번호 생성
	public int getMaxNo(){
		int no =0;
		String query ="select (max(no)) as no \r\n" + 
					  "from freeboard_board";
		try {
			con = DBConnection.getConnection();
			ps  = con.prepareStatement(query);
			rs  = ps.executeQuery();
			if(rs.next()){
				no = rs.getInt("no");
				no++;
			}
		}catch(SQLException e) {
			System.out.println("getMaxNo() 오류:"+query);
			e.printStackTrace();
		}finally {
			DBConnection.DBClose(con, ps, rs);
		}			
		return no;
	}
	

}
