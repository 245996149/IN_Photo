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

    <%--<link href="${pageContext.request.contextPath}/css/user/table.css" rel="stylesheet">--%>

</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h1>用户管理
                <small>用户管理</small>
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
                        添加用户
                    </button>
                </h3>
            </div>
            <!-- Table -->
            <div class="table-responsive">
                <table class="table <%--table-bordered--%> table-hover <%--dataTable--%>"
                       style="font-size: large;text-align: center;">
                    <thead>
                    <tr>
                        <td>用户编号</td>
                        <td>用户名</td>
                        <td>手机号</td>
                        <td>邮箱</td>
                        <td>创建日期</td>
                        <td>用户状态</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${adminList}" var="a">
                        <tr>
                            <td>${a.adminId}</td>
                            <td>${a.adminName}</td>
                            <td>${a.phone}</td>
                            <td>${a.email}</td>
                            <td><fmt:formatDate value="${a.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${a.adminStatu=='0'}">正常</c:when>
                                    <c:when test="${a.adminStatu=='1'}">暂停</c:when>
                                    <c:when test="${a.adminStatu=='2'}">停用</c:when>
                                    <c:otherwise>未知</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${a.adminId!=1}">
                                    <div class="btn-group-sm" role="group" aria-label="...">
                                        <button type="button" class="btn btn-danger" onclick="">
                                            删除
                                        </button>
                                        <button type="button" class="btn btn-primary"
                                                onclick="location='toCategoryList.do?user_id=${u.userId}'">
                                            更新用户资料
                                        </button>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <%-- 表格底部分页 --%>
                    <tfoot>
                    <tr>
                        <td colspan="7" style="text-align: center;">
                            <nav aria-label="Page navigation">
                                <ul class="pagination">
                                    <c:choose>
                                        <c:when test="${adminPage.currentPage==1}">
                                            <li class="disabled">
                                                <a href="javascript:void(0);" aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=${adminPage.currentPage-1}"
                                                   aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${adminPage.totalPage>5 && adminPage.currentPage>3}">
                                        <li>
                                            <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=1">1</a>
                                        </li>
                                        <li><a href="javascript:void(0);">...</a></li>
                                    </c:if>

                                    <c:choose>

                                        <%-- 总页数小于等于5张 --%>
                                        <c:when test="${adminPage.currentPage<=3}">
                                            <c:if test="${adminPage.totalPage<=5}">
                                                <c:forEach begin="1" end="${adminPage.totalPage}" var="i">
                                                    <c:choose>
                                                        <c:when test="${i==adminPage.currentPage}">
                                                            <li class="active"><a href="javascript:void(0);">${i}</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>
                                                                <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=${i}">${i}</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${adminPage.totalPage>5}">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i==adminPage.currentPage}">
                                                            <li class="active"><a href="javascript:void(0);">${i}</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>
                                                                <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=${i}">${i}</a>
                                                            </li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:if>
                                        </c:when>

                                        <%-- 总页数小于等于5张 --%>
                                        <c:when test="${adminPage.currentPage>=(adminPage.totalPage-2)}">
                                            <c:forEach begin="${adminPage.totalPage-5}" end="${adminPage.totalPage}"
                                                       var="i">
                                                <c:choose>
                                                    <c:when test="${i==adminPage.currentPage}">
                                                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li>
                                                            <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=${i}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:when>

                                        <c:otherwise>
                                            <c:forEach begin="${adminPage.currentPage-2}"
                                                       end="${adminPage.currentPage+2}" var="i">
                                                <c:choose>
                                                    <c:when test="${i==adminPage.currentPage}">
                                                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li>
                                                            <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=${i}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>

                                    </c:choose>

                                    <c:if test="${adminPage.totalPage>5 && adminPage.currentPage<(adminPage.totalPage-2)}">
                                        <li><a href="javascript:void(0);">...</a></li>
                                        <li>
                                            <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=${adminPage.totalPage}">${adminPage.totalPage}</a>
                                        </li>
                                    </c:if>

                                    <c:choose>
                                        <c:when test="${adminPage.currentPage==adminPage.totalPage}">
                                            <li class="disabled">
                                                <a href="javascript:void(0);" aria-label="Next">
                                                    <span aria-hidden="true">&raquo;</span>
                                                </a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li>
                                                <a href="${pageContext.request.contextPath}/admin/userManage/toUser.do?currentPage=${adminPage.currentPage+1}"
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
                <h4 class="modal-title" id="myModalLabel">添加管理员</h4>
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
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="user_state" checked value="true">启用该管理员账号
                                </label>
                                <label>
                                    <input type="radio" name="user_state" value="false">禁用该管理员账号
                                </label>
                            </div>
                        </div>
                    </div>
                    <br/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                <button type="button" class="btn btn-primary" onclick="checkForm();">下一步</button>
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

        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

        var url = localhostPaht + projectName + "/admin/checkEmail.do";

        $.post(
            url,
            {"email": email.val()},
            function (res) {
                if (res.success) {
                    alert(res.message);
//                    forms.submit();
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

</script>

</body>
</html>