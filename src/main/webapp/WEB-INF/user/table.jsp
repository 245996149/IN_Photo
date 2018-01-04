<%@ page import="cn.inphoto.dbentity.user.MediaData" %>
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
    <title>数据管理-<c:forEach items="${category}" var="c">
        <c:if test="${tablePage.category_id==c.categoryId}">
            ${c.categoryName}
        </c:if>
    </c:forEach></title>
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

    <script src="${pageContext.request.contextPath}/js/user/table.js"></script>

</head>
<body style="padding-top: 70px;">

<div style="display: none;">
    <input type="text" id="category_id" value="${tablePage.category_id}">
    <input type="text" id="user_id" value="${tablePage.user_id}">
</div>

<%-- 导航栏 --%>
<jsp:include page="/WEB-INF/user/menu.jsp"/>

<%-- 开始 --%>

<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <c:forEach items="${category}" var="c">
                <c:if test="${tablePage.category_id==c.categoryId}">
                    <h1> ${c.categoryName}
                        <small>${c.categoryNote}</small>
                    </h1>
                </c:if>
            </c:forEach>
        </div>
    </div>

</div>

<div class="row">
    <div class="col-md-8">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">数据查询</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <form class="form-inline" id="find__date_form">
                        <div class="form-group">
                            <label for="find__end_date">开始时间</label>
                            <input type="date" class="form-control" id="find__begin_date"
                                   onchange="settingFindDateInput();"/>
                        </div>
                        <div class="form-group">
                            <label for="find__end_date">结束时间</label>
                            <input type="date" class="form-control" id="find__end_date">
                        </div>
                        <button type="button" class="btn btn-info" onclick="findDate();">查询
                        </button>
                    </form>
                    <%--<div id="find_error_div">检测到您的浏览器不支持H5特性，所以禁用了批量下载功能，请使用<strong>Chrome内核</strong>的浏览器--%>
                    <%--<br/><strong>Chrome内核的浏览器:</strong>Google Chrome、360极速浏览器、QQ浏览器、UC浏览器等--%>
                    <%--</div>--%>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <canvas id="click_7" width="400" height="200"></canvas>
                    </div>
                    <div class="col-md-6">
                        <canvas id="share_7" width="400" height="200"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-4">

        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title">系统使用情况</h3>
            </div>
            <div class="panel-body">
                <canvas id="system_info" width="400" height="200"></canvas>
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
                                    <li><a href="javascript:void(0);" onclick="downloadImgZip();">下载</a></li>
                                    <li><a href="javascript:void(0);" onclick="delete_batch()">移到回收站</a></li>
                                </ul>
                            </div>
                        </td>
                        <td>缩略图</td>
                        <td>创建日期</td>
                        <td>提取码</td>
                        <td>状态</td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="WillDelete" value="<%=MediaData.MediaState.WillDelete.name() %>"/>
                    <c:set var="Normal" value="<%=MediaData.MediaState.Normal.name() %>"/>
                    <c:forEach items="${mediaDataList}" var="m">
                        <tr <c:if test="${m.mediaState eq WillDelete}">class="danger" </c:if>>
                            <td><input type="checkbox" name="media_data_checkbox"
                                       onclick="checkAllCheck();" value="${m.mediaId}"><span>${m.mediaName}</span></td>
                            <td width="5%">
                                <c:choose>
                                    <c:when test="${this_category.isVideo==1}">
                                        <c:forEach items="${picMediaList}" var="pics">
                                            <c:if test="${pics.mediaId==m.videoPicMedia}">
                                                <a href="javascript:void(0);" onclick="open_modal(${m.mediaName});"
                                                   class="thumbnail" style="margin-bottom:auto;">
                                                    <img src="http://file.in-photo.cn/${pics.mediaKey}?x-oss-process=style/100px"
                                                         alt="...">
                                                </a>
                                            </c:if>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:void(0);" onclick="open_modal(${m.mediaName});"
                                           class="thumbnail" style="margin-bottom:auto;">
                                            <img src="http://file.in-photo.cn/${m.mediaKey}?x-oss-process=style/100px"
                                                 alt="...">
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                </a></td>
                            <td><fmt:formatDate value="${m.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:forEach items="${mediaCodeList}" var="mc">
                                    <c:choose>
                                        <c:when test="${mc.mediaId==m.mediaId}">
                                            ${mc.mediaCode}
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </td>
                            <td><c:choose>
                                <c:when test="${m.mediaState eq Normal}">正常</c:when>
                                <c:when test="${m.mediaState eq WillDelete}">待删除<br/>
                                    <fmt:formatDate value="${m.deleteTime}" pattern="yyyy年MM月dd日"/>移动到回收站中</c:when>
                                <c:otherwise>未知</c:otherwise>
                            </c:choose></td>
                            <td>
                                <div class="btn-group-sm" role="group" aria-label="...">
                                    <button type="button" class="btn btn-danger" onclick="delete_media(${m.mediaId});">
                                        删除
                                    </button>
                                    <button type="button" class="btn btn-primary" onclick="open_modal(${m.mediaName});">
                                        查看
                                    </button>
                                    <button type="button" class="btn btn-info" onclick="download(${m.mediaKey});">
                                        下载
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="6" style="text-align: center;">
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
                                                <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=${tablePage.currentPage-1}"
                                                   aria-label="Previous">
                                                    <span aria-hidden="true">&laquo;</span>
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${tablePage.totalPage>5 && tablePage.currentPage>3}">
                                        <li>
                                            <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=1">1</a>
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
                                                                <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=${i}">${i}</a>
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
                                                                <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=${i}">${i}</a>
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
                                                            <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=${i}">${i}</a>
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
                                                            <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=${i}">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>

                                    </c:choose>

                                    <c:if test="${tablePage.totalPage>5 && tablePage.currentPage<(tablePage.totalPage-2)}">
                                        <li><a href="javascript:void(0);">...</a></li>
                                        <li>
                                            <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=${tablePage.totalPage}">${tablePage.totalPage}</a>
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
                                                <a href="${pageContext.request.contextPath}/user/table/toTable.do?category_id=${tablePage.category_id}&currentPage=${tablePage.currentPage+1}"
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
                    <tr>
                        <td colspan="6" style="text-align: center;">
                            <form class="form-inline" id="download_for_date_form" hidden>
                                <div class="form-group">
                                    <label for="download_begin_date">开始时间</label>
                                    <input type="date" class="form-control" id="download_begin_date"
                                           onchange="settingDownloadInput();"/>
                                </div>
                                <div class="form-group">
                                    <label for="download_end_date">结束时间</label>
                                    <input type="date" class="form-control" id="download_end_date">
                                </div>
                                <button type="button" class="btn btn-info" onclick="createZipForDate();">下载该时间段内数据
                                </button>
                                <div id="zip_create_states_message"></div>
                            </form>
                            <div id="error_div">检测到您的浏览器不支持H5特性，所以禁用了批量下载功能，请使用<strong>Chrome内核</strong>的浏览器
                                <br/><strong>Chrome内核的浏览器:</strong>Google Chrome、360极速浏览器、QQ浏览器、UC浏览器等
                            </div>
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
                <h4 class="modal-title" id="myModalLabel">当前页媒体数据查看</h4>
            </div>
            <div class="modal-body">
                <div id="myCarousel" class="carousel">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <c:forEach items="${mediaDataList}" var="m" varStatus="mv">
                            <li data-target="#myCarousel" data-slide-to="${mv.index}"
                                data-media-name="${m.mediaName}"></li>
                        </c:forEach>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner" id="carousel-object">
                        <c:choose>
                            <c:when test="${this_category.isVideo==1}">
                                <c:forEach items="${mediaDataList}" var="m">
                                    <c:forEach items="${picMediaList}" var="pics">
                                        <c:if test="${pics.mediaId==m.videoPicMedia}">
                                            <div class="item" data-media-name="${m.mediaName}">
                                                <video class="video" controls
                                                       poster="http://file.in-photo.cn/${pics.mediaKey}?x-oss-process=style/400px"
                                                       src="http://file.in-photo.cn/${m.mediaKey}" >
                                                </video>
                                                <div class="carousel-caption">${m.mediaName}</div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${mediaDataList}" var="m">
                                    <div class="item" data-media-name="${m.mediaName}">
                                        <img src="${pageContext.request.contextPath}/images/loading.gif" name="lazy"
                                             style="margin: 0 auto;"
                                             alt="${m.mediaKey}" data-id="${m.mediaId}"
                                             lz-src="http://file.in-photo.cn/${m.mediaKey}?x-oss-process=style/400px">
                                        <div class="carousel-caption">${m.mediaName}</div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${this_category.isVideo!=1}">
                        <!-- 轮播（Carousel）导航 -->
                        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </c:if>
                </div>
            </div>
            <div class="modal-footer">
                <a tabindex="0" class="btn btn-success" role="button" data-toggle="popover" id="share_button"
                   data-trigger="focus" title="扫描二维码分享图片">分享</a>
                <button type="button" class="btn btn-danger" onclick="modal_delete();">删除</button>
                <button type="button" class="btn btn-primary" onclick="modal_download();">下载</button>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/Chart.js"></script>
