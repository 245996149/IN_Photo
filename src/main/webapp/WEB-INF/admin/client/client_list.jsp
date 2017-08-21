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
    <title>IN Photo管理员系统</title>
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

    <link href="${pageContext.request.contextPath}/css/user/table.css" rel="stylesheet">

</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h1>客户列表
                <small>列出你有权限修改的客户名单</small>
            </h1>
        </div>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">数据列表
                    <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
                        添加客户
                    </button>
                </h3>
            </div>
            <!-- Table -->
            <div class="table-responsive">
                <table class="table  table-hover "
                       style="font-size: large;text-align: center;">
                    <thead>
                    <tr>
                        <td><span>客户编号</span>
                            <div class="dropdown" id="media_data_operation" style="display: none;">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                    操作
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                    <li><a href="javascript:void(0);" onclick="downloadImgZip();">下载</a></li>
                                    <li><a href="javascript:void(0);" onclick="delete_batch()">移到回收站</a></li>
                                </ul>
                            </div>
                        </td>
                        <td>用户名</td>
                        <td>创建日期</td>
                        <td>手机号</td>
                        <td>邮箱</td>
                        <td>客户状态</td>
                        <td>
                            <c:if test="${sessionScope.isAdmin==true}">隶属于(管理员ID)</c:if></td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userList}" var="u">
                        <tr <c:if test="${u.userState=='1'}">class="danger"</c:if></tr>
                        <td><%--<input type="checkbox" name="media_data_checkbox"
                                       onclick="checkAllCheck();" value="${u.userId}">--%><span>${u.userId}</span></td>
                        <td>${u.userName}</td>
                        <td><fmt:formatDate value="${u.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${u.phone}</td>
                        <td>${u.email}</td>
                        <td>
                            <c:choose>
                                <c:when test="${u.userState=='0'}">正常</c:when>
                                <c:when test="${u.userState=='1'}">停用</c:when>
                                <c:otherwise>未知</c:otherwise>
                            </c:choose>
                        </td>
                        <td><c:if test="${sessionScope.isAdmin==true}">${u.adminId}</c:if></td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <c:if test="${u.userState=='0'}">
                                    <button type="button" class="btn btn-danger"
                                            onclick="stopUser(${u.userId},'${u.userName}')">
                                        停用
                                    </button>
                                    <button type="button" class="btn btn-primary"
                                            onclick="location='toCategoryList.do?user_id=${u.userId}'">
                                        套餐管理
                                    </button>
                                    <button type="button" class="btn btn-warning"
                                            onclick="resetPassword(${u.userId});">
                                        重置密码
                                    </button>
                                </c:if>
                                <c:if test="${u.userState=='1'}">
                                    <button type="button" class="btn btn-success" onclick="enableUser(${u.userId});">
                                        启用
                                    </button>
                                </c:if>
                            </div>
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <%-- 表格底部分页 --%>
                    <tfoot>
                    <tr>
                        <td colspan="8" style="text-align: center;">
                            <nav aria-label="Page navigation">
                                <ul class="pagination">
                                    <c:choose>
                                        <c:when test="${userPage.currentPage==1}">
                                            <li class="disabled">
                                                <a href="javascript:void(0);" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=${userPage.currentPage-1}"
                                                   aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${userPage.totalPage>5 && userPage.currentPage>3}">
                                        <li>
                                            <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=1">1</a>
                                        </li>
                                        <li><a href="javascript:void(0);">...</a></li>
                                    </c:if>

                                    <c:choose>

                                        <%-- 总页数小于等于5张 --%>
                                        <c:when test="${userPage.currentPage<=3}">
                                            <c:if test="${userPage.totalPage<=5}">
                                                <c:forEach begin="1" end="${userPage.totalPage}" var="i">
                                                    <c:choose>
                                                        <c:when test="${i==userPage.currentPage}">
                                                            <li class="active"><a href="javascript:void(0);">${i}</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>
                                                                <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=${i}">${i}</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${userPage.totalPage>5}">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i==userPage.currentPage}">
                                                            <li class="active"><a href="javascript:void(0);">${i}</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>
                                                                <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=${i}">${i}</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                        </c:when>

                                        <%-- 总页数小于等于5张 --%>
                                        <c:when test="${userPage.currentPage>=(userPage.totalPage-2)}">
                                            <c:forEach begin="${userPage.totalPage-5}" end="${userPage.totalPage}"
                                                       var="i">
                                                <c:choose>
                                                    <c:when test="${i==userPage.currentPage}">
                                                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li>
                                                            <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=${i}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:when>

                                        <c:otherwise>
                                            <c:forEach begin="${userPage.currentPage-2}"
                                                       end="${userPage.currentPage+2}" var="i">
                                                <c:choose>
                                                    <c:when test="${i==userPage.currentPage}">
                                                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li>
                                                            <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=${i}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>

                                    </c:choose>

                                    <c:if test="${userPage.totalPage>5 && userPage.currentPage<(userPage.totalPage-2)}">
                                        <li><a href="javascript:void(0);">...</a></li>
                                        <li>
                                            <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=${userPage.totalPage}">${userPage.totalPage}</a>
                                        </li>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${userPage.currentPage==userPage.totalPage}">
                                            <li class="disabled">
                                                <a href="javascript:void(0);" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do?currentPage=${userPage.currentPage+1}"
                                                   aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </nav>
                        </td>
                    </tr>
                    </tfoot>

                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">添加客户</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="${pageContext.request.contextPath}/admin/clientManage/toAddClient.do"
                      id="addFrom">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="email_span">邮箱</span>
                        <input type="text" class="form-control" placeholder="Email" aria-describedby="Email_span"
                               id="email" name="email">
                    </div>
                    <br/>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="company_span">公司</span>
                        <input type="text" class="form-control" placeholder="公司" aria-describedby="company_span"
                               id="company" name="company">
                    </div>
                    <br/>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="category" checked value="true">添加套餐
                                </label>
                                <label>
                                    <input type="radio" name="category" value="false">不添加套餐
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                <button type="button" class="btn btn-primary" onclick="checkForm();">下一步</button>
            </div>
        </div>
    </div>
