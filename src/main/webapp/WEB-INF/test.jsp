<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="viewport" content="width=device-width,user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>测试</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>

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
                    "/IN_Photo/mobile/getWeChatInfo.do",
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
        
        function mapp() {
            wx.openLocation({
                latitude: 31.339275, // 纬度，浮点数，范围为90 ~ -90
                longitude: 121.436639, // 经度，浮点数，范围为180 ~ -180。
                name: '赢秀多媒体', // 位置名
                address: '上海赢秀多媒体科技有限公司', // 地址详情说明
                scale: 28, // 地图缩放级别,整形值,范围从1~28。默认为最大
                infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
            });
        }
    </script>

</head>
<body>

<button value="按钮" onclick="mapp();">按钮</button>

</body>
</html>