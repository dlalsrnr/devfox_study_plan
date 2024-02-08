package command.board;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;

public class BoardSave implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		int no = dao.getMaxNo();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String reg_id = request.getParameter("reg_id");
		String reg_date = request.getParameter("reg_date");
		BoardDto dto = new BoardDto(no, title, content, reg_id, reg_date);
		
		int result = dao.boardSave(dto);
		String msg = "게시글 등록성공!";
		if(result!=1) msg = "게시글 등록실패!";
		
		request.setAttribute("t_msg", msg);
		request.setAttribute("t_url", "freeboard");
	}

}
