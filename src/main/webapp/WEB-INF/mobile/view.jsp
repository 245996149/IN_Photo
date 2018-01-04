<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=640, user-scalable=no, target-densitydpi=device-dpi">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>${picWebInfo.pageTitle}</title>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <script language="javascript" type="text/javascript"
            src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script language="javascript" type="text/javascript"
            src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <%--<script src="http://tjs.sjs.sinajs.cn/open/thirdpart/js/jsapi/mobile.js" charset="utf-8"></script>--%>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/mobile/mobile-detect.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/mobile/share.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mobile/view.css"/>

</head>

<body>

<div class="box">
    <c:choose>
        <c:when test="${test==false}">
            <img src="http://file.in-photo.cn/${picWebInfo.backgroundMedia.mediaKey}"
                 class="boxBg">

            <div style="position: absolute;top: ${picWebInfo.pictureTop}%;bottom: ${picWebInfo.pictureBottom}%;
                    left: ${picWebInfo.pictureLeft}%;right: ${picWebInfo.pictureRight}%;z-index: 10;">
                <img src="http://file.in-photo.cn/${media.mediaKey}"
                     class="video">
            </div>

            <script type="text/javascript">
                window.onload = function () {
                    createWeibo();
                    share();
                    collectInformation();
                }
            </script>
        </c:when>
        <c:otherwise>

            <img src="http://file.in-photo.cn/${picWebInfo.backgroundMedia.mediaKey}"
                 class="boxBg">

            <div style="position: absolute;top: ${picWebInfo.pictureTop}%;bottom: ${picWebInfo.pictureBottom}%;
                    left: ${picWebInfo.pictureLeft}%;right: ${picWebInfo.pictureRight}%;z-index: 10">
                <img src="${pageContext.request.contextPath}/images/test.jpg"
                     class="video">
            </div>

            <script type="text/javascript">
                window.onload = function () {
                    share();
                    createWeibo();
                }
            </script>
        </c:otherwise>
    </c:choose>

    <div class="shareInfo">

        <input type="text" value="${user_id}" id="user_id">
        <input type="text" value="${category.categoryId}" id="category_id">
        <input type="text" value="${media.mediaId}" id="media_id">
        <input type="text" value="${test}" id="test">

        <c:choose>
            <c:when test="${shareInfo!=null}">
                <input type="text" value="${shareInfo.shareMomentsTitle}" id="share_moments_title">
                <input type="text"
                       value="http://file.in-photo.cn/${shareInfo.momentsIconMedia.mediaKey}"
                       id="share_moments_icon">
                <input type="text" value="${shareInfo.shareChatsTitle}" id="share_chats_title">
                <input type="text" value="${shareInfo.shareChatsText}" id="share_chats_text">
                <input type="text"
                       value="http://file.in-photo.cn/${shareInfo.chatsIconMedia.mediaKey}"
                       id="share_chats_icon">
            </c:when>
            <c:otherwise>
                <input type="text" value="我们制造全世界最美的笑容" id="share_moments_title">
                <input type="text" value="${url}${pageContext.request.contextPath}/images/logo.png"
                       id="share_moments_icon">
                <input type="text" value="我们制造全世界最美的笑容" id="share_chats_title">
                <input type="text" value="我们制造全世界最美的笑容" id="share_chats_text">
                <input type="text" value="${url}${pageContext.request.contextPath}/images/logo.png"
                       id="share_chats_icon">
            </c:otherwise>
        </c:choose>
    </div>

</div>

</body>
</html>
