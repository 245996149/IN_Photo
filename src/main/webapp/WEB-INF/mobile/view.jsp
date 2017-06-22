<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport"
          content="width=640, user-scalable=no, target-densitydpi=device-dpi">
    <title>${picWebinfo.pageTitle}</title>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
</head>
<body style="margin: 0;">

<div style="position: absolute;width: 100%;height: auto;top: 0;">

    <c:choose>
        <c:when test="${test==false}">
            <%--<img src="${pageContext.request.contextPath}/get/getMedia.do?type=1&id=${media_id}"--%>
                 <%--style="position:absolute;height: 100%;width: 100%;opacity: 0;z-index: 999">--%>

            <img src="${pageContext.request.contextPath}/get/getMedia.do?type=2&id=${picWebinfo.picWebinfoId}"
                 style="height: auto;width: 100%;z-index: 0">

            <div style="position: absolute;top: ${picWebinfo.pictureTop}%;bottom: ${picWebinfo.pictureBottom}%;
                    left: ${picWebinfo.pictureLeft}%;right: ${picWebinfo.pictureRight}%;z-index: 10;">
                <img src="${pageContext.request.contextPath}/get/getMedia.do?type=1&id=${media_id}"
                     style="height: 100%;width: 100%">
            </div>
        </c:when>
        <c:otherwise>
            <%--<img src="${pageContext.request.contextPath}/get/getMedia.do?type=7"--%>
                 <%--style="position:absolute;height: 100%;width: 100%;opacity: 0;z-index: 999">--%>

            <img src="${pageContext.request.contextPath}/get/getMedia.do?type=2&id=${picWebinfo.picWebinfoId}"
                 style="height: auto;width: 100%;z-index: 0">

            <div style="position: absolute;top: ${picWebinfo.pictureTop}%;bottom: ${picWebinfo.pictureBottom}%;
                    left: ${picWebinfo.pictureLeft}%;right: ${picWebinfo.pictureRight}%;z-index: 10">
                <img src="${pageContext.request.contextPath}/get/getMedia.do?type=7" style="height: 100%;width: 100%">
            </div>
        </c:otherwise>
    </c:choose>

    <div style="display: none">
        <c:choose>
            <c:when test="${shareInfoEntity!=null}">
                <input type="text" value="${shareInfoEntity.shareMomentsTitle}" id="share_moments_title">
                <input type="text"
                       value="${url}${pageContext.request.contextPath}/get/getMedia.do?type=5&id=${shareInfoEntity.shareInfoId}"
                       id="share_moments_icon">
                <input type="text" value="${shareInfoEntity.shareChatsTitle}" id="share_chats_title">
                <input type="text" value="${shareInfoEntity.shareChatsText}" id="share_chats_text">
                <input type="text"
                       value="${url}${pageContext.request.contextPath}/get/getMedia.do?type=6&id=${shareInfoEntity.shareInfoId}"
                       id="share_chats_icon">
            </c:when>
            <c:otherwise>
                <input type="text" value="我们制造全世界最美的笑容" id="share_moments_title">
                <input type="text" value="${url}/InPhoto/mobile/openMedia.do?id=...&type=4"
                       id="share_moments_icon">
                <input type="text" value="我们制造全世界最美的笑容" id="share_chats_title">
                <input type="text" value="我们制造全世界最美的笑容" id="share_chats_text">
                <input type="text" value="${url}/InPhoto/mobile/openMedia.do?id=...&type=4">
            </c:otherwise>
        </c:choose>
    </div>

</div>

</body>
</html>
