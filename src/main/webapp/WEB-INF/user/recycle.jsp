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
    <title>回收站</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/user/table.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/user/recycle.js"></script>

</head>
<body style="padding-top: 70px;">

<%-- 导航栏 --%>
<jsp:include page="/WEB-INF/user/menu.jsp"/>

<%-- 开始 --%>

<div class="row">
    <div class="col-md-6">
        <div class="page-header">
            <h1>回收站
                <small>媒体数据超出系统总量、用户删除媒体数据、系统过期将会将媒体数据移动到回收站中，媒体数据自进入回收站开始计时，30天后系统将会清理该媒体数据</small>
            </h1>
        </div>
    </div>
    <div class="col-md-3">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title">回收站中个系统数据分布情况</h3>
            </div>
            <div class="panel-body">
                <canvas id="system_info" width="400" height="200"></canvas>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title">回收站数据过期情况</h3>
            </div>
            <div class="panel-body">
                <canvas id="recycle_info" width="400" height="200"></canvas>
            </div>
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
                <table class="table table-hover dataTable" style="font-size: x-large;">
                    <thead>
                    <tr>
                        <td><input type="checkbox" id="media_data_all_checkbox" onclick="DoCheck();"><span>媒体编号</span>
                            <div class="dropdown" id="media_data_operation" style="display: none;">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                    操作
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                    <li><a href="javascript:void(0);" onclick="reductionMediaDataList();">还原</a></li>
                                    <li><a href="javascript:void(0);" onclick="cleanMediaDataList();">彻底删除</a></li>
                                </ul>
                            </div>
                        </td>
                        <td>隶属于</td>
                        <td>删除日期</td>
                        <td>有效时间</td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${mediaDataList}" var="m">
                        <tr>
                            <td><input type="checkbox" name="media_data_checkbox"
                                       onclick="checkAllCheck();" value="${m.mediaId}"><span>${m.mediaName}</span></td>
                            <td>
                                <c:forEach items="${category}" var="c">
                                    <c:if test="${m.categoryId==c.categoryId}">
                                        ${c.categoryName}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td><fmt:formatDate value="${m.deleteTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:forEach items="${tempMap}" var="t">
                                    <c:if test="${m.mediaId==t.key}">
                                        <c:choose>
                                            <c:when test="${t.value<8}">
                                                <strong style="color: red">${t.value}天</strong>
                                            </c:when>
                                            <c:otherwise>${t.value}天</c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <div class="btn-group-sm" role="group" aria-label="...">
                                    <button type="button" class="btn btn-danger" onclick="cleanMediaData(${m.mediaId})">
                                        彻底删除
                                    </button>
                                    <button type="button" class="btn btn-info"
                                            onclick="reductionMediaData(${m.mediaId})">还原
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="5" style="text-align: center;">
                            <nav aria-label="Page navigation">
                                <ul class="pagination">
                                    <c:choose>
                                        <c:when test="${tablePage.currentPage==1}">
                                            <li class="disabled">
                                                <a href="javascript:void(0);" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=${tablePage.currentPage-1}"
                                                   aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:if test="${tablePage.totalPage>5 && tablePage.currentPage>3}">
                                        <li>
                                            <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=1">1</a>
                                        </li>
                                        <li><a href="javascript:void(0);">...</a></li>
                                    </c:if>

                                    <c:choose>

                                        <%-- 总页数小于等于5张 --%>
                                        <c:when test="${tablePage.currentPage<=3}">
                                            <c:if test="${tablePage.totalPage<=5}">
                                                <c:forEach begin="1" end="${tablePage.totalPage}" var="i">
                                                    <c:choose>
                                                        <c:when test="${i==tablePage.currentPage}">
                                                            <li class="active"><a href="javascript:void(0);">${i}</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>
                                                                <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=${i}">${i}</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${tablePage.totalPage>5}">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i==tablePage.currentPage}">
                                                            <li class="active"><a href="javascript:void(0);">${i}</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>
                                                                <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=${i}">${i}</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                        </c:when>

                                        <%-- 总页数小于等于5张 --%>
                                        <c:when test="${tablePage.currentPage>=(tablePage.totalPage-2)}">
                                            <c:forEach begin="${tablePage.totalPage-5}" end="${tablePage.totalPage}"
                                                       var="i">
                                                <c:choose>
                                                    <c:when test="${i==tablePage.currentPage}">
                                                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li>
                                                            <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=${i}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:when>

                                        <c:otherwise>
                                            <c:forEach begin="${tablePage.currentPage-2}"
                                                       end="${tablePage.currentPage+2}" var="i">
                                                <c:choose>
                                                    <c:when test="${i==tablePage.currentPage}">
                                                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li>
                                                            <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=${i}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>

                                    </c:choose>

                                    <c:if test="${tablePage.totalPage>5 && tablePage.currentPage<(tablePage.totalPage-2)}">
                                        <li><a href="javascript:void(0);">...</a></li>
                                        <li>
                                            <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=${tablePage.totalPage}">${tablePage.totalPage}</a>
                                        </li>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${tablePage.currentPage==tablePage.totalPage}">
                                            <li class="disabled">
                                                <a href="javascript:void(0);" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=${tablePage.currentPage+1}"
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

<script src="${pageContext.request.contextPath}/js/Chart.js"></script>
<script type="text/javascript">

    $(function () {
        /*加载数据表单*/
        getRecycleInfo();
    });


</script>

</body>
</html>
