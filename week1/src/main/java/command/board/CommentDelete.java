package command.board;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import common.CommonExcute;

public class CommentDelete implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String t_no = request.getParameter("t_no");
		String cno = request.getParameter("t_cno");
		int result = dao.commentDelete(cno);
		String msg = "댓글 삭제성공!";
		if(result!=1) msg = "댓글 삭제실패!";
		
		request.setAttribute("t_msg", msg);
		request.setAttribute("t_url", "freeboard");
		request.setAttribute("t_gubun", "boardView");
		request.setAttribute("t_no", t_no);
	}

}
