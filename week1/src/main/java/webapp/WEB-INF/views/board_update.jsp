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
	function goUpdate(){
		board.t_gubun.value="boardUpdate";
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
							<td class="th_left">
								<input name="title" value="${dto.getTitle()}"  class="input_500px" type="text">
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td class="th_left">
								<textarea name="content" class="textArea_H250">value="${dto.getContent()}"</textarea>
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td class="th_left">
								<input name="reg_id" value="${dto.getReg_id()}" class="input_300px" type="text">
							</td>
						</tr>
						<tr>
							<th>작성일</th>
							<td class="th_left">
								<input name="reg_date" value="${dto.getReg_date()}" class="input_300px" type="date">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<div class="btn_wrap">
				<input type="button" value="수정" onclick="goUpdate()" class="btn_ok">&nbsp;&nbsp;
				<input type="button" value="목록" onclick="location.href='freeboard'" class="btn_list">
			</div>
		</div>
	</div>
</body>
</html>
