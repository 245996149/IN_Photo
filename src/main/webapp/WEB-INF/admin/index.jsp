<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>IN Photo管理员系统 首页</title>
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
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<h1>你好，管理员！</h1>

<c:if test="${sessionScope.adminUser.adminName==null||sessionScope.adminUser.adminName==''}">
    <!-- 填写账户名Modal -->
    <div class="modal fade" id="nameModal" tabindex="-1" role="dialog" aria-labelledby="nameModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">请填写管理员用户名</h4>
                </div>
                <div class="modal-body">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon">用户名</span>
                        <input type="text" class="form-control"
                               id="adminName" pattern="[a-zA-Z]\w{5,17}$/">
                    </div>
                    <br/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="checkAdminName();">确认</button>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.adminUser.adminName!=null && sessionScope.adminUser.adminName!=''}">
    <c:if test="${sessionScope.adminUser.phone==null || sessionScope.adminUser.phone ==''}">
        <!-- 填写手机号Modal -->
        <div class="modal fade" id="phoneModal" tabindex="-1" role="dialog" aria-labelledby="phoneModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">请填写管理员手机号</h4>
                    </div>
                    <div class="modal-body">

                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">手机号</span>
                            <input type="number" class="form-control" id="phone" pattern="[0-9]*"
                                   oninput="checkPhone();">
                        </div>
                        <br/>
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">验证码</span>
                            <input type="number" class="form-control"
                                   id="phone_code" pattern="[0-9]*" disabled>
                            <span class="input-group-btn">
                                    <button class="btn btn-success" type="button" id="getCode" disabled
                                            onclick="sendCode();">发送验证码</button>
                                </span>
                        </div>
                        <br/>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="addAdminPhone();">确认</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</c:if>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript">

    $(function () {
        var admin = '${sessionScope.adminUser.adminName}';
        var phone = '${sessionScope.adminUser.phone}';

        if (admin == null || admin == "") {
            $('#nameModal').modal({
                show: true,
                backdrop: 'static'
            });
        }
        if (admin != null && admin != "") {
            if (phone == null || phone == "") {
                $('#phoneModal').modal({
                    show: true,
                    backdrop: 'static'
                });
            }

        }

    });

    function checkAdminName() {

        var adminName = $("#adminName");

        if (adminName.val() == null || adminName.val() == "") {
            alert("用户名不能为空");
            adminName.focus();
            return false;
        }

        var nameReg = /^[a-zA-Z]\w{5,17}$/;

        if (!adminName.val().match(nameReg)) {
            alert("用户名只能为6-18位的字母开头，包含数字、字母、下划线的组合");
            adminName.focus();
            adminName.val("");
            return false;
        }
        console.log(adminName);
        $.post(
            "addAdminName.do",
            {"admin_name": adminName.val()},
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.reload();
                } else {
                    alert(res.message);
                }
            }
        )
    }

    var wait = 60;

    function countDown(o) {
        if (wait == 0) {
            $("#getCode").removeAttr("disabled");
            $("#getCode").text("获取验证码");
            wait = 60;
        } else {
            $("#getCode").attr("disabled", true);
            $("#getCode").text("重新发送" + wait);
            wait--;
            setTimeout(function () {
                countDown(o)
            }, 1000);
        }
    }

    function checkPhone() {
        var input = $("#phone").val();

        // 判断userName的值符合手机号的正则表达式
        var phoneReg = /^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/;

        if (!input.match(phoneReg)) {

            $("#getCode").attr("disabled", true);
            return false;

        }

        $("#getCode").removeAttr("disabled");

    }

    function sendCode() {
        $("#phone_code").removeAttr("disabled");

        var phone = $("#phone").val();

        $.post(
            "sendPhoneCode.do",
            {"phone": phone},
            function (res) {
                if (!res.success) {
                    alert(res.message);
                } else {
                    countDown(this);
                }
            }
        );
    }

    function addAdminPhone() {
        var phone = $("#phone").val();
        var code = $("#phone_code").val();

        $.post(
            "checkPhoneCode.do",
            {
                "phone": phone,
                "code": code
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.reload();

                } else {
                    alert(res.message);
                    return false;
                }
            }
        )
    }

</script>
</body>
</html>