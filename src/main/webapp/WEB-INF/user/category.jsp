<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Category</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body style="padding-top: 70px;">

<%-- 导航栏 --%>
<jsp:include page="/WEB-INF/user/menu.jsp"/>

<%-- 开始 --%>
<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h1>套餐管理
                <small>。。。。。</small>
            </h1>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">数据列表</h3>
            </div>
            <!-- Table -->
            <div class="table-responsive">
                <table class="table" style="text-align: center;">
                    <thead>
                    <tr>
                        <td>套餐id</td>
                        <td>套餐名称</td>
                        <td>支付时间</td>
                        <td>生效时间</td>
                        <td>截至时间</td>
                        <td>套餐容量</td>
                        <td>套餐使用情况</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <%--<tr>--%>
                    <%--<td>100</td>--%>
                    <%--<td>铝木抠像系统</td>--%>
                    <%--<td>2017-01-01 12:00:00</td>--%>
                    <%--<td>2017-01-01 12:00:00</td>--%>
                    <%--<td>2017-01-01 12:00:00</td>--%>
                    <%--<td>--%>
                    <%--<div class="progress">--%>
                    <%--<div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"--%>
                    <%--aria-valuemax="100" style="width: 60%;min-width: 2%;">--%>
                    <%--60%--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--</td>--%>
                    <%--<td>操作</td>--%>
                    <%--</tr>--%>
                    <c:forEach items="${userCategoryList}" var="uc">
                        <tr>
                            <td>${uc.userCategoryId}</td>
                            <td>
                                <c:forEach items="${category}" var="c">
                                    <c:if test="${uc.categoryId==c.categoryId}">
                                        ${c.categoryName}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td><fmt:formatDate value="${uc.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${uc.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${uc.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${uc.mediaNumber}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${uc.userCategoryState==0}">
                                        <c:forEach items="${tempMap}" var="t">
                                            <c:if test="${uc.userCategoryId==t.key}">
                                                <div class="progress">
                                                    <div class="progress-bar" role="progressbar"
                                                         aria-valuenow="${t.value}"
                                                         aria-valuemin="0"
                                                         aria-valuemax="100"
                                                         style="width: ${t.value}%;min-width: 1.5em;">
                                                            ${t.value}%
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${uc.userCategoryState==2}">
                                        未生效
                                    </c:when>
                                    <c:otherwise>
                                        已过期
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>操作</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="7" style="text-align: center;">
                            <nav aria-label="Page navigation">
                                <ul class="pagination">
                                    <li class="disabled">
                                        <a href="#" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <li class="active"><a href="#">1</a></li>
                                    <li><a href="#">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">4</a></li>
                                    <li><a href="#">5</a></li>
                                    <li>
                                        <a href="#" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
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


</body>
</html>