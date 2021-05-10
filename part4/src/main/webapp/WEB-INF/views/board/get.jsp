<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp"%>
<script src="/resources/js/reply.js"></script>
<script>
$(function() {
    const bnoValue = '<c:out value="${board.bno}"/>'
    const replyUL = $(".chat")
    showList(1)
    function showList(page) {
        console.log("show list " + page)
        replyService.getList({bno: bnoValue, page: page||1}, function(replyCnt, list) {
            console.log("replyCnt: " + replyCnt)
            console.log("list: " + list)
            console.log(list)
            if(page == -1) {
                pageNum = Math.ceil(replyCnt/10.0)
                showList(pageNum)
                return
            }
            let str=""
            if(list == null || list.length == 0) {
                return
            }
            list.forEach(element => {
                str += "<li class='left clearfix' data-rno='" + element.rno + "'>"
                str += "    <div><div class='header'><strong class='primary-font'>" + element.replyer + "</strong>"
                str += "        <small class='float-right text-muted'>" + replyService.displayTime(element.replyDate) + "</small></div>"
                str += "        <p>" + element.reply + "</p></div></li>"
            });
            replyUL.html(str)
            showReplyPage(replyCnt)
        })
        const modal = $("#replyModal")
        const modalInputReply = modal.find("input[name='reply']")
        const modalInputReplyer = modal.find("input[name='replyer']")
        const modalInputReplyDate = modal.find("input[name='replyDate']")
        const modalModBtn = $("#modalModBtn")
        const modalRemoveBtn = $("#modalRemoveBtn")
        const modalRegisterBtn = $("#modalRegisterBtn")

        $("#addReplyBtn").click(function() {
            modal.find("input").val("")
            modalInputReplyDate.closest("div").hide()
            modal.find("button[id != 'modalCloseBtn']").hide()
            modalRegisterBtn.show()
            modal.modal("show")
        })
        modalRegisterBtn.click(function(e) {
            const reply = {
                reply: modalInputReply.val(),
                replyer: modalInputReplyer.val(),
                bno: bnoValue
            }
            replyService.add(reply, function(result) {
                alert(result)
                modal.find("input").val("")
                modal.modal("hide")

                showList(-1)
            })
        })
        replyUL.on("click", "li", function(e) {
            const rno = $(this).data("rno")
            replyService.get(rno, function(reply){
                modalInputReply.val(reply.reply)
                modalInputReplyer.val(reply.replyer)
                modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly")
                modalInputReplyDate.closest("div").show()
                modal.data("rno", reply.rno)
                modal.find("button[id != 'modalCloseBtn']").hide()
                modalModBtn.show()
                modalRemoveBtn.show()
                modal.modal("show")
            })
        })
        modalModBtn.click(function(e){
            const reply = {rno: modal.data("rno"), reply: modalInputReply.val(), replyer: modalInputReplyer.val()}
            replyService.update(reply, function(result) {
                alert(result)
                modal.modal("hide")
                showList(pageNum)
            })
        })
        modalRemoveBtn.click(function(e) {
            const rno = modal.data("rno")
            replyService.remove(rno, function(result) {
                alert(result)
                modal.modal("hide")
                showList(pageNum)
            })
        })
    }

    let pageNum = 1
    const replyPageFooter = $(".card-footer")
    function showReplyPage(replyCnt) {
        let endNum = Math.ceil(pageNum / 10.0) * 10
        let startNum = endNum - 9
        let prev = startNum != 1
        let next = false
        if(endNum * 10 >= replyCnt)
            endNum = Math.ceil(replyCnt/10.0)
        else
            next = true
        let str = "<ul class='pagination float-right'>"
        if(prev) {
            str += "<li class='page-item'><a class='page-link' href='" + (startNum - 1) + "'>Previous</a></li>"
        }
        for (let i = startNum; i <= endNum; i++) {
            const active = pageNum == i ? "active" : ""
            str += "<li class='page-item " + active + "'><a class='page-link' href='" + i + "'>" + i + "</a></li>"
        }
        if (next) {
            str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) +"'>Next</a></li>"
        }
        str += "</ul></div>"
        console.log(str)
        replyPageFooter.html(str)
    }

    replyPageFooter.on("click", "li a", function(e) {
        e.preventDefault()
        console.log("page click")
        const targetPageNum = $(this).attr("href")
        console.log("targetPageNum : " + targetPageNum)
        pageNum = targetPageNum
        showList(pageNum)
    })
})
</script>
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
<!-- Modal-->
<div class="modal fade" id="replyModal" tabindex="-1" role="dialog" aria-labelledby="replyModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="replyModalLabel">Reply Modal</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>Reply</label>
                    <input class="form-control" name="reply" value="New Reply!!!!">
                </div>
                <div class="form-group">
                    <label>Replyer</label>
                    <input class="form-control" name="replyer" value="replyer">
                </div>
                <div class="form-group">
                    <label>Reply Date</label>
                    <input class="form-control" name="replyDate" value="">
                </div>
            </div>
            <div class="modal-footer">
                <button id="modalModBtn" class="btn btn-warning" type="button" data-dismiss="modal">Modify</button>
                <button id="modalRemoveBtn" class="btn btn-danger" type="button" data-dismiss="modal">Remove</button>
                <button id="modalRegisterBtn" class="btn btn-primary" type="button" data-dismiss="modal">Register</button>
                <button id="modalCloseBtn" class="btn btn-default" type="button" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

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
                <input type='hidden' name='pageNum' value="<c:out value='${pageMaker.cri.pageNum}'/>">
                <input type='hidden' name='amount' value="<c:out value='${pageMaker.cri.amount}'/>">
                <input type='hidden' name='type' value="<c:out value='${pageMaker.cri.type}'/>">
                <input type='hidden' name='keyword' value="<c:out value='${pageMaker.cri.keyword}'/>">
            </form>
        </div>
    </div>
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <i class="fa fa-comments fa-fw"></i> Reply
            <button id='addReplyBtn' class='btn btn-primary btn-sm float-right'>New Reply</button>
        </div>
        <div class="card-body">
            <ul class="chat">
            </ul>
        </div>
        <nav class='card-footer' aria-label="Page navigation">
        </nav>
    </div>
</div>
<!-- /.container-fluid -->
<%@include file="../includes/footer.jsp"%>