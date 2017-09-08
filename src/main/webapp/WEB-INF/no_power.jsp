<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>权限出错 INPHOTO</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body style="padding-top: 70px;" onload="startTimes();">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<div style=" position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);">
    <img src="${pageContext.request.contextPath}/images/without_permission.png">
    <div style="    text-align: center;
    font-size: x-large;">
        <span id="secondes">5</span>&nbsp;秒后将自动返回，立即跳转请点击&nbsp;<a href="javascript:window.history.back();">这里</a>
    </div>
</div>

<script language="javascript" type="text/javascript">
    var timer;
    //启动跳转的定时器
    function startTimes() {
        timer = window.setInterval(showSecondes,1000);
    }

    var i = 5;
    function showSecondes() {
        if (i > 0) {
            i--;
            document.getElementById("secondes").innerHTML = i;
        }
        else {
            window.clearInterval(timer);
            window.history.back();
        }
    }

    //取消跳转
    function resetTimer() {
        if (timer != null && timer != undefined) {
            window.clearInterval(timer);
            //location.href = "index.html";
            window.history.back();
        }
    }
</script>


</body>
</html>