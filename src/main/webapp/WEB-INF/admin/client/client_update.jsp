<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>客户信息更新-INPHOTO管理员系统</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<div class="row">
    <div class="col-md-12">

    </div>
</div>

<div class="row">
    <div class="col-md-offset-4 col-md-4 col-xs-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><span>客户信息更新</span>
                    <button type="button" class="btn btn-danger btn-xs" style="float: right;"
                            onclick="resetPassword(${user.userId});">
                        重置密码
                    </button>
                </h3>
            </div>
            <div class="panel-body">
                <input type="text" hidden id="user_id" name="user_id" value="${user.userId}">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" id="user_name">用户名</span>
                    <input type="text" class="form-control" placeholder="客户未填写用户名" aria-describedby="user_name"
                           readonly
                           value="${user.userName}">
                </div>
                <br/>
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="phone">手机号</span>
                    <input type="text" class="form-control" placeholder="客户未填写手机号" aria-describedby="phone" readonly
                           value="${user.phone}">
                </div>
                <br/>
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="email">邮箱</span>
                    <input type="text" class="form-control" placeholder="客户未填写邮箱" aria-describedby="email" readonly
                           value="${user.email}">
                </div>
                <br/>
                <form id="check_form" method="post">
                    <div class=" input-group input-group-lg">
                        <span class="input-group-addon" id="company_span">公司</span>
                        <input type="text" class="form-control" placeholder="客户未填写公司" aria-describedby="company"
                               value="${user.company}" id="company">
                    </div>
                    <br/>
                    <label>归属者</label>
                    <select class="form-control" id="admin_id">
                        <c:forEach items="${adminList}" var="al">
                            <option
                                    <c:if test="${user.adminId==al.adminId}">selected</c:if>
                                    value="${al.adminId}">${al.adminName} ${al.phone} ${al.email}</option>
                        </c:forEach>
                    </select>
                </form>
                <br/>
                <div class="row">
                    <div class="col-md-12" style="text-align: center">
                        <div class="btn-group" role="group" aria-label="...">
                            <button type="button" class="btn btn-danger" onclick="window.history.back();">返回</button>
                            <button type="button" class="btn btn-success" onclick="sendData();">确认</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">

    function sendData() {

        var forms = $("#check_form");
        if (!judgeNull(forms)) {
            alert("有必填项为空！");
            return false;
        }

        $.post(
            "updateClient.do",
            {
                "user_id": $("#user_id").val(),
                "admin_id": $("#admin_id").val(),
                "company": $("#company").val()
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.href = 'toClient.do';
                } else {
                    alert(res.message);
                }
            }
        );

    }


    /*判断表单中是否有空*/
    function judgeNull(a) {
        var forms = a.find('input');
        //alert(forms.length);
        for (var i = 0; i < forms.length; i++) {
            if (forms[i].value == "") {
                forms[i].focus();
                //alert("有选项为空");
                return false;
            }
        }
        return true;
    }

    function resetPassword(user_id) {

        if (!confirm("确定要重置该客户的密码吗？点击确认后，系统会将新的密码发送至客户邮箱")) {
            return false;
        }

//        alert(user_id);

        $.post(
            "resetPassword.do",
            {
                "user_id": user_id
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.href = 'toClient.do';
                } else {
                    alert(res.message);
                }
            }
        );

    }
</script>
</body>
</html>