package co.spring.test;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import boardDao.BoardDao;
import boardDto.BoardDto;
import command.board.BoardDelete;
import command.board.BoardList;
import command.board.BoardSave;
import command.board.BoardUpdate;
import command.board.BoardView;
import command.board.CommentDelete;
import command.board.CommentSave;
import command.board.CommentUpdate;
import command.board.CommentUpdateForm;
import command.board.MemberLogin;
import command.board.MemberLogout;
import command.board.MemberSave;
import common.CommonExcute;
import common.CommonUtil;

@Controller
public class FreeboardController {
	
	
	@GetMapping(value = "/commentList", produces = "application/json")
	@ResponseBody
	public ArrayList<BoardDto> commentList(@RequestParam("t_no") String t_no){
		BoardDao dao = new BoardDao();
		ArrayList<BoardDto> dtos = new ArrayList<>();
		dtos = dao.getCommentList(t_no);
		return dtos;
	}
	
	
	@PostMapping(value = "/commentSave", produces = "application/json")
	@ResponseBody
	public int commentSave(@RequestParam("t_no") int t_no, @RequestParam("comment") String comment, HttpServletRequest request) {
		BoardDao dao = new BoardDao();
		String reg_id = dao.getSessionName(request);
		String reg_date = CommonUtil.getToday();
		int no = dao.getMaxNo();
		int result = dao.commentSave2(t_no,comment,reg_id,reg_date,no);
		return result;
	}
	
	@PostMapping(value = "/checkid", produces = "application/json")
	@ResponseBody
	public  int idCheck(@RequestParam("mid") String mid) {
		return BoardDao.checkId(mid);
	}
	
	@RequestMapping("freeboard")
	public String boardList(HttpServletRequest request) {
		String gubun = request.getParameter("t_gubun");
		String viewPage = "";
		if(gubun == null) gubun="boardList";
		
		if(gubun.equals("boardList")) {
			CommonExcute board = new BoardList();
			board.excute(request);
			viewPage = "/board_list";
			
		} else if(gubun.equals("writeForm")) {
			String today = CommonUtil.getToday();
			request.setAttribute("today", today);
			viewPage = "/board_write";
			
		} else if(gubun.equals("boardSave")) {
			CommonExcute board = new BoardSave();
			board.excute(request);
			viewPage = "common_alert";
			
		} else if(gubun.equals("boardView")) {
			CommonExcute board = new BoardView();
			board.excute(request);
			viewPage = "/board_view";
			
		} else if(gubun.equals("boardUpdateForm")) {
			CommonExcute board = new BoardView();
			board.excute(request);
			viewPage = "/board_update";
			
		} else if(gubun.equals("boardUpdate")) {
			CommonExcute board = new BoardUpdate();
			board.excute(request);
			viewPage = "common_alert";
			
		} else if(gubun.equals("boardDelete")) {
			CommonExcute board = new BoardDelete();
			board.excute(request);
			viewPage = "common_alert";
			
		//댓글등록	
		} else if(gubun.equals("commentSave")) {
			CommonExcute board = new CommentSave();
			board.excute(request);
			viewPage = "common_alert_view";
		
		//댓글수정화면	
		} else if(gubun.equals("commentUpdateForm")) {
			CommonExcute board = new CommentUpdateForm();
			board.excute(request);
			viewPage = "/comment_update";
			
		//댓글수정	
		} else if(gubun.equals("commentUpdate")) {
			CommonExcute board = new CommentUpdate();
			board.excute(request);
			viewPage = "common_alert_view";
			
		//댓글삭제	
		} else if(gubun.equals("commentDelete")) {
			CommonExcute board = new CommentDelete();
			board.excute(request);
			viewPage = "common_alert_view";
		
		//로그인 화면
		} else if(gubun.equals("memberLogin")) {
			viewPage = "member_login";
			
		//로그인
		} else if(gubun.equals("login")) {
			CommonExcute board = new MemberLogin();
			board.excute(request);
			viewPage = "common_alert";
			
		//로그아웃
		} else if(gubun.equals("logout")) {
			CommonExcute board = new MemberLogout();
			board.excute(request);
			viewPage = "common_alert";
			
		//회원가입 화면
		} else if(gubun.equals("memberJoin")) {
			String today = CommonUtil.getToday();
			request.setAttribute("today", today);
			viewPage = "member_join";
			
		//회원저장
		} else if(gubun.equals("memberSave")) {
			CommonExcute board = new MemberSave();
			board.excute(request);
			viewPage = "common_alert";
		}
		return viewPage;
	}
	
}
