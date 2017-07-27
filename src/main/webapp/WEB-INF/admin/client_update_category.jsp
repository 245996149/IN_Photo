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
                <h3 class="panel-title">添加用户</h3>
            </div>
            <div class="panel-body">
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
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="company">公司</span>
                    <input type="text" class="form-control" placeholder="客户未填写公司" aria-describedby="company" readonly
                           value="${user.company}">
                </div>
                <br/>
                <div class="input-group input-group-lg">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default disabled">添加套餐</button>
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu">
                            <c:forEach items="${categoryList}" var="cl">
                                <li><a href="#"
                                       onclick="selectCategory(${cl.categoryId},'${cl.categoryName}');">${cl.categoryName}</a>
                                </li>
                                <%--<option value="${cl.categoryId}">${cl.categoryName}</option>--%>
                            </c:forEach>
                        </ul>
                    </div>
                    <input type="text" id="category_name" placeholder="点击左侧下拉箭头，选择要添加的系统" readonly class="form-control"
                           aria-label="Text input with segmented button dropdown">
                </div>
                <%--<select>--%>
                <%--<c:forEach items="${categoryList}" var="cl">--%>
                <%--<option value="${cl.categoryId}">${cl.categoryName}</option>--%>
                <%--</c:forEach>--%>
                <%--</select>--%>
                <br/>
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="beginDate">生效时间</span>
                    <input type="datetime-local" class="form-control" placeholder="客户未填写公司"
                           aria-describedby="beginDate">
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-12" style="text-align: center">
                        <div class="btn-group" role="group" aria-label="...">
                            <button type="button" class="btn btn-danger">返回</button>
                            <button type="button" class="btn btn-success">下一步</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    var category;

    function selectCategory(category_id, category_name) {

        category = category_id;

        $("#category_name").val(category_name);

    }

</script>
</body>
</html>