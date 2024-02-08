package command.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import common.CommonExcute;

public class MemberLogout implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("sessionName");
		session.invalidate();
		
		String msg = name+"님 로그아웃 되었습니다!";
		if(name == null) msg = "로그아웃 되었습니다!";
		
		request.setAttribute("t_msg", msg);
		request.setAttribute("t_url", "freeboard");
	}

}
