<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Category</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

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
                <li class="active"><a href="#">套餐管理</a></li>
                <li class="dropdown "><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
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
            <h1>套餐管理
                <small>。。。。。</small>
            </h1>
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
                <table class="table" style="text-align: center;">
                    <thead>
                    <tr>
                        <td>套餐id</td>
                        <td>套餐名称</td>
                        <td>支付时间</td>
                        <td>生效时间</td>
                        <td>截至时间</td>
                        <td>套餐使用情况</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 60%;min-width: 2%;">
                                    60%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 10%;min-width: 2em;">
                                    10%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 5%;min-width: 1em;">
                                    5%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 2%;min-width: 1.5em;">
                                    2%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>100</td>
                        <td>铝木抠像系统</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>2017-01-01 12:00:00</td>
                        <td>
                            <div class="progress">
                                <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                     aria-valuemax="100" style="width: 0%;min-width: 2%;">
                                    0%
                                </div>
                            </div>
                        </td>
                        <td>操作</td>
                    </tr>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="7" style="text-align: center;">
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


</body>
</html>