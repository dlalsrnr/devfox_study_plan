<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp"%>
<script type="text/javascript">
$(function() {
    var result = '<c:out value="${result}" />'
    checkModal(result)
    history.replaceState({}, null, null)
    function checkModal(result) {
        if (result === '' || history.state) {
            return;
        }
        if (parseInt(result) > 0) {
            $(".modal-body").html("게시글 " + parseInt(result) + " 번이 등록되었습니다.")
        }
        $("#myModal").modal("show")
    }

    $("#regBtn").on("click", function() {
        self.location="/board/register"
    })
})
</script>
<!-- Begin Page Content -->
<div class="container-fluid">
    <!-- Page Heading -->
    <h1 class="h3 mb-2 text-gray-800">Tables</h1>
    <!-- DataTables Example -->
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h6 class="m-0 font-weight-bold text-primary">Board List Page
            <button id="regBtn" type="button" class="btn-primary btn-xs float-right">Register New Board</button></h6>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                            <th>수정일</th>
                        </tr>
                    </thead>
                    <c:forEach items="${list}" var="board">
                        <tr>
                            <td><c:out value="${board.bno}" /></td>
                            <td><a href='/board/get?bno=<c:out value="${board.bno}"/>'><c:out value="${board.title}" /></a></td>
                            <td><c:out value="${board.writer}" /></td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}" /></td>
                            <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate}" /></td>
                        </tr>
                    </c:forEach>
                </table>
                <!-- Modal-->
                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="myModalLabel">Modal Title</h5>
                                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">×</span>
                                </button>
                            </div>
                            <div class="modal-body">처리가 완료되었습니다.</div>
                            <div class="modal-footer">
                                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.container-fluid -->
<%@include file="../includes/footer.jsp"%>