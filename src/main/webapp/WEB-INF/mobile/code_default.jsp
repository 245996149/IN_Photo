<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,user-scalable=no">

    <title>我们制造最美的笑容</title>

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

    <link href="${pageContext.request.contextPath}/css/mobile/code.css" rel="stylesheet">


    <script language="javascript" type="text/javascript"
            src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script language="javascript" type="text/javascript"
            src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

    <script type="text/javascript">

        //网页加载后执行函数
        window.onload = function () {

            //判断是否为微信内核
            if (isWeixin()) {
                //是微信打开

                var url = location.href;
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

                            wx.hideAllNonBaseMenuItem();

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

        function check_code() {
            var code = $("#code").val();
            var user_id = $("#user_id").val();
            var category_id = $("#category_id").val();

            var reg = /^(\d{6})?$/;
            if (!code.match(reg)) {
                alert("您输入验证码有误，请检查之后重新输入！");
                $("#code").val("");
                return;
            }

            $.post("checkCode.do",
                {
                    "user_id": user_id,
                    "category_id": category_id,
                    "code": code
                },
                function (res) {
                    if (res.success) {
                        location.replace(res.page_url);
                    } else {
                        alert(res.message);
                        $("#code").val("");
                    }

                });
        }

        window.alert = function (name) {
            var iframe = document.createElement("IFRAME");
            iframe.style.display = "none";
            iframe.setAttribute("src", 'data:text/plain,');
            document.documentElement.appendChild(iframe);
            window.frames[0].window.alert(name);
            iframe.parentNode.removeChild(iframe);
        };

    </script>

</head>
<body>

<div class="viewDiv">

    <img src="${pageContext.request.contextPath}/images/mobile/code_default.png"
         style="width: 100%;height: auto;z-index: 0;">

    <div class="inputDiv">
        <input id="code" oninput="if(value.length>6)value=value.slice(0,6);" type="number" pattern="[0-9]*" autofocus/>
    </div>

    <div class="buttonDiv" onclick="check_code();">
        <img src="${pageContext.request.contextPath}/images/mobile/code_con.png"
             style="width: 100%;height: 100%"/>
    </div>

    <div style="display: none">
        <input id="user_id" value="${userCategory.userId}"/>
        <input id="category_id" value="${userCategory.categoryId}"/>
    </div>

</div>

</body>
</html>
