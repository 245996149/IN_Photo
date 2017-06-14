<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>PageSettings</title>

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
                <li><a href="#">套餐管理</a></li>
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
                <li class="dropdown active"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                               aria-haspopup="true" aria-expanded="false">页面设置<span
                        class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">A系统</a></li>
                        <li><a href="#">B系统</a></li>
                        <li><a href="#">C系统</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                        aria-haspopup="true" aria-expanded="false">今日数据<span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">点击量 <span class="badge">${sessionScope.click_num}</span></a></li>
                        <li><a href="#">好友分享量 <span class="badge">${sessionScope.chats_num}</span></a></li>
                        <li><a href="#">朋友圈分享量 <span class="badge">${sessionScope.moments_num}</span></a></li>
                    </ul>
                </li>
                <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                        aria-haspopup="true"
                                        aria-expanded="false">${sessionScope.loginUser.userName}<span
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
            <h1>页面设置
                <small>。。。。。</small>
            </h1>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-md-12">
        <div>
            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#mobile_show_settings"
                                                          aria-controls="mobile_show_settings" role="tab"
                                                          data-toggle="tab">展示页面设置</a></li>
                <li role="presentation"><a href="#mobile_code_settings" aria-controls="profile" role="tab"
                                           data-toggle="tab">提取页面设置</a></li>
                <li role="presentation"><a href="#mobile_wechat_settings" aria-controls="messages" role="tab"
                                           data-toggle="tab">微信分享设置</a>
                </li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="mobile_show_settings">
                    <div class="row" style="margin-left: -15px;margin-right: -15px;">
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="show_page_title_span">页面标题</span>
                                        <input type="text" class="form-control" id="show_page_title"
                                               aria-describedby="show_page_title_span">
                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <label for="exampleInputFile">页面背景图片</label>
                                        <input type="file" id="exampleInputFile">
                                        <p class="help-block">
                                            仅支持大小为200kb以内的jpg、png格式的图片，在保证图片质量的情况下，尽量压缩图片，以加快读取速度</p>
                                    </div>
                                    <br>
                                    <label for="show_pic_top">媒体上边距离版面顶部的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="show_pic_top"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <label for="show_pic_bottom">媒体下边距离版面底部的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="show_pic_bottom"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <label for="show_pic_left">媒体左边距离版面左边的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="show_pic_left"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <label for="show_pic_right">媒体右边距离版面右边的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="show_pic_right"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-md-12" style="text-align: center;">
                                            <div class="btn-group" role="group" aria-label="...">
                                                <button type="button" class="btn btn-success">确认</button>
                                                <button type="button" class="btn btn-primary">预览</button>
                                                <button type="button" class="btn btn-danger">恢复默认</button>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">设置说明</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <img src="${pageContext.request.contextPath}/images/show_explain.png"
                                                 alt="..." class="img-rounded" style="max-width: 100%;">
                                        </div>
                                        <div class="col-md-6">
                                            <h3><span class="label label-default"
                                                      style="background-color: red">背景版图</span></h3><br>
                                            <p>
                                                图中红色框位置为背景图片，图片自适应浏览器宽度，并按图片比例设置高度，因各个品牌手机屏幕比例不一关系，推荐使用长图，文件大小小于200kb</p>
                                            <h3> <span class="label label-default"
                                                       style="background-color: #0024ff">页面标题</span></h3><br>
                                            <p>
                                                该颜色框中为页面标题，因各个品牌手机屏幕比例不一关系，页面标题字数应该尽量精简，否则会造成页面标题无法完全显示</p>
                                            <h3>
                                                <span class="label label-default"
                                                      style="background-color: #002e73">上边</span>
                                                <span class="label label-default"
                                                      style="background-color: #ff0000">下边</span>
                                                <span class="label label-default"
                                                      style="background-color: #ffff00">左边</span>
                                                <span class="label label-default"
                                                      style="background-color: #ff00ff">右边</span>
                                            </h3><br>
                                            <p>
                                                该数值用于页面中媒体数据的定位，使用百分比形式，由图可知：媒体数据上边距离页面顶部占页面总长度的20.7%，媒体数据左边距离页面左边占页面总宽度的13.6%。注意媒体数据的比例。</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="mobile_code_settings">
                    <div class="row" style="margin-left: -15px;margin-right: -15px;">
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="code_page_title_span">页面标题</span>
                                        <input type="text" class="form-control" id="code_page_title"
                                               aria-describedby="code_page_title_span">
                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <label for="code_bg">页面背景图片</label>
                                        <input type="file" id="code_bg">
                                        <p class="help-block">
                                            仅支持大小为200kb以内的jpg、png格式的图片，在保证图片质量的情况下，尽量压缩图片，以加快读取速度</p>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-lg-6">
                                            <label for="code_input_top">输入框上边距离版面顶部的百分比</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="code_input_top"
                                                       aria-describedby="basic-addon3">
                                                <span class="input-group-addon">%</span>
                                            </div>
                                            <br>
                                            <label for="code_pic_bottom">输入框下边距离版面底部的百分比</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="code_pic_bottom"
                                                       aria-describedby="basic-addon3">
                                                <span class="input-group-addon">%</span>
                                            </div>
                                            <br>
                                            <label for="code_pic_left">输入框左边距离版面左边的百分比</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="code_pic_left"
                                                       aria-describedby="basic-addon3">
                                                <span class="input-group-addon">%</span>
                                            </div>
                                            <br>
                                            <label for="code_pic_right">输入框右边距离版面右边的百分比</label>
                                            <div class="input-group">
                                                <input type="text" class="form-control" id="code_pic_right"
                                                       aria-describedby="basic-addon3">
                                                <span class="input-group-addon">%</span>
                                            </div>
                                            <br>
                                        </div>
                                        <div class="col-lg-6">
                                            <label for="code_input_bg_color">输入框背景色</label>
                                            <div class="input-group">
                                                <span class="input-group-addon" id="code_input_bg_color_span">#</span>
                                                <input type="text" class="form-control"
                                                       aria-describedby="basic-addon1" id="code_input_bg_color">
                                            </div>
                                            <br>
                                            <label for="code_input_border_color">输入框边框色</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"
                                                      id="code_input_border_color_span">#</span>
                                                <input type="text" class="form-control"
                                                       aria-describedby="basic-addon1" id="code_input_border_color">
                                            </div>
                                            <br>
                                            <label for="code_input_text_color">输入框文本色</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"
                                                      id="code_input_text_color_span">#</span>
                                                <input type="text" class="form-control"
                                                       aria-describedby="basic-addon1" id="code_input_text_color">
                                            </div>
                                            <br>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="form-group">
                                        <label for="button_pic">按钮图片</label>
                                        <input type="file" id="button_pic">
                                        <p class="help-block">
                                            仅支持大小为200kb以内的jpg、png格式的图片，在保证图片质量的情况下，尽量压缩图片，以加快读取速度</p>
                                    </div>
                                    <br>
                                    <label for="code_button_top">按钮上边距离版面顶部的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="code_button_top"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <label for="code_button_bottom">按钮下边距离版面底部的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="code_button_bottom"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <label for="code_button_left">按钮左边距离版面左边的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="code_button_left"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <label for="code_button_right">按钮右边距离版面右边的百分比</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="code_button_right"
                                               aria-describedby="basic-addon3">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                    <br>
                                    <div class="row">
                                        <div class="col-md-12" style="text-align: center;">
                                            <div class="btn-group" role="group" aria-label="...">
                                                <button type="button" class="btn btn-success">确认</button>
                                                <button type="button" class="btn btn-primary">预览</button>
                                                <button type="button" class="btn btn-danger">恢复默认</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">设置说明</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <img src="${pageContext.request.contextPath}/images/code_explain.png"
                                                 alt="..." class="img-rounded" style="max-width: 100%;">
                                        </div>
                                        <div class="col-md-6">
                                            <h3><span class="label label-default"
                                                      style="background-color: #00ffff">背景版图</span></h3><br>
                                            <p>
                                                该颜色框位置为背景图片，图片自适应浏览器宽度，并按图片比例设置高度，因各个品牌手机屏幕比例不一关系，推荐使用长图，文件大小小于200kb</p>
                                            <h3> <span class="label label-default"
                                                       style="background-color: #eeeeee">页面标题</span></h3><br>
                                            <p>
                                                该颜色框中为页面标题，因各个品牌手机屏幕比例不一关系，页面标题字数应该尽量精简，否则会造成页面标题无法完全显示</p>
                                            <h3>
                                                 <span class="label label-default"
                                                       style="background-color: #0000ff">输入框区域</span>
                                                <span class="label label-default"
                                                      style="background-color: #e60012">输入框定位</span>
                                                <span class="label label-default"
                                                      style="background-color: red">输入框边框颜色</span>
                                                <span class="label label-default"
                                                      style="background-color: #e93c77">输入框文本颜色</span>
                                                <span class="label label-default"
                                                      style="background-color: white;color: #0f0f0f;">输入框背景颜色</span>
                                            </h3><br>
                                            <p>
                                                此部分为输入框及其定位配置，输入框定位采用上下左右四边相对于页面四边百分比定位，如图所示：输入框上边相对于页面顶部占页面总长度的31.7%；输入框颜色由文本、边框、背景颜色构成，由图：背景颜色为#ffffff，边框颜色为#ff0000，文本颜色为#d73e71；</p>
                                            <h3>
                                                 <span class="label label-default"
                                                       style="background-color: #ff00ff">确认按钮区域</span>
                                                <span class="label label-default"
                                                      style="background-color: #ff00ff">输入框定位</span>
                                            </h3><br>
                                            <p>
                                                此部分为确认按钮定位配置，确认按钮为png或者jpg图片，如果为异形按钮，需要无底的png图才能保证图片透明部分正确显示；确认按钮定位采用上下左右四边相对于页面四边百分比定位，如图所示：确认按钮上边相对于页面顶部占页面总长度的45%；因手机品牌、平台等原因，输入框及其确认按钮建议靠近页面顶部，输入框与确认按钮间的相对位置不应相距较远。</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="mobile_wechat_settings">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">朋友圈分享设置</h3>
                                        </div>
                                        <div class="panel-body">
                                            <div class="input-group">
                                                <span class="input-group-addon"
                                                      id="share_moments_title_span">分享标题</span>
                                                <input type="text" class="form-control" id="share_moments_title"
                                                       aria-describedby="share_moments_title_span">
                                            </div>
                                            <br>
                                            <div class="form-group">
                                                <label for="share_moments_icon">分享图标</label>
                                                <input type="file" id="share_moments_icon">
                                                <p class="help-block">
                                                    仅支持大小为200kb以内的jpg、png格式，分辨率300*300以内的正方形图片，在保证图片质量的情况下，尽量压缩图片，以加快读取速度</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">分享给好友设置</h3>
                                        </div>
                                        <div class="panel-body">
                                            <div class="input-group">
                                                <span class="input-group-addon" id="share_chats_title_span">分享标题</span>
                                                <input type="text" class="form-control" id="share_chats_title"
                                                       aria-describedby="share_moments_title_span">
                                            </div>
                                            <br>
                                            <div class="input-group">
                                                <span class="input-group-addon" id="share_chats_text_span">分享内容</span>
                                                <input type="text" class="form-control" id="share_chats_text"
                                                       aria-describedby="share_moments_title_span">
                                            </div>
                                            <br>
                                            <div class="form-group">
                                                <label for="share_chats_icon">分享图标</label>
                                                <input type="file" id="share_chats_icon">
                                                <p class="help-block">
                                                    仅支持大小为200kb以内的jpg、png格式，分辨率300*300以内的正方形图片，在保证图片质量的情况下，尽量压缩图片，以加快读取速度</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12" style="text-align: center;">
                                            <div class="btn-group" role="group" aria-label="...">
                                                <button type="button" class="btn btn-success">确认</button>
                                                <button type="button" class="btn btn-primary">预览</button>
                                                <button type="button" class="btn btn-danger">恢复默认</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">设置说明</h3>
                                </div>
                                <div class="panel-body">

                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">分享到朋友圈演示</h3>
                                        </div>
                                        <div class="panel-body">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <img src="${pageContext.request.contextPath}/images/share_moments_explain.png"
                                                         alt="..." class="img-rounded" style="max-width: 100%;">
                                                </div>
                                                <div class="col-md-6">
                                                    <h3><span class="label label-success">分享到朋友圈</span></h3><br>
                                                    <p>
                                                        朋友圈分享由两部分构成：分享标题、分享图标。由于手机平台等原因，标题过长会造成标题过长部分折叠，所以标题应当精简，标题支持1-30长度字符串；分享图标需使用200kb以内的jpg、png格式的图片，建议使用正方形图片，长方形有可能会造成图片变形。</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">分享给好友演示</h3>
                                        </div>
                                        <div class="panel-body">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <img src="${pageContext.request.contextPath}/images/share_chats_explain.png"
                                                         alt="..." class="img-rounded" style="max-width: 100%;">
                                                </div>
                                                <div class="col-md-6">
                                                    <h3><span class="label label-danger">分享给好友</span></h3><br>
                                                    <p>
                                                        好友分享由三部分构成：分享标题、分享内容、分享图标。由于手机平台等原因，标题过长会造成标题过长部分折叠，所以标题应当精简，标题支持1-30长度字符串；内容题过长会造成过长部分折叠，内容支持1-50长度字符串；分享图标需使用200kb以内的jpg、png格式的图片，建议使用正方形图片，长方形有可能会造成图片变形。</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>