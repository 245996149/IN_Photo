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
    <script src="${pageContext.request.contextPath}/"></script>
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
                    <span class="input-group-addon" id="Email">邮箱</span>
                    <input type="text" class="form-control" placeholder="Email" aria-describedby="Email" readonly
                           value="${user.email}">
                </div>
                <br/>
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="company">公司</span>
                    <input type="text" class="form-control" placeholder="公司" aria-describedby="company" readonly
                           value="${user.company}">
                </div>
                <br/>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="radio">
                            <label>
                                <input type="radio" name="category" checked >添加套餐
                            </label>
                            <label>
                                <input type="radio" name="category">不添加套餐
                            </label>
                        </div>
                    </div>
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
</body>
</html>