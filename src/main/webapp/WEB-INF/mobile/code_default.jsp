<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,user-scalable=no">

    <title>我们制造最美的笑容</title>

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

    <link href="${pageContext.request.contextPath}/css/mobile/code.css" rel="stylesheet">

    <script type="text/javascript">

        function check_code() {

            var code = $("#code").val();
            var user_id = $("#user_id").val();
            var category_id = $("#category_id").val();

            alert(code);

        }

    </script>

</head>
<body>

<div class="viewDiv">

    <img src="${pageContext.request.contextPath}/images/mobile/code_default.png"
         style="width: 100%;height: auto;z-index: 0;">

    <div class="inputDiv">
        <input id="code" oninput="if(value.length>6)value=value.slice(0,6);" type="number" pattern="[0-9]*"/>
    </div>

    <div class="buttonDiv" onclick="check_code();">
        <img src="${pageContext.request.contextPath}/images/mobile/code_con.png"
             style="width: 100%;height: 100%"/>
    </div>

    <div style="display: none">
        <input id="user_id" value="${userCategory.userId}"/>
        <input id="category_id" value="${userCategory.categoryId}"/>
    </div>

</div>

</body>
</html>