<script type="text/javascript">

    $(function () {
        var date = new Date();
        var day = date.getDate();
        var month = date.getMonth() + 1;

        if (date.getMonth() < 9) {
            month = "0" + (date.getMonth() + 1);
        }

        if (date.getDate() < 10) {
            day = "0" + date.getDate();
        }

        var today = date.getFullYear() + "-" + month + "-" + day;
        /*加载数据表单*/
        checkDatesupport();
        getClickData(getNewDay(today, -6), today);
        getShareDate(getNewDay(today, -6), today);
        getSystemInfo();
//        getRecycleInfo();
        $('#share_button').popover({
            container: 'body',
            html: true,
            content: aaa,
            placement: 'left'
        });
        onloadSetting();
    });

    $('.carousel').carousel({
        interval: false
    });

    function aaa() {

        var carousel_obj = $("#carousel-object .active img");

        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

        var s = localhostPaht + projectName + "/mobile/toPage.do?user_id=${sessionScope.loginUser.userId}&category_id=${tablePage.category_id}&media_id=" + carousel_obj.attr("data-id");

        var aa = projectName + "/get/getQR.do?url=" + encodeURIComponent(s);

        var a = '<img src="' + aa + '" alt="${m.mediaId}" width="200px">';

        return a;
//            $(this).popover({
//                container: 'body',
//                html: true,
//                content: a,
//                placement: 'left'
//            });

    }

    function checkDatesupport() {
        var i = document.createElement('input');
        i.setAttribute('type', 'date');
        //浏览器不支持date类型
        if (i.type == 'date') {
            $("#error_div").hide();
            $("#download_for_date_form").show();
        }
    }

    function onloadSetting() {

        var date = new Date();

        var day = date.getDate();
        var month = date.getMonth() + 1;

        if (date.getMonth() < 9) {
            month = "0" + (date.getMonth() + 1);
        }

        if (date.getDate() < 10) {
            day = "0" + date.getDate();
        }

        today = date.getFullYear() + "-" + month + "-" + day;

        var begin_date = $("#download_begin_date");

        begin_date.val(today);

        begin_date.attr("max", today);

        var end_date = $("#download_end_date");

        end_date.val(today);

        end_date.attr("max", today);

        var find_begin = $("#find__begin_date");

        find_begin.val(getNewDay(today, -6), today);
        find_begin.attr("max", today);

        var find_end = $("#find__end_date");

        find_end.val(today);
        find_end.attr("max", today);
    }


</script>

</body>
</html>