package command.board;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;

public class BoardUpdate implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String no = request.getParameter("t_no");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String reg_id = request.getParameter("reg_id");
		String reg_date = request.getParameter("reg_date");
		BoardDto dto = new BoardDto(Integer.parseInt(no), title, content, reg_id, reg_date);
		int result = dao.boardUpdate(dto);
		
		String msg = "게시글 수정성공!";
		if(result!=1) msg = "게시글 수정실패!";
		
		request.setAttribute("t_msg", msg);
		request.setAttribute("t_url", "freeboard");
	}

}
