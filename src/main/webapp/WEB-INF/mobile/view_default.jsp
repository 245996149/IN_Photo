<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>我们制造全世界最美的笑容</title>
</head>
<body style="margin: 0;">

<div style="position: absolute;width: 100%;height: auto;top: 0;">

    <img src="${pageContext.request.contextPath}/images/mobile/view_default.png"
         style="height: auto;width: 100%;z-index: 0">

    <div style="position: absolute;top: 21.8%;bottom: 61.6%;left: 14.1%;right: 14.1%;z-index: 10;">
        <img src="${pageContext.request.contextPath}/get/getMedia.do?type=1&id=${media_id}"
             style="height: 100%;width: 100%">
    </div>

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
