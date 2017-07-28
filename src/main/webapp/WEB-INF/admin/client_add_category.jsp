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
    <title>IN Photo管理员系统</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<div class="row">
    <div class="col-md-12">

    </div>
</div>

<div class="row">
    <div class="col-md-offset-4 col-md-4 col-xs-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">添加套餐</h3>
            </div>
            <div class="panel-body">
                <input type="text" hidden id="user_id" name="user_id" value="${user.userId}">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" id="user_name">用户名</span>
                    <input type="text" class="form-control" placeholder="客户未填写用户名" aria-describedby="user_name"
                           readonly
                           value="${user.userName}">
                </div>
                <br/>
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="phone">手机号</span>
                    <input type="text" class="form-control" placeholder="客户未填写手机号" aria-describedby="phone" readonly
                           value="${user.phone}">
                </div>
                <br/>
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="email">邮箱</span>
                    <input type="text" class="form-control" placeholder="客户未填写邮箱" aria-describedby="email" readonly
                           value="${user.email}">
                </div>
                <br/>
                <div class=" input-group input-group-lg">
                    <span class="input-group-addon" id="company">公司</span>
                    <input type="text" class="form-control" placeholder="客户未填写公司" aria-describedby="company" readonly
                           value="${user.company}">
                </div>
                <br/>
                <div class="input-group input-group-lg">
                    <div class="input-group-btn">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">下拉选择<span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <c:forEach items="${categoryList}" var="cl">
                                <li><a href="#"
                                       onclick="selectCategory(${cl.categoryId},'${cl.categoryName}');">${cl.categoryName}</a>
                                </li>
                                <%--<option value="${cl.categoryId}">${cl.categoryName}</option>--%>
                            </c:forEach>
                        </ul>
                    </div>
                    <input type="text" id="category_name" placeholder="点击左侧，选择要添加的系统" readonly class="form-control"
                           aria-label="Text input with segmented button dropdown">
                </div>
                <br/>
                <form id="check_form">
                    <div class=" input-group input-group-lg">
                        <span class="input-group-addon" id="begin_date_span">生效日期</span>
                        <input type="date" class="form-control" id="begin_date" name="begin_date"
                               onchange="settingBeginDate();">
                    </div>
                    <br/>
                    <div class=" input-group input-group-lg">
                        <span class="input-group-addon" id="end_date_span">过期日期</span>
                        <input type="date" class="form-control" id="end_date" name="end_date">
                    </div>
                    <br/>
                    <div class=" input-group input-group-lg">
                        <span class="input-group-addon" id="number_span">数据量</span>
                        <input type="number" class="form-control"
                               aria-describedby="beginDate" id="number" name="number">
                    </div>
                </form>
                <br/>
                <div class="row">
                    <div class="col-md-12" style="text-align: center">
                        <div class="btn-group" role="group" aria-label="...">
                            <button type="button" class="btn btn-danger">返回</button>
                            <button type="button" class="btn btn-success" onclick="sendData();">确认</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    var category;

    var today;

    function selectCategory(category_id, category_name) {

        category = category_id;

        $("#category_name").val(category_name);

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

        var begin_date = $("#begin_date");

        begin_date.val(today);

        begin_date.attr("min", today);

        var end_date_str = getNewDay(today, 365);

        var end_date = $("#end_date");

        end_date.val(end_date_str);

        end_date.attr("min", getNewDay(today, 1));

    }

    function settingBeginDate() {

        var begin_date = $("#begin_date");

        var end_date = $("#end_date");

        end_date.val("");

        end_date.attr("min", getNewDay(begin_date.val(), 1));

    }

    function sendData() {

        if (category == null) {
            alert("没有选择套餐，请返回选择套餐")
            return false;
        }

        var forms = $("#check_form");
        if (!judgeNull(forms)) {
            alert("有必填项为空！");
            return false;
        }

        $.post(
            "",
            {
                "user_id": $("#user_id").val(),
                "category_id": category,
                "begin_date": $("#begin_date").val(),
                "end_date": $("#end_date").val(),
                "number": $("#number").val()
            },
            function (res) {
                alert(res.message);
            }
        );

    }

    //日期加上天数得到新的日期
    //dateTemp 需要参加计算的日期，days要添加的天数，返回新的日期，日期格式：YYYY-MM-DD
    function getNewDay(dateTemp, days) {
        var dateTemp = dateTemp.split("-");
        var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式
        var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);
        var rDate = new Date(millSeconds);
        var year = rDate.getFullYear();
        var month = rDate.getMonth() + 1;
        if (month < 10) month = "0" + month;
        var date = rDate.getDate();
        if (date < 10) date = "0" + date;
        return (year + "-" + month + "-" + date);
    }

    window.onload = function () {
        onloadSetting();
    }

    /*判断表单中是否有空*/
    function judgeNull(a) {
        var forms = a.find('input');
        //alert(forms.length);
        for (var i = 0; i < forms.length; i++) {
            if (forms[i].value == "") {
                forms[i].focus();
                //alert("有选项为空");
                return false;
            }
        }
        return true;
    }
</script>
</body>
</html>