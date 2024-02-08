<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--
	******************************************** 
		title : 풀스텍 홍길동
	******************************************** 
 -->	
	<title>자유게시판연습_이민국</title>
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css">	
	<link href="css/common.css" rel="stylesheet">
	<link href="css/layout.css" rel="stylesheet" >	
</head>
<body>
<script>
function goSearch(){
	board.method="post";
	board.action="freeboard";
	board.submit();
}
function goLogin(){
	view.t_gubun.value = "memberLogin";
	view.method="post";
	view.action="freeboard";
	view.submit();
}
function goLogout(){
	view.t_gubun.value = "logout";
	view.method="post";
	view.action="freeboard";
	view.submit();
}
function goPage(pageNumber){
	board.t_nowPage.value = pageNumber;
	board.method="post";
	board.action="freeboard";
	board.submit();
}
function goView(no){
	view.t_no.value=no;
	view.t_gubun.value = "boardView";
	view.method="post";
	view.action="freeboard";
	view.submit();
}
function gowriteForm(){
	view.t_gubun.value = "writeForm";
	view.method="post";
	view.action="freeboard";
	view.submit();
}
</script>
<form name="view">
	<input type="hidden" name="t_no">
	<input type="hidden" name="t_gubun">
</form>
	<div class="container">

		<div class="leftmargin">
			<h1>자유게시판 연습</h1>
		</div>		
		<div class="search_wrap">
			<div class="record_group">
				<p>총게시글 : <span>${dtos.size()}</span>건</p>
			</div>
			<form name="board">
			<input type="hidden" name="t_nowPage">	
			<div class="search_group">
				<select name="t_select" class="select">
					<option value="title" <c:if test="${select eq 'title'}">selected</c:if>>제목</option>
					<option value="reg_id" <c:if test="${select eq 'reg_id'}">selected</c:if>>작성자</option>
				</select>
				<input type="text" name="t_search" value="${search}" class="search_word">
				<button onclick="goSearch()" class="btn_search"><i class="fa fa-search"></i><span class="sr-only">검색버튼</span></button>
			</div>
			</form>
		</div>
	</div>
	<div class="board_list">
		<table class="board_table">
			<colgroup>
				<col width="10%">
				<col width="50%">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${dtos}" var="dto">
				<tr>
					<td><a href="freeboard?t_gubun=boardView&t_no=${dto.getNo()}">${dto.getNo()}</a></td>
					<td><a href="javascript:goView('${dto.getNo()}')">${dto.getTitle()}</a></td>
					<td>${dto.getReg_id()}</td>
					<td>${dto.getReg_date()}</td>
				</tr>	
			</c:forEach>
			</tbody>
		</table>
		<div class="paging">
			${displayPage}
			<c:if test="${not empty sessionId}">
				<a href="javascript:gowriteForm()" class="write">게시글등록</a>
			</c:if>
			<c:if test="${empty sessionId}">
				<a href="javascript:goLogin()" class="write">로그인</a>
			</c:if>
			<c:if test="${not empty sessionId}">
				<a href="javascript:goLogout()" class="write">로그아웃</a>
			</c:if>
		</div>
	</div>
 </body>
</html>
