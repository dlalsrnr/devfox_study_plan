<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp"%>
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
    const bno = '<c:out value="${board.bno}"/>'

    $.getJSON("/board/getAttachList", {bno: bno},
        function (arr) {
            let str = ""
            $(arr).each(function(i, attach) {
                const fileCallPath = encodeURIComponent(attach.uploadPath + ((attach.fileType) ? "/s_" : "/") + attach.uuid + "_" + attach.fileName)
                attach.fileType = attach.fileType === "1" ? true : false
                str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'>"
                str += "<div>"
                str += "<span>" + attach.fileName + " </span>"
                str += "<button type='button' data-file=\"" + fileCallPath + "\" data-type='" + (attach.fileType ? "image" : "file") + "' class='btn btn-warning btn-cricle'><i class='fa fa-times'></i></button><br>"
                str += "<img src='" + (attach.fileType ? ("/display?fileName=" + fileCallPath) : "/resources/img/attach.png") + "'>"
                str += "</div></li>"
            })
            $(".uploadResult ul").html(str)
        }
    )
    $(".uploadResult").on("click", "button", function(e) {
        if(confirm("Remove this file? ")) {
            const targetLi = $(this).closest("li")
            targetLi.remove()
        }
    })

    const regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$")
    const maxSize = 10*1024*1024 // 10MB
    function checkExtenstion(fileName, fileSize) {
        if (fileSize >= maxSize) {
            alert("10MB 이하의 파일만 업로드 할 수 있습니다.")
            return false
        }
        if (regex.test(fileName)) {
            alert("업로드 할 수 없는 파일 타입입니다.")
            return false
        }
        return true
    }
    $("input[type='file']").change(function(e) {
        const formData = new FormData()
        const inputFile = $("input[name='uploadFile']")
        const files = inputFile[0].files
        for (let i = 0; i < files.length; i++) {
            if(!checkExtenstion(files[i].name, files[i].size))
                return false
            formData.append("uploadFile", files[i])
        }
        $.ajax({
            type: "post",
            url: "/uploadAjaxAction",
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (response) {
                console.log(response)
                showUploadResult(response)
            }
        })
    })
    function showUploadResult(uploadResultArr) { 
        if(!uploadResultArr || uploadResultArr.length === 0)
            return
        const uploadUL = $(".uploadResult ul")
        let str = ""
        $(uploadResultArr).each(function(i, obj){
            const fileCallPath = encodeURIComponent(obj.uploadPath + ((obj.image) ? "/s_" : "/") + obj.uuid + "_" + obj.fileName)
        
            str += "<li data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'>"
            str += "<div><span>" + obj.fileName + "</span>"
            str += "<button type='button' data-file=\"" + fileCallPath + "\" data-type='" + (obj.image ? "image" : "file") + "' class='btn btn-warning btn-cricle'><i class='fa fa-times'></i></button><br>"
            str += "<img src='" + (obj.image ? ("/display?fileName=" + fileCallPath) : "/resources/img/attach.png") + "'>"
            str += "</div></li>"
        })
        uploadUL.append(str)
    }
})
</script>
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
        } else {
            $("button[type='submit']").click(function(e) {
            e.preventDefault()
            let str = ""
            $(".uploadResult ul li").each(function(i, obj) {
                const jobj = $(obj)
                console.dir(jobj)
                str += "<input type='hidden' name='attachList[" + i +"].fileName' value='" + jobj.data("filename") + "'>"
                str += "<input type='hidden' name='attachList[" + i +"].uuid' value='" + jobj.data("uuid") + "'>"
                str += "<input type='hidden' name='attachList[" + i +"].uploadPath' value='" + jobj.data("path") + "'>"
                str += "<input type='hidden' name='attachList[" + i +"].fileType' value='" + jobj.data("type") + "'>"
            })
            formObj.append(str).submit()
        })
        }
        formObj.submit()
    })
})
</script>
<main>
    <div class="container-fluid">
        <h1 class="mt-4">Board</h1>
        <div class="card mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Board Modify Page</h6>
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
        <div class='bigPictureWrapper'>
            <div class='bigPicture'>
            </div>
        </div>
        <div class="card mb-4">
            <div class="card-header py-3">
                <h6 class="m-0 font-weight-bold text-primary">Files</h6>
            </div>
            <div class="card-body">
                <div class="form-group uploadDiv">
                    <input type="file" name="uploadFile" multiple>
                </div>
                <div class="uploadResult">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="../includes/footer.jsp"%>