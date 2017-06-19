<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,user-scalable=no">

    <title>${codeWebinfo.pageTitle}</title>

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

    <link href="${pageContext.request.contextPath}/css/mobile/code.css" rel="stylesheet">

    <script type="text/javascript">

        function check_code() {

            var code = $("#code").val();
            alert(code);

        }

    </script>

</head>
<body>

<div class="viewDiv">

    <img src="${pageContext.request.contextPath}/get/getMedia.do?id=${codeWebinfo.codeWebinfoId}&type=3"
         style="width: 100%;height: auto;z-index: 0;">

    <div class="inputDiv">
        <input id="code" oninput="if(value.length>6)value=value.slice(0,6);" type="number" pattern="[0-9]*"/>
    </div>

    <div class="buttonDiv" onclick="check_code();">
        <img src="${pageContext.request.contextPath}/get/getMedia.do?id=${codeWebinfo.codeWebinfoId}&type=4"
             style="width: 100%;height: 100%"/>
    </div>

    <div style="display: none">
        <input id="user_id" value="${codeWebinfo.userId}"/>
        <input id="category_id" value="${codeWebinfo.categoryId}"/>
    </div>

</div>

</body>
</html>
