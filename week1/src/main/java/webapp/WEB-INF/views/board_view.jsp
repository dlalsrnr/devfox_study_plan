<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>자유게시판연습_이민국</title>
	<link href="css/common.css" rel="stylesheet">
	<link href="css/layout.css" rel="stylesheet" >		
	<script type="text/javascript">

	</script>
</head>
<body>
<script>
	function goUpdateForm(){
		board.t_gubun.value="boardUpdateForm";
		board.method="post";
		board.action="freeboard";
		board.submit();
	}
	function goDelete(){
		if(confirm("정말 삭제하시겠습니까?")){
			board.t_gubun.value="boardDelete";
			board.method="post";
			board.action="freeboard";
			board.submit();
		}
	}
	function goCommentUpdateForm(){
		board.t_gubun.value="commentUpdateForm";
		board.method="post";
		board.action="freeboard";
		board.submit();
	}
	function goCommentDelete(){
		if(confirm("정말 삭제하시겠습니까?")){
			board.t_gubun.value="commentDelete";
			board.method="post";
			board.action="freeboard";
			board.submit();
		}
	}
	function goCommentSave(){
		board.t_gubun.value="commentSave";
		board.method="post";
		board.action="freeboard";
		board.submit();
	}
</script>
	<div class="container">

		<div class="leftmargin">
			<h1>자유게시판 연습</h1>
		</div>		
		<div class="write_wrap">
			<form name="board">
			<input type="hidden" name="t_no" value="${dto.getNo()}">
			<input type="hidden" name="t_cno" value="${dto2.getNo()}">
			<input type="hidden" name="t_gubun">
			<div class="board_list">
				<table class="board_table">
					<colgroup>
						<col width="15%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th>번호</th>
							<td class="th_left">${dto.getNo()}</td>
						</tr>
						<tr>
							<th>제목</th>
							<td class="th_left">${dto.getTitle()}</td>
						</tr>
						<tr>
							<th>내용</th>
							<td class="th_left">
								<textarea name="content" class="textArea_H250" readonly>${dto.getContent()}</textarea>
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td class="th_left">${dto.getReg_id()}</td>
						</tr>
						<tr>
							<th>작성일</th>
							<td class="th_left">${dto.getReg_date()}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="board_list comment">
				<table class="board_table">
					<colgroup>
						<col width="15%">
						<col width="*">
						<col width="20%">
					</colgroup>
					<tbody>
						<tr>
							<th>댓글작성</th>
							<td class="th_left">
								<textarea name="t_comment" class="textArea_H250"></textarea>
							</td>
							<td class="buttontop"><input type="button" onClick="goCommentSave()" value="댓글등록" class="cbutton"></td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<c:if test="${dtos.size() > 0 }">
				<div class="board_list comment_box">
					<table class="board_table">
						<colgroup>
							<col width="15%">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<th>댓글</th>
								<td class="th_left">
								<c:forEach items="${dtos}" var="dto">
									${dto.getReg_id()}&nbsp;:&nbsp;${dto.getComment()}<br><br><br>
								</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</c:if>
			<div class="btn_wrap">
				<input type="button" onClick="location.href='freeboard'" value="목록" class="btn_list">
				<c:if test="${dto.getReg_id() eq sessionName}">
					<input type="button" onClick="goUpdateForm()" value="수정" class="btn_list">
					<input type="button" onClick="goDelete()" value="삭제" class="btn_list">
				</c:if>
				<c:if test="${dto2.getReg_id() eq sessionName and dtos.size() > 0 and sessionName ne null}">
					<input type="button" onClick="goCommentUpdateForm()" value="댓글수정" class="btn_list">
					<input type="button" onClick="goCommentDelete()" value="댓글삭제" class="btn_list">
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>
