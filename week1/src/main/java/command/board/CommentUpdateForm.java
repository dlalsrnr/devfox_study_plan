package command.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;

public class CommentUpdateForm implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String no = request.getParameter("t_no");
		String cno = request.getParameter("t_cno");
		String sessionName = dao.getSessionName(request);
		BoardDto dto = dao.getBoardView(no);
		BoardDto dto2 = dao.getCommentView(sessionName,cno);
		ArrayList<BoardDto> dtos = dao.getCommentList(no);
		request.setAttribute("dto", dto);
		request.setAttribute("dto2", dto2);
		request.setAttribute("dtos", dtos);
	}

}
