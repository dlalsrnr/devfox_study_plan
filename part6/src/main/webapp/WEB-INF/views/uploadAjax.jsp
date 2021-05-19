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
    <div class='uploadResult'>
        <ul>
        </ul>
    </div>
    <button id='uploadBtn'>Upload</button>
    <div class='bigPictureWrapper'>
        <div class='bigPicture'>
        </div>
    </div>
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
                $(uploadResultArr).each(function (i, obj) {
                    let str = "<li>"
                    const fileCallPath = encodeURIComponent(obj.uploadPath + ((obj.image) ? "/s_" : "/") + obj.uuid + "_" + obj.fileName)
                    if (!obj.image) {
                        str += "<div><a href='/download?fileName=" + fileCallPath + "'><img src='/resources/img/attach.png'>" + obj.fileName + "</a>"
                        + "<span data-file=\"" + fileCallPath + "\" data-type='file'> x </span></div>"
                    } else {
                        const originPath = obj.uploadPath.replace(new RegExp(/\\/g), "/") + "/" + obj.uuid + "_" + obj.fileName
                        str += "<a href='#' onclick='showImage(\"" + originPath + "\")'><img src='/display?fileName=" + fileCallPath + "'></a>"
                        + "<span data-file=\"" + fileCallPath + "\" data-type='image'> x </span>"
                    }
                    str += "</li>"
                    uploadResult.append(str)
                })
            }

            $(".bigPictureWrapper").click(function(e) {
                $(".bigPicture").animate({width: '0%', height: '0%'}, 1000)
                setTimeout(() => {
                    $(this).hide()
                }, 1000);
            })

            $(".uploadResult").on("click", "span", function(e) {
                const targetFile = $(this).data("file")
                const type = $(this).data("type")
                console.log(targetFile)
                console.log($(this).data)
                $.ajax({
                    url: '/deleteFile',
                    data: {fileName: targetFile, type: type},
                    dataType: 'text',
                    type: 'post',
                    success: function (result) {
                        alert(result)
                        e.target.closest('li').remove()
                    }
                })
            })
        })

        function showImage(fileCallPath) {
            $(".bigPictureWrapper").css("display", "flex").show()
            $(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>").animate({width: '100%', height: '100%'}, 1000)
        }
    </script>
</body>
</html>