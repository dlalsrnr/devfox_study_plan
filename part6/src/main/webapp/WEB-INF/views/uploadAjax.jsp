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
    <button id='uploadBtn'>Upload</button>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script>
        $(function() {
            $("#uploadBtn").click(function(e) {
                const formData = new FormData()
                const inputFile = $("input[name='uploadFile']")
                const files = inputFile[0].files
                console.log(files)
                for (let i = 0; i < files.length; i++) {
                    formData.append("uploadFile", files[i]);
                }
                $.ajax({
                    type: "post",
                    url: "/uploadAjaxAction",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (response) {
                        alert("Uploaded")
                    }
                })
            })
        })
    </script>
</body>
</html>