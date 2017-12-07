<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 17-11-30
  Time: 下午5:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/js/mobile/mobile-detect.min.js"></script>
    <script type="text/javascript">
        var device_type = window.navigator.userAgent;
        window.onload = function () {
            document.write(device_type);//打印到页面
            var md = new MobileDetect(device_type);//初始化mobile-detect
            var os = md.os();//获取系统
            var osType = "other";
            var osVersion = "other";
            var brand = "other";
            var browserType = "other";
            var model = "other";
            if (os == "iOS") {//ios系统的处理
                osType = "ios";
                osVersion = md.os() + md.version("iPhone");
                brand = md.mobile();
                model = "iPhone";
            } else if (os == "AndroidOS") {//Android系统的处理
                osType = "android";
                osVersion = md.os() + md.version("Android");
                var sss = device_type.split(";");
                var flag = false;
                for (var i = 0; i < sss.length; i++) {
                    if (sss[i].indexOf("Build/") !== -1) {
                        brand = md.mobile();
                        model = sss[i].substring(0, sss[i].indexOf("Build/"));
                        flag = true;
                    }
                }
                if (!flag) {
                    brand = "other";
                }


            }

            if (device_type.toLowerCase().match(/MicroMessenger/i) == 'micromessenger') {
                browserType = "wechat";
            } else if (device_type.toLowerCase().match(/WeiBo/i) == "weibo") {
                browserType = "weibo";
            }

            var screenResolution = window.screen.width + "*" + window.screen.height;

            $.post(
                "collectInformation.do",
                {
                    "osType": osType,
                    "osVersion": osVersion,
                    "brand": brand,
                    "browserType": browserType,
                    "model": model,
                    "screenResolution":screenResolution
                },
                function (res) {
                    alert(res.data);
                }
            );

        }
    </script>
</head>
<body>

</body>
</html>
