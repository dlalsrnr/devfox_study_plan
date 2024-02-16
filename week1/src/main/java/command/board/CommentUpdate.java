package command.board;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;
import common.CommonUtil;

public class CommentUpdate implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String t_no = request.getParameter("t_no");
		String cno = request.getParameter("t_cno");
		String comment = request.getParameter("t_comment");
		String reg_date = CommonUtil.getToday();
		BoardDto dto = new BoardDto(comment, reg_date, Integer.parseInt(cno));
		int result = dao.commentUpdate(dto);
		
		String msg = "댓글 수정성공!";
		if(result!=1) msg = "댓글 수정실패!";
		
		request.setAttribute("t_msg", msg);
		request.setAttribute("t_url", "freeboard");
		request.setAttribute("t_gubun", "boardView");
		request.setAttribute("t_no", t_no);
	}

}
