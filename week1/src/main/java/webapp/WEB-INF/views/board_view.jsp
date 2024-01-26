<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>자유게시판연습_이민국</title>
	<link href="/spr/css/common.css" rel="stylesheet">
	<link href="/spr/css/layout.css" rel="stylesheet" >		
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
</script>
	<div class="container">

		<div class="leftmargin">
			<h1>자유게시판 연습</h1>
		</div>		
		<div class="write_wrap">
			<form name="board">
			<input type="hidden" name="t_no" value="${dto.getNo()}">
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
			</form>
			<div class="btn_wrap">
				<input type="button" onClick="location.href='freeboard'" value="목록" class="btn_list">
				<input type="button" onClick="goUpdateForm()" value="수정" class="btn_list">
				<input type="button" onClick="goDelete()" value="삭제" class="btn_list">
			</div>
		</div>
	</div>
</body>
</html>
