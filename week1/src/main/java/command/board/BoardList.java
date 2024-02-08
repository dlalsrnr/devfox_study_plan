package command.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import boardDao.BoardDao;
import boardDto.BoardDto;
import common.CommonExcute;
import common.CommonUtil;

public class BoardList implements CommonExcute {

	@Override
	public void excute(HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String select = request.getParameter("t_select");
		String search = request.getParameter("t_search");
		if(select == null) {
			select = "title";
			search = "";
		}
		
		/* paging 설정 start*/
	    int total_count = dao.getTotalCount(select,search);
	    int list_setup_count = 5;  //한페이지당 출력 행수 
	    int pageNumber_count = 3;  //한페이지당 출력 페이지 갯수
	   
	    String nowPage = request.getParameter("t_nowPage");
	    int current_page = 0; // 현재페이지 번호
	    int total_page = 0;    // 전체 페이지 수
	   
	    if(nowPage == null || nowPage.equals("")) current_page = 1; 
	    else current_page = Integer.parseInt(nowPage);
	   
	    total_page = total_count / list_setup_count;  
	    int rest =    total_count % list_setup_count;  
	    if(rest !=0) total_page = total_page + 1;
	   
	    int start = (current_page -1) * list_setup_count + 1;
	    int end   = current_page * list_setup_count;
	    /* paging 설정 end*/
		   
	    int order = total_count - (list_setup_count * (current_page - 1));
	   

		String displayPage = CommonUtil.pageListPost(current_page, total_page, pageNumber_count);
		
		ArrayList<BoardDto> dtos = dao.getBoardList(select, search, start, end);
		
		request.setAttribute("dtos", dtos);
		request.setAttribute("select", select);
		request.setAttribute("search", search);
		request.setAttribute("displayPage", displayPage);
		request.setAttribute("total_count", total_count);
		request.setAttribute("order", order);
	}

}
