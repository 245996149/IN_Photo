<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Title</title>

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

</head>
<body style="margin: 0;background-color: white;">

<div style="position: absolute;top: 0;left: 0;right: 0;">

    <img src="${pageContext.request.contextPath}/images/mobile/code_default.png"
         style="width: 100%;height: auto;z-index: 0;">

    <div style="position: absolute; border: 2px solid #ff5252; left:31.9%; top:33.1%; right:31.9%; bottom:61.4%;  border-radius: 6px;z-index: 999;">
        <input id="code"
               style="height: 100%;width: 100%;text-align: center;font-size: xx-large; border: 0;color: #ff5252; "
               oninput="if(value.length>6)value=value.slice(0,6);" type="number" pattern="[0-9]*">
    </div>

    <div style="position:absolute; left:32.1%; top:84.4%; right:31.9%; bottom:10.4%; z-index:1;">
        <img src="${pageContext.request.contextPath}/images/mobile/code_con.png"
             style="width: 100%;height: 100%"/>
    </div>
</div>

</body>
</html>
