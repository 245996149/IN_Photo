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
    <title>INPHOTO管理系统</title>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

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

</head>

<body>
<div class="container">
    <div class="alert alert-danger" role="alert" style="display: none" id="error_message">...</div>
    <form class="form-signin" id="signin_form" method="post">
        <h2 class="form-signin-heading">重置密码</h2>
        <div class="form-group" id="password_div">
            <input type="password" id="password" class="form-control input-lg" placeholder="请输入密码" required
                   name="password" oninput="check_password();">
        </div>
        <div class="form-group" id="password_again_div">
            <input type="password" id="password_again" class="form-control input-lg" placeholder="请再次输入密码" required
                   name="password_again" oninput="check_password_again();">
        </div>
        <div class="form-group" id="code_div">
            <input type="text" id="code" class="form-control input-lg" placeholder="验证码" required
                   name="code">
        </div>
        <button type="button" class="btn btn-lg btn-primary btn-block" onclick="submit_a();">提&nbsp;交</button>
    </form>
</div>
<!-- /container -->

<script type="text/javascript">
    //判断浏览器是否支持date
    window.onload = function () {
//        $("#error_div").hide();
        var i = document.createElement('input');
        i.setAttribute('type', 'date');
        //浏览器不支持date类型
        if (i.type == 'text') {
            $("#error_div").show();
        }
    };

    function check_password() {

        var passReg = /^[a-zA-Z]\w{7,17}$/;

        var password = $("#password").val();
        var password_div = $("#password_div");

        if (!password.match(passReg)) {
            password_div.removeClass("has-success");
            password_div.addClass("has-error");
            return false;
        } else {
            password_div.removeClass("has-error");
            password_div.addClass("has-success");
            return true;
        }

    }

    function check_password_again() {

        var password = $("#password").val();
        var password_again = $("#password_again").val();
        var password_again_div = $("#password_again_div");

        if (password !== password_again) {
            password_again_div.removeClass("has-success");
            password_again_div.addClass("has-error");
            return false;
        } else {
            password_again_div.removeClass("has-error");
            password_again_div.addClass("has-success");
            return true;
        }

    }

    function submit_a() {

        if (!check_password() || !check_password_again()) {
            alert("请检查密码是否符合要求");
        }

        $.post(
            "resetPassword.do",
            {
                "password": $("#password").val(),
                "code": $("#code").val()
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.href = "toLogin.do";
                } else {
                    alert(res.message);
                }
            }
        )

    }

</script>
</body>
</html>

