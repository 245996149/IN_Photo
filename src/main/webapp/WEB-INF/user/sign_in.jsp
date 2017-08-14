<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">
    <title>IN Photo管理系统</title>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="${pageContext.request.contextPath}/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${pageContext.request.contextPath}/css/user/sign_in.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="${pageContext.request.contextPath}/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="${pageContext.request.contextPath}/js/ie10-viewport-bug-workaround.js"></script>

    <script src="${pageContext.request.contextPath}/js/sign_in.js"></script>

</head>

<body>
<div class="container">
    <div class="alert alert-danger" role="alert" style="display: none" id="error_message">...</div>
    <form class="form-signin" id="signin_form" method="post">
        <h2 class="form-signin-heading">IN Photo管理系统</h2>
        <div class="input-group input-group-lg">
            <div class="input-group-btn">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false" id="inputButton">
                    <c:choose>
                        <c:when test="${login_type=='1'}">手机号</c:when>
                        <c:when test="${login_type=='2'}">邮箱</c:when>
                        <c:otherwise>用户名</c:otherwise>
                    </c:choose>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="javascript:void(0);" onclick="selectType(0,'用户名');">用户名</a></li>
                    <li><a href="javascript:void(0);" onclick="selectType(1,'手机号')">手机号</a></li>
                    <li><a href="javascript:void(0);" onclick="selectType(2,'邮箱')">邮箱</a></li>
                </ul>
            </div><!-- /btn-group -->
            <input type="text" hidden id="login_type" name="login_type"
            <c:choose>
            <c:when test="${login_type!=''}">
                   value="${login_type}"
            </c:when>
            <c:otherwise>
                   value="0"
            </c:otherwise>
            </c:choose>>
            <input type="text" class="form-control" name="input_text" id="input_text" placeholder="下拉选择登录方式"
                   value="${input_text}">
        </div><!-- /input-group -->
        <br/>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control input-lg" placeholder="Password" required
               name="password" value="${password}">
        <div class="checkbox">
            <label> <input type="checkbox" id="remLogin" value="remember-me"
            <c:if test="${remLogin=='0'}"> checked </c:if>
            > 记住登录状态 </label>
        </div>
        <button type="button" class="btn btn-lg btn-primary btn-block" onclick="check_user();">登&nbsp;陆</button>
    </form>
</div>
<!-- /container -->

</body>
</html>

