package co.spring.freeboard;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import command.board.BoardDelete;
import command.board.BoardList;
import command.board.BoardSave;
import command.board.BoardUpdate;
import command.board.BoardView;
import command.board.CommentSave;
import command.board.MemberLogin;
import command.board.MemberLogout;
import command.board.MemberSave;
import common.CommonExcute;

@Controller
public class FreeboardController {

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
