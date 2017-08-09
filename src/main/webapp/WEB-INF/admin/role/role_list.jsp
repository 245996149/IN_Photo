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
            <h1>角色管理
                <small>角色管理</small>
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
                    <a class="btn btn-success" style="color: white;"
                       href="${pageContext.request.contextPath}/admin/roleManage/toAddRole.do">添加角色</a>
                </h3>
            </div>
            <!-- Table -->
            <div class="table-responsive">
                <table class="table <%--table-bordered--%> table-hover <%--dataTable--%>"
                       style="font-size: large;text-align: center;">
                    <thead>
                    <tr>
                        <td>角色编号</td>
                        <td>角色名称</td>
                        <td>拥有的权限</td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${roleList}" var="rl">
                        <tr>
                            <td>${rl.roleId}</td>
                            <td>${rl.name}</td>
                            <td>
                                <c:forEach items="${rl.moduleInfoSet}" var="module" varStatus="s">
                                    <c:choose>
                                        <c:when test="${s.last }">
                                            ${module.name }
                                        </c:when>
                                        <c:otherwise>
                                            ${module.name }、
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </td>
                            <td>
                                <c:if test="${rl.roleId!=1}">
                                    <div class="btn-group-sm" role="group" aria-label="...">
                                        <a class="btn btn-primary"
                                           href="${pageContext.request.contextPath}/admin/roleManage/toUpdateRole.do?role_id=${rl.roleId}">
                                            更新信息
                                        </a>
                                        <button type="button" class="btn btn-danger"
                                                onclick="deleteRole(${rl.roleId});">
                                            删除
                                        </button>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function deleteRole(role_id) {

        if (!confirm("请确认您要删除编号为：" + role_id + " 的角色")) {
            return false;
        }

        $.post(
            "deleteRole.do",
            {
                "role_id": role_id
            },
            function (res) {
                if (res.success) {
                    window.location.reload();
                } else {
                    alert(res.message);
                }
            }
        );

    }

</script>

</body>
</html>