</div>

<!-- Danger Modal -->
<div class="modal fade" id="dangerModal" tabindex="-1" role="dialog" aria-labelledby="dangerModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="dangerModalLabel">危险操作提醒</h4>
            </div>
            <div class="modal-body" style="text-align: center">
                <span id="danger_modal_span"></span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消操作</button>
                <button type="button" class="btn btn-danger" onclick="confirmStopUserCategory();">确认操作</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function checkForm() {
        var forms = $("#addFrom");
        if (!judgeNull(forms)) {
            alert("有必填项为空！");
            return false;
        }

        var email = $("#email");

        var emailReg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

        if (!email.val().match(emailReg)) {

            alert("请填写正确格式的邮箱");
            email.val("");
            return false;

        }

        $.post(
            "checkEmail.do",
            {"email": email.val()},
            function (res) {
                if (res.success) {
                    forms.submit();
                } else {
                    alert(res.message);
                    email.val("");
                }

            });

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

    var userId;

    /*提交停止申请，弹出确认模态框*/
    function stopUser(user_id, user_name) {

        userId = user_id;

        $("#danger_modal_span").html("此操作属于危险操作，请确认您真的要使以下的用户失效吗？<br/>" +
            "<strong>用户id： " + user_id + " ，用户名： " + user_name +
            "<br/>用户的所有套餐将会同时失效，相应的媒体数据会进入待删除状态。</strong><br/>" +
            "待删除状态下，7天内没有相应的套餐生效，媒体数据将会进入回收站。" +
            "7天内，如果有相应的套餐生效，则待删除状态的媒体数据在下一个数据更新周期将会重新进入正常状态。");
        $("#dangerModal").modal(
            {backdrop: 'static'}
        );

    }

    /*确认停止申请*/
    function confirmStopUserCategory() {

        $.post(
            "changeUserState.do",
            {
                "user_id": userId,
                "user_state": '1'
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.reload();
                } else {
                    alert(res.message);
                    window.location.reload();
                }
            }
        )

    }

    /*提交停止申请，弹出确认模态框*/
    function enableUser(user_id) {
        $.post(
            "changeUserState.do",
            {
                "user_id": user_id,
                "user_state": '0'
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                    window.location.reload();
                } else {
                    alert(res.message);
                    window.location.reload();
                }
            }
        )
    }

    function resetPassword(user_id) {

        if (!confirm("确定要重置该客户的密码吗？点击确认后，系统会将新的密码发送至客户邮箱")) {
            return false;
        }

        alert(user_id);

    }
</script>

</body>
</html>