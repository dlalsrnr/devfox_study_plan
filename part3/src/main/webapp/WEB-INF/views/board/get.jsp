<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp"%>
<script type="text/javascript">
$(function() {
    var operForm = $("#operForm")
    $("button[data-oper='modify']").on("click", function(e) {
        operForm.attr("action", "/board/modify").submit()
    })

    $("button[data-oper='list']").on("click", function(e) {
        operForm.find("#bno").remove()
        operForm.attr("action", "/board/list")
        operForm.submit()
    })
})
</script>
<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Board Read Page</h1>
    <!-- DataTales Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Board Read Page</h6>
        </div>
        <div class="card-body">
            <div class="form-group">
                <label>Bno</label>
                <input type="text" class="form-control form-control-user" name="bno"
                value='<c:out value="${board.bno}"/>' readOnly>
            </div>
            <div class="form-group">
                <label>Title</label>
                <input type="text" class="form-control form-control-user" name="title"
                value='<c:out value="${board.title}"/>' readOnly>
            </div>
            <div class="form-group">
                <label>Text area</label>
                <textarea class="form-control form-control-user" rows="3" name="content" readOnly><c:out value="${board.content}"/></textarea>
            </div>
            <div class="form-group">
                <label>Writer</label>
                <input type="text" class="form-control form-control-user" name="writer"
                value='<c:out value="${board.writer}"/>' readOnly>
            </div>
            <button data-oper="modify" class="btn btn-success">Modify</button>
            <button data-oper="list" class="btn btn-secondary">List</button>
            <form id="operForm" action="/board/modify" method="get">
                <input type="hidden" id='bno' name="bno" value='<c:out value="${board.bno}"/>'>
                <input type="hidden" id='pageNum' name="pageNum" value='<c:out value="${cri.pageNum}"/>'>
                <input type="hidden" id='amount' name="amount" value='<c:out value="${cri.amount}"/>'>
            </form>
        </div>
    </div>
</div>
<!-- /.container-fluid -->
<%@include file="../includes/footer.jsp"%>