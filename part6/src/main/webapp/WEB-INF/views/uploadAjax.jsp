<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<h1>Upload with Ajax</h1>
    <div class='uploadDiv'>
        <input type='file' name='uploadFile' multiple>
    </div>
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
        }
        .uploadResult ul li img {
            width: 50px;
        }
    </style>
    <div class='uploadResult'>
        <ul>
        </ul>
    </div>
    <button id='uploadBtn'>Upload</button>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script>
        $(function() {
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
            const cloneObj = $(".uploadDiv").clone()
            $("#uploadBtn").click(function(e) {
                const formData = new FormData()
                const inputFile = $("input[name='uploadFile']")
                const files = inputFile[0].files
                console.log(files)
                for (let i = 0; i < files.length; i++) {
                    if (!checkExtenstion(files[i].name, files[i].size))
                        return false
                    formData.append("uploadFile", files[i]);
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
                        showUploadedFile(response)
                        $(".uploadDiv").html(cloneObj.html())
                    }
                })
            })
            const uploadResult = $(".uploadResult ul")
            function showUploadedFile(uploadResultArr) { 
                let str = "<li>"
                $(uploadResultArr).each(function (i, obj) {
                    if (!obj.image) {
                        str += "<img src='/resources/img/attach.png'>" + obj.fileName
                    } else {
                        const fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName)
                        str += "<img src='/display?fileName=" + fileCallPath + "'>"
                    }
                    str += "</li>"
                })
                uploadResult.append(str)
            }
        })
    </script>
</body>
</html>