<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>자유게시판연습_이민국</title>
	<link href="css/common.css" rel="stylesheet">
	<link href="css/layout.css" rel="stylesheet" >		
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
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
		var comment = $('#comment').val();
		var t_no = $('input[name="t_no"]').val();
		$.ajax({
			url : "/test/commentSave",
			data : {
				comment : comment,
				t_no : t_no
			},
			type: "POST",
			dataType: "json",
			success : function(data) {
				if (data == 1) {
					alert("댓글 등록 성공!");
					$("#comment").val('');
					
				}
			}
		});
	}
	function goCommentList(){
		var t_no = $('input[name="t_no"]').val();
		$.ajax({
			url : "/test/commentList",
			data : {
				t_no : t_no
			},
			type: "GET",
			dataType: "json",
			success : function(data) {
				 // 서버에서 받은 데이터 활용하여 HTML 문자열 생성
	            for (var i = 0; i < data.length; i++) {
	                var regId = data[i].reg_id;
	                var comment = data[i].comment;
	             // 각 댓글에 대한 정보를 이미 존재하는 span 태그에 설정
	                $("#reg_id").text(regId);
	                $("#comment").text(comment);
	            }
			}
		});
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
								<textarea name="t_comment" id="comment" class="textArea_H250"></textarea>
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
									<span id="reg_id"></span>&nbsp;:&nbsp;<span id="comment"></span><br><br><br>
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
