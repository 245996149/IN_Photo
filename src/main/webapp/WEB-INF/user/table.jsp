<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Table</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/user/table.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body style="padding-top: 70px;">

<!-- 导航栏 -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false"><span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span></button>
            <a class="navbar-brand" href="#">IN Photo</a></div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="#">首页 </a></li>
                <li><a href="#">套餐管理</a></li>
                <li class="dropdown active"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                               aria-haspopup="true" aria-expanded="false">数据管理<span
                        class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">A系统</a></li>
                        <li><a href="#">B系统</a></li>
                        <li><a href="#">C系统</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">回收站</a></li>
                    </ul>
                </li>
                <li><a href="#">页面设置</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                        aria-haspopup="true" aria-expanded="false">今日数据<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">点击量 <span class="badge">42</span></a></li>
                        <li><a href="#">好友分享量 <span class="badge">100</span></a></li>
                        <li><a href="#">朋友圈分享量 <span class="badge">100</span></a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                        aria-haspopup="true" aria-expanded="false">${usersEntity.userName}<span
                        class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">用户资料</a></li>
                        <li><a href="#">安全设置</a></li>
                        <li><a href="#">退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

<%-- 开始 --%>

<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h1>A系统
                <small>绿幕抠像系统</small>
            </h1>
        </div>
    </div>

</div>

<div class="row">
    <div class="col-md-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">七日点击量</h3>
            </div>
            <div class="panel-body">
                <canvas id="myChart" width="400" height="200"></canvas>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">七日分享量</h3>
            </div>
            <div class="panel-body">
                <canvas id="myChart2" width="400" height="200"></canvas>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title">系统使用情况</h3>
            </div>
            <div class="panel-body">
                <canvas id="myChart3" width="400" height="200"></canvas>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <h3 class="panel-title">回收站数据过期情况</h3>
            </div>
            <div class="panel-body">
                <canvas id="myChart4" width="400" height="200"></canvas>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">数据列表</h3>
            </div>
            <!-- Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover dataTable" style="font-size: x-large;">
                    <thead>
                    <tr>
                        <td><input type="checkbox"><span>媒体编号</span>
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                                    操作
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                                    <li><a href="#">下载</a></li>
                                    <li><a href="#">移到回收站</a></li>
                                </ul>
                            </div>
                        </td>
                        <td>缩略图</td>
                        <td>创建日期</td>
                        <td>创建设备</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary btn-lg" data-toggle="modal"
                                        data-target="#myModal">查看
                                </button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="checkbox"><span>12345678934</span></td>
                        <td width="5%"><a href="#" class="thumbnail" style="margin-bottom:auto;">
                            <img src="${pageContext.request.contextPath}/images/test.jpg" alt="...">
                        </a></td>
                        <td>2017-12-30 24:00:00</td>
                        <td>123455</td>
                        <td>
                            <div class="btn-group-sm" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger">删除</button>
                                <button type="button" class="btn btn-primary">查看</button>
                                <button type="button" class="btn btn-info">下载</button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="5" style="text-align: center;">
                            <nav aria-label="Page navigation">
                                <ul class="pagination">
                                    <li class="disabled">
                                        <a href="#" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <li class="active"><a href="#">1</a></li>
                                    <li><a href="#">2</a></li>
                                    <li><a href="#">3</a></li>
                                    <li><a href="#">4</a></li>
                                    <li><a href="#">5</a></li>
                                    <li>
                                        <a href="#" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">
                <div id="myCarousel" class="carousel">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner" id="carousel-object">
                        <div class="item active">
                            <img src="${pageContext.request.contextPath}/images/1.jpg" alt="First slide">
                            <div class="carousel-caption">1</div>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/images/2.jpg" alt="Second slide">
                            <div class="carousel-caption">2</div>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/images/3.jpg" alt="Third slide">
                            <div class="carousel-caption">3</div>
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger">删除</button>
                <button type="button" class="btn btn-primary" onclick="download();">下载</button>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/Chart.js"></script>
<script type="text/javascript">

    var ctx = document.getElementById("myChart").getContext('2d');
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

    var ctx2 = document.getElementById("myChart2").getContext('2d');
    var myChart2 = new Chart(ctx2, {
        type: 'line',
        data: {
            labels: ["January", "February", "March", "April", "May", "June", "July"],
            datasets: [{
                label: "分享到朋友圈",
                data: [65, 59, 80, 81, 56, 55, 40],
                fill: false,
                borderColor: "rgb(75, 192, 192)",
                lineTension: 0.1
            }, {
                label: "分享给好友",
                data: [100, 44, 80, 88, 10, 27, 55],
                fill: false,
                borderColor: "rgb(255, 99, 132)",
                lineTension: 0.1
            }]
        }, options: {}
    });

    var ctx3 = document.getElementById("myChart3").getContext('2d');
    var myChart3 = new Chart(ctx3, {
        type: 'doughnut',
        data: {
            labels: ["已使用", "剩余"],
            datasets: [{
                label: "My First Dataset",
                data: [300, 100],
                backgroundColor: ["rgb(255, 99, 132)", "rgb(54, 162, 235)"]
            }]
        }
    });

    var ctx4 = document.getElementById("myChart4").getContext('2d');
    var myChart4 = new Chart(ctx4, {
        type: 'polarArea',
        data: {
            labels: ["该系统数据总数", "数据7天内过期数", "数据15天内过期数"],
            datasets: [{
                label: "My First Dataset",
                data: [100, 30, 44],
                backgroundColor: ["rgb(255, 99, 132)", "rgb(75, 192, 192)", "rgb(255, 205, 86)",]
            }]
        }
    });

    $('.carousel').carousel({
        interval: false
    });

    /*测试选择*/
    function download() {
        var carousel_obj = $("#carousel-object .active div");
        alert(carousel_obj.text());
    }
</script>

</body>
</html>