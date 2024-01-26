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
	function goSave(){
		board.t_gubun.value="boardSave";
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
			<input type="hidden" name="t_gubun">
			<div class="board_list">
				<table class="board_table">
					<colgroup>
						<col width="15%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th>제목</th>
							<td class="th_left">
								<input name="title"  class="input_500px" type="text">
							</td>
						</tr>
						<tr>
							<th>내용</th>
							<td class="th_left">
								<textarea name="content" class="textArea_H250"></textarea>
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td class="th_left">
								<input name="reg_id"  class="input_300px" type="text">
							</td>
						</tr>
						<tr>
							<th>작성일</th>
							<td class="th_left">
								<input name="reg_date"  class="input_300px" type="date">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<div class="btn_wrap">
				<input type="button" value="등록" onclick="goSave()" class="btn_ok">&nbsp;&nbsp;
				<input type="button" value="목록" onclick="location.href='freeboard'" class="btn_list">
			</div>
		</div>
	</div>
</body>
</html>
