package command.board;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;

public class MemberSave implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//비밀번호 암호화 작업
		try {
			password = dao.encryptSHA256(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String email = request.getParameter("email");
		String reg_date = request.getParameter("reg_date");
		
		BoardDto dto = new BoardDto(reg_date, id, name, password, email);
		
		int result = dao.memberSave(dto);
		String msg = "회원가입 성공!";
		if(result!=1) msg = "회원가입 실패!";
		
		request.setAttribute("t_msg", msg);
		request.setAttribute("t_url", "freeboard");
	}

}
