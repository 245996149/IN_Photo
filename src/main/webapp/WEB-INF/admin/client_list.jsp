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

    <link href="${pageContext.request.contextPath}/css/user/table.css" rel="stylesheet">

</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<div class="row">
    <div class="col-md-12">

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
                <table class="table table-bordered table-hover dataTable" style="font-size: x-large;">
                    <thead>
                    <tr>
                        <td><input type="checkbox" id="media_data_all_checkbox" onclick="DoCheck();"><span>客户编号</span>
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
                        <td>隶属于(管理员ID)</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userList}" var="u">
                        <tr>
                            <td><input type="checkbox" name="media_data_checkbox"
                                       onclick="checkAllCheck();" value="${u.userId}"><span>${u.userId}</span></td>
                            <td>${u.userName}</td>
                            <td><fmt:formatDate value="${u.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${u.phone}</td>
                            <td>${u.email}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${u.userState=='0'}">正常</c:when>
                                    <c:when test="${u.userState=='1'}">暂停</c:when>
                                    <c:when test="${u.userState=='2'}">停用</c:when>
                                    <c:otherwise>未知</c:otherwise>
                                </c:choose>
                            </td>
                            <td>${u.adminId}</td>
                            <td>
                                <div class="btn-group-sm" role="group" aria-label="...">
                                    <button type="button" class="btn btn-danger" onclick="">
                                        删除
                                    </button>
                                    <button type="button" class="btn btn-primary" onclick="">
                                        查看
                                    </button>
                                    <button type="button" class="btn btn-info" onclick="">下载
                                    </button>
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
</body>
</html>