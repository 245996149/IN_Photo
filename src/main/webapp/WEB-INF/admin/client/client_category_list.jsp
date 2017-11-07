<%@ page import="cn.inphoto.dbentity.user.UserCategory" %>
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
    <title>INPHOTO管理员系统</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

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

<c:set var="OVER" value="<%=UserCategory.UserState.OVER.name() %>"/>
<c:set var="NORMAL" value="<%=UserCategory.UserState.NORMAL.name() %>"/>
<c:set var="NOT_START" value="<%=UserCategory.UserState.NOT_START.name() %>"/>

<%-- 导航栏 --%>
<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<%-- 开始 --%>
<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h1>套餐管理
                <small>您现在在email为${user.email}的客户的套餐管理</small>
            </h1>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">数据列表
                    <a class="btn btn-success" style="color: white;"
                       href="${pageContext.request.contextPath}/admin/clientManage/toAddCategory.do?user_id=${user.userId}">添加套餐</a>
                    <input hidden id="user_id" value="${user.userId}">
                </h3>
            </div>
            <!-- Table -->
            <div class="table-responsive">
                <table class="table table-hover " style="text-align: center;font-size: large;">
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
                    <c:forEach items="${userCategoryList}" var="uc">
                        <tr <c:choose>
                            <c:when test="${uc.userCategoryState eq OVER}">class="danger"</c:when>
                            <c:when test="${uc.userCategoryState eq NOT_START}">class="warning"</c:when>
                        </c:choose> >
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
                            <c:choose>
                                <c:when test="${uc.userCategoryState eq NORMAL}">
                                    <td>
                                        <c:forEach items="${tempMap}" var="t">
                                            <c:if test="${uc.userCategoryId==t.key}">
                                                <div class="progress" style="margin: auto;">
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
                                    </td>
                                    <td><a class="btn btn-info btn-xs"
                                           href="${pageContext.request.contextPath}/admin/clientManage/toClientMedia.do?user_id=${uc.userId}&category_id=${uc.categoryId}">查看媒体数据</a>
                                        <a class="btn btn-success btn-xs"
                                           href="${pageContext.request.contextPath}/admin/clientManage/toUpdateCategory.do?userCategory_id=${uc.userCategoryId}">修改套餐信息</a>
                                        <c:forEach items="${category}" var="c">
                                            <c:if test="${uc.categoryId==c.categoryId}">
                                                <button type="button" class="btn btn-danger btn-xs"
                                                        onclick="stopUserCategory(${uc.userCategoryId},'${c.categoryName}');">
                                                    失效
                                                </button>
                                            </c:if>
                                        </c:forEach>
                                        <button type="button" class="btn btn-primary btn-xs"
                                                onclick="getURL(${uc.categoryId})">
                                            显示提取地址
                                        </button>
                                    </td>
                                </c:when>
                                <c:when test="${uc.userCategoryState eq NOT_START}">
                                    <td> 未生效</td>
                                    <td>
                                        <a class="btn btn-success btn-xs"
                                           href="${pageContext.request.contextPath}/admin/clientManage/toUpdateCategory.do?userCategory_id=${uc.userCategoryId}">修改套餐信息</a>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td> 已过期</td>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Url Modal -->
<div class="modal fade" id="urlModal" tabindex="-1" role="dialog" aria-labelledby="urlModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="urlModalLabel">提取地址</h4>
            </div>
            <div class="modal-body" style="text-align: center">
                <img src="${pageContext.request.contextPath}/images/QRcode_test.png" id="modal_img"><br>
                <span id="url_modal_span">微信扫描二维码预览页面</span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
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

    /*弹出获取url模态框*/
    function getURL(category_id) {

        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

        var s = localhostPaht + projectName + "/mobile/toCode.do?user_id=${user.userId}&category_id=" + category_id;

        var aa = projectName + "/get/getQR.do?url=" + encodeURIComponent(s);

        $("#modal_img").attr("src", aa);
        $("#url_modal_span").html(s);
        $("#urlModal").modal(
            {backdrop: 'static'}
        );
    }

    var user_category_id;

    /*提交停止申请，弹出确认模态框*/
    function stopUserCategory(userCategory_id, category_name) {

        user_category_id = userCategory_id;

        $("#danger_modal_span").html("此操作属于危险操作，请确认您真的要使一下的用户套餐失效吗？<br/>" +
            "<strong>用户套餐id： " + userCategory_id + " ，套餐名称： " + category_name +
            "</strong> <br/>失效之后，相应的媒体数据会进入待删除状态。待删除状态下，7天内没有相应的套餐生效，媒体数据将会进入回收站回收流程。" +
            "7天内，如果有相应的套餐生效，则待删除状态的媒体数据在下一个数据更新周期将会重新进入正常状态。");
        $("#dangerModal").modal(
            {backdrop: 'static'}
        );

    }

    /*确认停止申请*/
    function confirmStopUserCategory() {

        $.post(
            "stopUserCategory.do",
            {
                "userCategory_id": user_category_id
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

</script>

</body>
</html>