<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Bootstrap 101 Template</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/user/menu.jsp"/>

<h1>你好，世界！</h1>
<a type="button" href="javascript:takeScreenshot();">aaaaa</a>
<a type="button" id="bbbbb">bbbbb</a>

<div class="row">
    <div class="col-lg-offset-3 col-lg-6  col-md-offset-2 col-md-8 col-sm-12 col-xs-12" id="aaaaa"
         style="background-color: white;">
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
            <canvas id="myChart" width="200" height="100"></canvas>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
            <canvas id="myChart2" width="200" height="100"></canvas>
        </div>
    </div>
</div>
<c:if test="${sessionScope.loginUser.userName==null||sessionScope.loginUser.userName==''}">
    <!-- 填写账户名Modal -->
    <div class="modal fade" id="nameModal" tabindex="-1" role="dialog" aria-labelledby="nameModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">请填写用户名</h4>
                </div>
                <div class="modal-body">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon">用户名</span>
                        <input type="text" class="form-control"
                               id="userName" pattern="[a-zA-Z]\w{5,17}$/">
                    </div>
                    <br/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="checkUserName();">确认</button>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${sessionScope.loginUser.userName!=null && sessionScope.loginUser.userName!=''}">
    <c:if test="${sessionScope.loginUser.phone==null || sessionScope.loginUser.phone ==''}">
        <!-- 填写手机号Modal -->
        <div class="modal fade" id="phoneModal" tabindex="-1" role="dialog" aria-labelledby="phoneModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">请填写用户手机号</h4>
                    </div>
                    <div class="modal-body">

                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">手机号</span>
                            <input type="number" class="form-control" id="phone" pattern="[0-9]*"
                                   oninput="checkPhone();">
                        </div>
                        <br/>
                        <div class="input-group input-group-lg">
                            <span class="input-group-addon">验证码</span>
                            <input type="number" class="form-control"
                                   id="phone_code" pattern="[0-9]*" disabled>
                            <span class="input-group-btn">
                                    <button class="btn btn-success" type="button" id="getCode" disabled
                                            onclick="sendCode();">发送验证码</button>
                                </span>
                        </div>
                        <br/>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" onclick="addUserPhone();">确认</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</c:if>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/user/index.js"></script>
<script src="${pageContext.request.contextPath}/js/Chart.js"></script>
<script src="${pageContext.request.contextPath}/js/html2canvas.js"></script>
<script type="text/javascript">
    $(function () {
        var user = '${sessionScope.loginUser.userName}';
        var phone = '${sessionScope.loginUser.phone}';

        if (user == null || user == "") {
            $('#nameModal').modal({
                show: true,
                backdrop: 'static'
            });
        }
        if (user != null && user != "") {
            if (phone == null || phone == "") {
                $('#phoneModal').modal({
                    show: true,
                    backdrop: 'static'
                });
            }

        }

    });

    var ctx = document.getElementById("myChart").getContext('2d');
    var ctx2 = document.getElementById("myChart2").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
            datasets: [{
                label: '# 点击量',
                data: [12, 19, 3, 5, 2, 3],
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255,99,132,1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });

    var recycle_info = new Chart(ctx2, {
            type: 'polarArea',
            data: {
                labels: ["该系统数据总数", "数据7天内过期数", "数据15天内过期数"],
                datasets: [{
                    label: "回收站数据过期情况",
                    data: [30, 90, 24],
                    backgroundColor: ["rgb(255, 99, 132)", "rgb(75, 192, 192)", "rgb(255, 205, 86)"]
                }]
            }
        })
    ;

    function takeScreenshot() {

        html2canvas($("#aaaaa"), {
            onrendered: function (canvas) {
                $('#bbbbb').attr('href', canvas.toDataURL());
                $('#bbbbb').attr('download', 'myjobdeer.png');
                //$('#down_button').css('display','inline-block');
                var html_canvas = canvas.toDataURL();
                $.post('', {
                    order_id: 1,
                    type_id: 2,
                    html_canvas: html_canvas
                }, function (json) {
                }, 'json');
            }
        });

    }

</script>
</body>
</html>