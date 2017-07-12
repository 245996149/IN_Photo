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

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

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
</head>

<body>
<div class="container">
    <div class="alert alert-danger" role="alert" style="display: none" id="error_message">...</div>
    <form class="form-signin" id="signin_form" action="login.do" method="post">
        <h2 class="form-signin-heading">IN Photo管理员系统</h2>
        <label for="inputUsername" class="sr-only">User name</label>
        <input type="email" id="inputUsername" class="form-control" placeholder="User name" required autofocus
               name="user_name">
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required name="password">
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me" checked="checked">
                记住登录状态 </label>
            <%--<label style="float: right;">--%>
                <%--<a href="#">忘记密码?</a>--%>
            <%--</label>--%>
        </div>
        <button class="btn btn-lg btn-primary btn-block" onclick="check_user();">登&nbsp;陆</button>
    </form>
</div>
<!-- /container -->

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="${pageContext.request.contextPath}/js/ie10-viewport-bug-workaround.js"></script>
<script type="text/javascript">

    function check_user() {
        var user_name = $("#inputUsername").val();
        var password = $("#inputPassword").val();
        var error_message = $("#error_message");
        var signin_form = $("#signin_form");

        $.post(
            "checkUser.do",
            {
                "user_name": user_name,
                "password": password
            },
            function (res) {
                if (res.success) {
                    error_message.text("user_name = " + user_name + " password = " + password + " 登陆成功");
                    error_message.show();
                    setTimeout(function () {
                        $("#error_message").hide();
                    }, 2000);

                    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                    var pathName = window.document.location.pathname;
                    //获取带"/"的项目名，如：/uimcardprj
                    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

                    signin_form.src = "login.do";
                    signin_form.submit();

                } else {
                    error_message.text("user_name = " + user_name + " " + res.message);
                    error_message.show();
                    setTimeout(function () {
                        $("#error_message").hide();
                    }, 5000);
                }
            }
        );


    }

</script>
</body>
</html>

