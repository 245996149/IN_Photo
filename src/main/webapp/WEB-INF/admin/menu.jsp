<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="o" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 导航栏 -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false"><span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span></button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/index/index.do">INPHOTO</a></div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${currentModule==0}">
                        <li class="active"><a href="${pageContext.request.contextPath}/admin/index/index.do">首页</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/admin/index/index.do">首页</a></li>
                    </c:otherwise>
                </c:choose>

                <c:forEach items="${allModules}" var="m">
                    <c:if test="${m.moduleId==1}">
                        <c:choose>
                            <c:when test="${currentModule==1}">
                                <li class="active"><a
                                        href="${pageContext.request.contextPath}/admin/clientManage/toClient.do">客户管理</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${pageContext.request.contextPath}/admin/clientManage/toClient.do">客户管理</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${m.moduleId==2}">
                        <c:choose>
                            <c:when test="${currentModule==2}">
                                <li class="active"><a
                                        href="${pageContext.request.contextPath}/admin/categoryManage/toCategory.do">套餐管理</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/categoryManage/toCategory.do">套餐管理</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${m.moduleId==3}">
                        <c:choose>
                            <c:when test="${currentModule==3}">
                                <li class="active"><a
                                        href="${pageContext.request.contextPath}/admin/adminManage/toAdmin.do">管理员账户管理</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/adminManage/toAdmin.do">管理员账户管理</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <c:if test="${m.moduleId==4}">
                        <c:choose>
                            <c:when test="${currentModule==4}">
                                <li class="active"><a
                                        href="${pageContext.request.contextPath}/admin/roleManage/toRole.do">角色管理</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${pageContext.request.contextPath}/admin/roleManage/toRole.do">角色管理</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown "><a href="javascript:void(0);" class="dropdown-toggle"
                                         data-toggle="dropdown"
                                         role="button"
                                         aria-haspopup="true"
                                         aria-expanded="false">${sessionScope.adminUser.adminName}<span
                        class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">用户资料</a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/login/forgotPassword.do">修改密码</a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/login/logout.do">退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>