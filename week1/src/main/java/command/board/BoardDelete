package command.board;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import common.CommonExcute;

public class BoardDelete implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String no = request.getParameter("t_no");
		int result = dao.boardDelete(no);
		String msg = "게시글 삭제성공!";
		if(result!=1) msg = "게시글 삭제실패!";
		
		request.setAttribute("t_msg", msg);
		request.setAttribute("t_url", "freeboard");
	}

}
