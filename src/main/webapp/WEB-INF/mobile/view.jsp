<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport"
          content="width=640, user-scalable=no, target-densitydpi=device-dpi">
    <title>${picWebInfo.pageTitle}</title>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>


    <script language="javascript" type="text/javascript"
            src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script language="javascript" type="text/javascript"
            src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="http://tjs.sjs.sinajs.cn/open/thirdpart/js/jsapi/mobile.js" charset="utf-8"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/mobile/mobile-detect.min.js"></script>

    <script type="text/javascript">

        //网页加载后执行函数
        window.onload = function () {
            var user_id = $("#user_id").val();
            var category_id = $("#category_id").val();
            var media_id = $("#media_id").val();
            var test = $("#test").val();

            var device_type = window.navigator.userAgent;
            var md = new MobileDetect(device_type);//初始化mobile-detect
            var os = md.os();//获取系统
            var osType = "other";
            var osVersion = "other";
            var brand = "other";
            var browserType = "other";
            var model = "other";

            var url = location.href;
            var share_moments_title = $("#share_moments_title").val();
            var share_moments_icon = $("#share_moments_icon").val();
            var share_chats_title = $("#share_chats_title").val();
            var share_chats_text = $("#share_chats_text").val();
            var share_chats_icon = $("#share_chats_icon").val();

            //判断是否为微信内核
            if (isWeixin()) {

                $.post(
                    "getWeChatInfo.do",
                    {
                        "url": url
                    },
                    function (res) {
                        //是微信打开
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
                                    if (!test) {
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
                                    }
                                },
                                cancel: function () {
                                    // 用户取消分享后执行的回调函数
                                }
                            });
                        });
                    })
            }

            if (isWeiBo()) {

                $.post(
                    "getWeiBoInfo.do",
                    {
                        "url": url
                    },
                    function (res) {

                        window.WeiboJS.init({
                            'appkey': res.appkey.toString(),
                            'debug': true,
                            'timestamp': res.timestamp,
                            'noncestr': res.nonceStr,
                            'signature': res.signature,
                            'scope': [
                                'getNetworkType',
                                'networkTypeChanged',
                                'getBrowserInfo',
                                'checkAvailability',
                                'setBrowserTitle',
                                'openMenu',
                                'setMenuItems',
                                'menuItemSelected',
                                'setSharingContent',
                                'openImage',
                                'scanQRCode',
                                'pickImage',
                                'getLocation',
                                'pickContact',
                                'apiFromTheFuture'
                            ]
                        }, function (ret) {
                            alert('init done\n' + JSON.stringify(ret));
                            alert("appkey=" + res.appkey.toString() + "，timestamp=" +
                                res.timestamp + ",nonceStr=" + res.nonceStr + ",signature=" + res.signature);
                        });

//                        WeiboJS.invoke("setSharingContent", {
//                            "icon": share_chats_icon,
//                            "title": share_chats_title,
//                            "desc": share_chats_text
//                        }, function (params) {
//                            alert("setMenuItems 返回数据：" + JSON.stringify(params));
//                        });

                        WeiboJS.invoke("setMenuItems", {
                            menus: ["shareToWeibo", "follow"],
                            content: share_chats_title
                        }, function (params) {
                            alert("setMenuItems 返回数据：" + JSON.stringify(params));
                        });
                    }
                );
            }

            if (os === "iOS") {//ios系统的处理
                osType = "ios";
                osVersion = md.os() + md.version("iPhone");
                brand = md.mobile();
                model = md.mobile();
            } else if (os === "AndroidOS") {//Android系统的处理
                osType = "android";
                osVersion = md.os() + md.version("Android");
                var sss = device_type.split(";");
                var flag = false;
                for (var i = 0; i < sss.length; i++) {
                    if (sss[i].indexOf("Build/") !== -1) {
                        brand = md.phone();
                        model = sss[i].substring(0, sss[i].indexOf("Build/"));
                        flag = true;
                    }
                }
                if (!flag) {
                    brand = "other";
                }
            }

            if (isWeixin()) {
                browserType = "wechat";
            } else if (isWeiBo()) {
                browserType = "weibo";
            }

            if (!test) {
                $.post(
                    "collectInformation.do",
                    {
                        "userId": user_id,
                        "categoryId": category_id,
                        "mediaId": media_id,
                        "osType": osType,
                        "osVersion": osVersion,
                        "brand": brand,
                        "browserType": browserType,
                        "model": model,
                        "screenResolution": window.screen.width + "*" + window.screen.height
                    },
                    function (res) {
                    }
                );
            }
        };

        var WxObj = window.navigator.userAgent.toLowerCase();

        //这个函数用来判断当前浏览器是否微信内置浏览器，是微信返回true，不是微信返回false
        function isWeixin() {
            return WxObj.match(/microMessenger/i) === 'micromessenger';
        }

        //这个函数用来判断当前浏览器是否微信内置浏览器，是微信返回true，不是微信返回false
        function isWeiBo() {
            return WxObj.match(/WeiBo/i) === "weibo";
        }

    </script>

</head>
<body style="margin: 0;">

<div style="position: absolute;width: 100%;height: auto;top: 0;">

    <c:choose>
        <c:when test="${test==false}">
            <%--<img src="${pageContext.request.contextPath}/get/getMedia.do?type=1&id=${media_id}"--%>
            <%--style="position:absolute;height: 100%;width: 100%;opacity: 0;z-index: 999">--%>

            <img src="http://file.in-photo.cn/${picWebInfo.backgroundMedia.mediaKey}"
                 style="height: auto;width: 100%;z-index: 0">

            <div style="position: absolute;top: ${picWebInfo.pictureTop}%;bottom: ${picWebInfo.pictureBottom}%;
                    left: ${picWebInfo.pictureLeft}%;right: ${picWebInfo.pictureRight}%;z-index: 10;">
                <img src="http://file.in-photo.cn/${media.mediaKey}"
                     style="height: 100%;width: 100%">
            </div>
        </c:when>
        <c:when test="${category.isVideo==1}">
            <img src="http://file.in-photo.cn/${picWebInfo.backgroundMedia.mediaKey}"
                 style="height: auto;width: 100%;z-index: 0">

            <div style="position: absolute;top: ${picWebInfo.pictureTop}%;bottom: ${picWebInfo.pictureBottom}%;
                    left: ${picWebInfo.pictureLeft}%;right: ${picWebInfo.pictureRight}%;z-index: 10">
                <video src="http://file.in-photo.cn/${media.mediaKey}" style="height: 100%;width: 100%"></video>
            </div>
        </c:when>
        <c:otherwise>
            <%--<img src="${pageContext.request.contextPath}/get/getMedia.do?type=7"--%>
            <%--style="position:absolute;height: 100%;width: 100%;opacity: 0;z-index: 999">--%>

            <img src="http://file.in-photo.cn/${picWebInfo.backgroundMedia.mediaKey}"
                 style="height: auto;width: 100%;z-index: 0">

            <div style="position: absolute;top: ${picWebInfo.pictureTop}%;bottom: ${picWebInfo.pictureBottom}%;
                    left: ${picWebInfo.pictureLeft}%;right: ${picWebInfo.pictureRight}%;z-index: 10">
                <img src="${pageContext.request.contextPath}/images/test.jpg"
                     style="height: 100%;width: 100%">
            </div>
        </c:otherwise>
    </c:choose>

    <div style="display: none">

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
