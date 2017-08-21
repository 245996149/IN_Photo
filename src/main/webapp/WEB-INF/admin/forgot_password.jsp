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
    <title>IN Photo管理员系统</title>

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
        <input type="text" id="inputText" class="form-control input-lg" placeholder="请输入登录手机号/邮箱" required
               name="inputText">
        <br/>
        <div class="row">
            <div class="col-md-6" style="padding: 0;">
                <input type="text" id="code" class="form-control input-lg" placeholder="验证码" required
                       name="code">
            </div>
            <div class="col-md-6">
                <img src="createImage.do" alt="验证码" title="点击更换" id="code_image" onclick="change();"
                     style="height: 45px;"/>
            </div>
        </div>
        <br/>
        <a href="javascript:window.history.back()">返回登录页面</a>
        <br/>
        <br/>
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

    //刷新验证码
    function change() {
        $("#code_image").attr("src", "createImage.do?date=" + new Date().getTime());
    }

    function submit_a() {

        var input_text = $("#inputText");
        var code = $("#code");

        var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        var phoneReg = /^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/;

        if (!input_text.val().match(phoneReg) && !input_text.val().match(emailReg)) {
            alert("请确认手机号、邮箱地址是否正确");
            return false;
        }

        if (code.val() === "" || code.val().length !== 4) {
            alert("请输入正确的验证码");
            change();
            return false;
        }

        var type;

        if (input_text.val().match(phoneReg)) {
            type = 1;
        } else if (input_text.val().match(emailReg)) {
            type = 2;
        }

        $.post(
            "sendForgotPasswordCode.do",
            {
                "input_text": input_text.val(),
                "type": type,
                "code": code.val()
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.href = "toResetPassword.do";
                } else {
                    alert(res.message);
                }
            }
        )

    }

</script>
</body>
</html>

