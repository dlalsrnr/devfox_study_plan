<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	function goLogin(){
		board.t_gubun.value="login";
		board.method="post";
		board.action="freeboard";
		board.submit();
	}
	
	function goJoin(){
		board.t_gubun.value="memberJoin";
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
							<th>아이디</th>
							<td class="th_left">
								<input name="id"  class="input_300px" type="text">
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td class="th_left">
								<input name="password"  class="input_300px" type="password">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<div class="btn_wrap">
				<input type="button" value="로그인" onclick="goLogin()" class="btn_ok">&nbsp;&nbsp;
				<input type="button" value="회원가입" onclick="goJoin()" class="btn_ok">&nbsp;&nbsp;
				<input type="button" value="홈으로" onclick="location.href='freeboard'" class="btn_list">
			</div>
		</div>
	</div>
</body>
</html>
