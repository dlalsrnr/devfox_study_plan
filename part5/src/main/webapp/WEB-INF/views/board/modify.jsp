<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp"%>
<script type="text/javascript">
$(function() {
    const formObj = $("form")
    $("button").on("click", function(e) {
        e.preventDefault()
        const operation = $(this).data("oper")
        console.log(operation)
        if(operation === 'remove') {
            formObj.attr("action", "/board/remove")
        } else if(operation === 'list') {
            formObj.attr("action", "/board/list").attr("method", "get")
            const pageNumTag = $("input[name='pageNum']").clone()
            const amountTag = $("input[name='amount']").clone()
            const keywordTag = $("input[name='keyword']").clone()
            const typeTag = $("input[name='type']").clone()

            formObj.empty()
            formObj.append(pageNumTag)
            formObj.append(amountTag)
            formObj.append(keywordTag)
            formObj.append(typeTag)
        }
        formObj.submit()
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
            <form role="form" action="/board/modify" method="post">
                <div class="form-group">
                    <label>Bno</label>
                    <input type="text" class="form-control form-control-user" name="bno"
                    value='<c:out value="${board.bno}"/>' readOnly>
                </div>
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" class="form-control form-control-user" name="title"
                    value='<c:out value="${board.title}"/>'>
                </div>
                <div class="form-group">
                    <label>Text area</label>
                    <textarea class="form-control form-control-user" rows="3" name="content"><c:out value="${board.content}"/></textarea>
                </div>
                <div class="form-group">
                    <label>Writer</label>
                    <input type="text" class="form-control form-control-user" name="writer"
                    value='<c:out value="${board.writer}"/>' readOnly>
                </div>
                <%-- <div class="form-group">
                    <input type="hidden" class="form-control form-control-user" name="regdate"
                    value='<c:out value="${board.regdate}"/>' readOnly>
                </div>
                <div class="form-group">
                    <input type="hidden" class="form-control form-control-user" name="updateDate"
                    value='<c:out value="${board.updateDate}"/>' readOnly>
                </div> --%>
                <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
                <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
                <input type='hidden' name='type' value="<c:out value='${pageMaker.cri.type}'/>">
                <input type='hidden' name='keyword' value="<c:out value='${pageMaker.cri.keyword}'/>">
                <button type="submit" data-oper="modify" class="btn btn-success">Modify</button>
                <button type="submit" data-oper="remove" class="btn btn-danger">Remove</button>
                <button type="submit" data-oper="list" class="btn btn-secondary">List</button>
            </form>
        </div>
    </div>
</div>
<!-- /.container-fluid -->
<%@include file="../includes/footer.jsp"%>