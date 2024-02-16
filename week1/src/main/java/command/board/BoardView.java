package command.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;

public class BoardView implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String sessionName = dao.getSessionName(request);
		String no = request.getParameter("t_no");
		BoardDto dto = dao.getBoardView(no);
		ArrayList<BoardDto> dtos = dao.getCommentList(no);
		String cno = "";
		BoardDto dto2 = dao.getCommentView(sessionName,cno);
		request.setAttribute("dto", dto);
		request.setAttribute("dto2", dto2);
		request.setAttribute("dtos", dtos);
	}

}
