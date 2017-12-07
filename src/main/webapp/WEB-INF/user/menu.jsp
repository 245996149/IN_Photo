<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 导航栏 -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false"><span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span></button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/user/index.do">INPHOTO</a></div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${nav_code==1}">
                        <li class="active"><a href="${pageContext.request.contextPath}/user/index.do">首页 </a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/user/index.do">首页 </a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${nav_code==2}">
                        <li class="active"><a href="#">套餐管理</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/user/category/toCategory.do">套餐管理</a></li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${nav_code==3}">
                        <li class="dropdown active"><a href="javascript:void(0);" class="dropdown-toggle"
                                                       data-toggle="dropdown"
                                                       role="button"
                                                       aria-haspopup="true" aria-expanded="false">数据管理<span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${allUserCategory}" var="auc">
                                    <c:forEach items="${category}" var="c">
                                        <c:if test="${auc.categoryId==c.categoryId}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${auc.categoryId}&currentPage=1">${c.categoryName}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                                <li role="separator" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/user/table/toRecycle.do?currentPage=1">回收站</a>
                                </li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown"><a href="javascript:void(0);" class="dropdown-toggle"
                                                data-toggle="dropdown"
                                                role="button"
                                                aria-haspopup="true" aria-expanded="false">数据管理<span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${allUserCategory}" var="auc">
                                    <c:forEach items="${category}" var="c">
                                        <c:if test="${auc.categoryId==c.categoryId}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${auc.categoryId}">${c.categoryName}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                                <li role="separator" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/user/table/toRecycle.do">回收站</a></li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${nav_code==4}">
                        <li class="dropdown active"><a href="javascript:void(0);" class="dropdown-toggle"
                                                       data-toggle="dropdown"
                                                       role="button"
                                                       aria-haspopup="true" aria-expanded="false">页面设置<span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${allUserCategory}" var="auc">
                                    <c:forEach items="${category}" var="c">
                                        <c:if test="${auc.categoryId==c.categoryId}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/user/setting/toPageSettings.do?category_id=${auc.categoryId}">${c.categoryName}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown"><a href="javascript:void(0);" class="dropdown-toggle"
                                                data-toggle="dropdown"
                                                role="button"
                                                aria-haspopup="true" aria-expanded="false">页面设置<span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach items="${allUserCategory}" var="auc">
                                    <c:forEach items="${category}" var="c">
                                        <c:if test="${auc.categoryId==c.categoryId}">
                                            <li>
                                                <a href="${pageContext.request.contextPath}/user/setting/toPageSettings.do?category_id=${auc.categoryId}">${c.categoryName}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${nav_code==5}">
                        <li class="active"><a href="#">数据统计</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/user/statistic/toStatistic.do">数据统计</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                        aria-haspopup="true" aria-expanded="false">今日数据<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:void(0);">点击量 <span class="badge">${sessionScope.click_num}</span></a>
                        </li>
                        <li><a href="javascript:void(0);">好友分享量 <span class="badge">${sessionScope.chats_num}</span></a>
                        </li>
                        <li><a href="javascript:void(0);">朋友圈分享量 <span class="badge">${sessionScope.moments_num}</span></a>
                        </li>
                    </ul>
                </li>
                <c:choose>
                    <c:when test="${nav_code==0}">
                        <li class="dropdown active"><a href="javascript:void(0);" class="dropdown-toggle"
                                                       data-toggle="dropdown"
                                                       role="button"
                                                       aria-haspopup="true"
                                                       aria-expanded="false">${sessionScope.loginUser.userName}<span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">用户资料</a></li>
                                <li><a href="#">安全设置</a></li>
                                <li><a href="${pageContext.request.contextPath}/user/login/signOut.do">退出</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="dropdown"><a href="javascript:void(0);" class="dropdown-toggle"
                                                data-toggle="dropdown"
                                                role="button"
                                                aria-haspopup="true"
                                                aria-expanded="false">${sessionScope.loginUser.userName}<span
                                class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">用户资料</a></li>
                                <li><a href="${pageContext.request.contextPath}/user/login/forgotPassword.do">修改密码</a>
                                </li>
                                <li><a href="${pageContext.request.contextPath}/user/login/signOut.do">退出</a></li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>