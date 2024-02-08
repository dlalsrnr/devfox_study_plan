package command.board;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import boardDao.BoardDao;
import common.CommonExcute;

public class MemberLogin implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		try {
			password = dao.encryptSHA256(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String name = dao.getLoginInfo(id, password);
		String msg = "";
		
		if(name.equals("")) {
			msg="아이디 혹은 비밀번호가 정확하지 않거나 존재하지 않는 아이디입니다.";
			request.setAttribute("t_url", "freeboard");
		}else {
			msg=name+"님 환영합니다!";
			HttpSession session = request.getSession();
			session.setAttribute("sessionId", id);
			session.setAttribute("sessionName", name);
			
			request.setAttribute("t_url", "freeboard");
			
		}
		request.setAttribute("t_msg", msg);
	}

}
