<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>我们制造全世界最美的笑容</title>
</head>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

<script language="javascript" type="text/javascript"
        src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script language="javascript" type="text/javascript"
        src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script type="text/javascript">

    //网页加载后执行函数
    window.onload = function () {

        var user_id = $("#user_id").val();
        var category_id = $("#category_id").val();
        var media_id = $("#media_id").val();

        $.post(
            "collectingData.do",
            {
                "user_id": user_id,
                "category_id": category_id,
                "media_id": media_id,
                "share_type": "0"
            },
            function (res) {
            }
        );

        //判断是否为微信内核
        if (isWeixin()) {
            //是微信打开

            var url = location.href;

            var share_moments_title = $("#share_moments_title").val();
            var share_moments_icon = $("#share_moments_icon").val();
            var share_chats_title = $("#share_chats_title").val();
            var share_chats_text = $("#share_chats_text").val();
            var share_chats_icon = $("#share_chats_icon").val();

            $.post(
                "getWeChatInfo.do",
                {
                    "url": url
                },
                function (res) {

                    wx.config({
                        debug: false,
                        appId: res.appid,
                        timestamp: res.timestamp,
                        nonceStr: res.nonceStr,
                        signature: res.signature,
                        jsApiList: ['checkJsApi', 'onMenuShareTimeline',
                            'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo',
                            'hideMenuItems', 'showMenuItems', 'hideAllNonBaseMenuItem',
                            'showAllNonBaseMenuItem', 'translateVoice', 'startRecord',
                            'stopRecord', 'onRecordEnd', 'playVoice', 'pauseVoice',
                            'stopVoice', 'uploadVoice', 'downloadVoice', 'chooseImage',
                            'previewImage', 'uploadImage', 'downloadImage',
                            'getNetworkType', 'openLocation', 'getLocation',
                            'hideOptionMenu', 'showOptionMenu', 'closeWindow',
                            'scanQRCode', 'chooseWXPay', 'openProductSpecificView',
                            'addCard', 'chooseCard', 'openCard']
                    });

                    wx.ready(function () {

                        wx.onMenuShareTimeline({
                            title: share_moments_title, // 分享标题timg.jpeg
                            link: url, // 分享链接
                            imgUrl: share_moments_icon, // 分享图标
                            success: function () {
                                // 用户确认分享后执行的回调函数
                                $.post(
                                    "collectingData.do",
                                    {
                                        "user_id": user_id,
                                        "category_id": category_id,
                                        "media_id": media_id,
                                        "share_type": "2"
                                    },
                                    function (res) {
                                    })
                            },
                            cancel: function () {
                                // 用户取消分享后执行的回调函数
                            }
                        });

                        wx.onMenuShareAppMessage({
                            title: share_chats_title, // 分享标题
                            desc: share_chats_text, // 分享描述
                            link: url, // 分享链接
                            imgUrl: share_chats_icon, // 分享图标
                            type: '', // 分享类型,music、video或link，不填默认为link
                            dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                            success: function () {
                                // 用户确认分享后执行的回调函数
                                $.post(
                                    "collectingData.do",
                                    {
                                        "user_id": user_id,
                                        "category_id": category_id,
                                        "media_id": media_id,
                                        "share_type": "1"
                                    },
                                    function (res) {
                                    })
                            },
                            cancel: function () {
                                // 用户取消分享后执行的回调函数
                            }
                        });
                    });
                })

        }

    }

    //这个函数用来判断当前浏览器是否微信内置浏览器，是微信返回true，不是微信返回false
    function isWeixin() {
        var WxObj = window.navigator.userAgent.toLowerCase();
        if (WxObj.match(/microMessenger/i) == 'micromessenger') {
            return true;
        } else {
            return false;
        }
    }

</script>

<body style="margin: 0;">

<div style="position: absolute;width: 100%;height: auto;top: 0;">

    <c:choose>
        <c:when test="${category.isVideo==1}">
            <img src="${pageContext.request.contextPath}/images/mobile/view_default.png"
                 style="height: auto;width: 100%;z-index: 0">

            <div style="position: absolute;top: 21.8%;bottom: 61.6%;left: 14.1%;right: 14.1%;z-index: 10;">
                <video src="${pageContext.request.contextPath}/get/getMedia.do?type=1&id=${media_id}&download=true&image_type=.${image_type}"
                       style="height: 100%;width: 100%" controls preload="auto" webkit-playsinline="true"
                       x5-video-player-type="h5"
                       x5-video-orientation="h5"/>
                </video>
            </div>
        </c:when>
        <c:otherwise>
            <img src="${pageContext.request.contextPath}/images/mobile/view_default.png"
                 style="height: auto;width: 100%;z-index: 0">

            <div style="position: absolute;top: 21.8%;bottom: 61.6%;left: 14.1%;right: 14.1%;z-index: 10;">
                <img src="${pageContext.request.contextPath}/get/getMedia.do?type=1&id=${media_id}&download=true&image_type=.${image_type}"
                     style="height: 100%;width: 100%">
            </div>
        </c:otherwise>
    </c:choose>

    <div style="display: none">

        <input type="text" value="${user_id}" id="user_id">
        <input type="text" value="${category.categoryId}" id="category_id">
        <input type="text" value="${media_id}" id="media_id">

        <c:choose>
            <c:when test="${shareInfo!=null}">
                <input type="text" value="${shareInfo.shareMomentsTitle}" id="share_moments_title">
                <input type="text"
                       value="${url}${pageContext.request.contextPath}/get/getMedia.do?type=5&id=${shareInfo.shareInfoId}"
                       id="share_moments_icon">
                <input type="text" value="${shareInfo.shareChatsTitle}" id="share_chats_title">
                <input type="text" value="${shareInfo.shareChatsText}" id="share_chats_text">
                <input type="text"
                       value="${url}${pageContext.request.contextPath}/get/getMedia.do?type=6&id=${shareInfo.shareInfoId}"
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
