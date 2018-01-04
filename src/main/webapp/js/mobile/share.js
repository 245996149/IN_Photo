var WxObj = window.navigator.userAgent.toLowerCase();

//这个函数用来判断当前浏览器是否微信内置浏览器，是微信返回true，不是微信返回false
function isWeixin() {
    return WxObj.match(/microMessenger/i) == 'micromessenger';
}

//这个函数用来判断当前浏览器是否微信内置浏览器，是微信返回true，不是微信返回false
function isWeiBo() {
    return WxObj.match(/WeiBo/i) == "weibo";
}


function share() {
    var user_id = $("#user_id").val();
    var category_id = $("#category_id").val();
    var media_id = $("#media_id").val();
    var test = $("#test").val();

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
}

function collectInformation() {

    var user_id = $("#user_id").val();
    var category_id = $("#category_id").val();
    var media_id = $("#media_id").val();

    var device_type = window.navigator.userAgent;
    var md = new MobileDetect(device_type);//初始化mobile-detect
    var os = md.os();//获取系统
    var osType = "otherOs";
    var osVersion = "otherVersion";
    var brand = "otherBrand";
    var browserType = "otherBrowser";
    var model = "otherModel";


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

/*
显示微博分享
 */
function createWeibo() {
    if (isWeiBo()) {
        $("#weibo_share_div").show();
    }
}

/*
分享到微博
 */
function shareToWeibo(pic_url) {

    var share_chats_title = $("#share_chats_title").val();

    var share_chats_text = $("#share_chats_text").val();

    var f = 'http://v.t.sina.com.cn/share/share.php?appkey=' + share_chats_title + '&url='
        + encodeURIComponent(location.href) + '&title=' + share_chats_text
        + '&pic=' + encodeURIComponent(pic_url) + '&content=utf-8';

    window.location.href = f;
}