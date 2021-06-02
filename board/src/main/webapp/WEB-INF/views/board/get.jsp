<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<%@include file="../includes/header.jsp"%>
<script src="/resources/js/reply.js"></script>
<style>
.uploadResult {
    width: 100%;
    background-color: gray;
}
.uploadResult ul {
    display: flex;
    flex-flow: row;
    justify-content: center;
    align-items: center;
}
.uploadResult ul li {
    list-style: none;
    padding: 10px;
    align-content: center;
    text-align: center;
}
.uploadResult ul li img {
    width: 100px;
}
.uploadResult ul li span {
    color: white;
}
.bigPictureWrapper {
    position: absolute;
    display: none;
    justify-content: center;
    align-items: center;
    top: 0%;
    width: 100%;
    height: 100%;
    background-color: gray;
    z-index: 100;
    background: rgba(255, 255, 255, 0.5);
}
.bigPicture {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
}
.bigPicture img {
    width: 100%;
    height: 100%;
}
</style>
<script>
$(function() {
    const bnoValue = '<c:out value="${board.bno}"/>'
    const replyUL = $(".chat")

    $.getJSON("/board/getAttachList", {bno: bnoValue},
        function (arr) {
            let str = ""
            $(arr).each(function(i, attach) {
                const fileCallPath = encodeURIComponent(attach.uploadPath + ((attach.fileType) ? "/s_" : "/") + attach.uuid + "_" + attach.fileName)
                attach.fileType = attach.fileType === "1" ? true : false
                str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'>"
                str += "<div>"
                str += attach.fileType ? "" : "<span>" + attach.fileName + "</span><br>"
                str += "<img src='" + (attach.fileType ? ("/display?fileName=" + fileCallPath) : "/resources/img/attach.png") + "'>"
                str += "</div></li>"
            })
            $(".uploadResult ul").html(str)
        }
    )

    $(".uploadResult").on("click", "li", function(e) {
        const liObj = $(this)
        let path = liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename")
        path = path.replace(new RegExp(/\\/g), "/")
        console.log(path)
        if (liObj.data("type")) {
            showImage(path)
            console.log(path)
        } else {
            path = encodeURIComponent(path)
            self.location = "/download?fileName=" + path
        }
    })

    function showImage(fileCallPath) {
        $(".bigPictureWrapper").css("display", "flex").show()
        $(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>").animate({width: '100%', height: '100%'}, 1000)
    }

    $(".bigPictureWrapper").click(function(e) {
        $(".bigPicture").animate({width: '0%', height: '0%'}, 1000)
        setTimeout(() => {
            $(this).hide()
        }, 1000);
    })

    showList(1)
    function showList(page) {
        console.log("show list " + page)
        replyService.getList({bno: bnoValue, page: page||1}, function(replyCnt, list) {
            console.log("replyCnt: " + replyCnt)
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

        let replyer = null
        <sec:authorize access="isAuthenticated()">
            replyer = '<sec:authentication property="principal.username" />'
        </sec:authorize>
        const csrfHeaderName = "${_csrf.headerName}"
        const csrfTokenValue = "${_csrf.token}"

        $("#addReplyBtn").click(function() {
            modal.find("input").val("")
            modal.find("input[name='replyer']").val(replyer)
            modalInputReplyDate.closest("div").hide()
            modal.find("button[id != 'modalCloseBtn']").hide()
            modalRegisterBtn.show()
            modal.modal("show")
        })

        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
        })

        modalRegisterBtn.unbind("click")
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
        modalModBtn.unbind("click")
        modalModBtn.click(function(e){
            const originalReplyer = modalInputReplyer.val()
            const reply = {rno: modal.data("rno"), reply: modalInputReply.val(), replyer: originalReplyer}
            if (replyer != originalReplyer) {
                alert("자신이 작성한 댓글만 수정이 가능합니다.")
                modal.modal("hide")
                return
            }
            replyService.update(reply, function(result) {
                alert(result)
                modal.modal("hide")
                showList(pageNum)
            })
        })
        modalRemoveBtn.unbind("click")
        modalRemoveBtn.click(function(e) {
            const rno = modal.data("rno")
            if (!replyer) {
                alert("로그인후 삭제가 가능합니다.")
                modal.modal("hide")
                return
            }
            const originalReplyer = modalInputReplyer.val()
            if (replyer != originalReplyer) {
                alert("자신이 작성한 댓글만 삭제가 가능합니다.")
                modal.modal("hide")
                return
            }
            replyService.remove(rno, function(result) {
                alert(result)
                modal.modal("hide")
                replyService.getList({bno: bnoValue, page: pageNum}, function(replyCnt, list) {
                    pageNum = list.length === 0 ? pageNum - 1 : pageNum
                    showList(pageNum)
                })
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
<!--Reply  Modal-->
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
<main>
    <div class="container-fluid">
        <h1 class="mt-4">Board</h1>
        <div class="card mb-4">
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
                <sec:authentication property="principal" var="pinfo" />
                <sec:authorize access="isAuthenticated()">
                    <c:if test="${pinfo.username eq board.writer}">
                        <button data-oper="modify" class="btn btn-success">Modify</button>
                    </c:if>
                </sec:authorize>
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
        <div class='bigPictureWrapper'>
            <div class='bigPicture'>
            </div>
        </div>
        <div class="card mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Files</h6>
            </div>
            <div class="card-body">
                <div class="uploadResult">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <i class="fa fa-comments fa-fw"></i> Reply
                <sec:authorize access="isAuthenticated()">
                    <button id='addReplyBtn' class='btn btn-primary btn-sm float-right'>New Reply</button>
                </sec:authorize>
            </div>
            <div class="card-body">
                <ul class="chat">
                </ul>
            </div>
            <nav class='card-footer' aria-label="Page navigation">
            </nav>
        </div>
    </div>
</main>
<%@include file="../includes/footer.jsp"%>