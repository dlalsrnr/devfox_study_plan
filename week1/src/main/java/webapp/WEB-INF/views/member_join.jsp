<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	function goMemberSave(){
		board.t_gubun.value="memberSave";
		board.method="post";
		board.action="freeboard";
		board.submit();
	}
</script>
<script type="text/javascript">

function checkId(){
	var inputed = $('#id').val();
	$.ajax({
		url : "/test/checkid",
		data : {
			mid : inputed
		},
		type: "POST",
		dataType: "json",
		success : function(data) {
			if (data == 1) {
				$("#label1").css("color", "red").text("이미 사용중인 ID 입니다!");
			}else if(inputed == ""){
				$("#label1").css("color", "red").text("공백은 사용할 수 없습니다!");
			}else{
				$("#label1").css("color", "green").text("사용 가능한 ID 입니다!");
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
								<input name="id" id="id" value="" class="input_300px" type="text" oninput="checkId()"><label id="label1"></label>
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td class="th_left">
								<input name="name" value="" class="input_300px" type="text">
							</td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td class="th_left">
								<input name="password" value="" class="input_300px" type="password">
							</td>
						</tr>
						<tr>
							<th>이메일</th>
							<td class="th_left">
								<input name="email" value="" class="input_300px" type="text">
							</td>
						</tr>
						<tr>
							<th>회원가입일</th>
							<td class="th_left">
								<input name="reg_date" value="${today}" class="input_300px" type="text">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			</form>
			<div class="btn_wrap">
				<input type="button" value="회원등록" onclick="goMemberSave()" class="btn_ok">&nbsp;&nbsp;
				<input type="button" value="홈으로" onclick="location.href='freeboard'" class="btn_list">
			</div>
		</div>
	</div>
</body>
</html>
