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

<!-- Modal -->
<div class="modal fade" id="nameModal" tabindex="-1" role="dialog" aria-labelledby="nameModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>--%>
                <%--</button>--%>
                <h4 class="modal-title" id="myModalLabel">请填写管理员用户名</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="${pageContext.request.contextPath}/admin/clientManage/toAddClient.do"
                      id="addFrom">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="categoryName_span">用户名</span>
                        <input type="text" class="form-control" aria-describedby="categoryName_span"
                               id="adminName" name="adminName" pattern="[a-zA-Z]\w{5,17}$/">
                    </div>
                    <br/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="checkAdminName();">确认</button>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript">

    $(function () {
        var admin = '${sessionScope.adminUser.adminName}';
        if (admin == null || admin == "") {
            $('#nameModal').modal({
                show: true,
                backdrop: 'static'
            });
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
    }

</script>
</body>
</html>