package command.board;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;

public class BoardUpdateForm implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String no = request.getParameter("t_no");
		BoardDto dto = dao.getBoardView(no);
		request.setAttribute("dto", dto);
	}

}
