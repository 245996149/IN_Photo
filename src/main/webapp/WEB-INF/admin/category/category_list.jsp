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

    <link href="${pageContext.request.contextPath}/css/user/table.css" rel="stylesheet">

</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/admin/menu.jsp"/>

<div class="row">
    <div class="col-md-12">
        <div class="page-header">
            <h1>套餐管理
                <small>套餐管理</small>
            </h1>
        </div>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">数据列表
                    <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
                        添加套餐
                    </button>
                </h3>
            </div>
            <!-- Table -->
            <div class="table-responsive">
                <table class="table <%--table-bordered--%> table-hover <%--dataTable--%>"
                       style="font-size: large;text-align: center;">
                    <thead>
                    <tr>
                        <td>套餐编号</td>
                        <td>套餐简码</td>
                        <td>套餐名称</td>
                        <td>是否生成GIF</td>
                        <td>GIF是否透明</td>
                        <td>备注</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${categoryList}" var="cl">
                        <tr>
                            <td>${cl.categoryId}</td>
                            <td>${cl.categoryCode}</td>
                            <td>${cl.categoryName}</td>
                            <td><c:if test="${cl.madeGif==1}">是</c:if></td>
                            <td><c:if test="${cl.gifTransparency==1}">是</c:if></td>
                            <td>${cl.categoryNote} </td>
                            <td>
                                <div class="btn-group-sm" role="group" aria-label="...">
                                    <button type="button" class="btn btn-primary"
                                            onclick="location='toUpdateCategory.do?category_id=${cl.categoryId}'">
                                        更新信息
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
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
                <h4 class="modal-title" id="myModalLabel">添加套餐</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="${pageContext.request.contextPath}/admin/clientManage/toAddClient.do"
                      id="addFrom">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="categoryName_span">套餐名称</span>
                        <input type="text" class="form-control" placeholder="套餐名称" aria-describedby="Email_span"
                               id="categoryName" name="categoryName">
                    </div>
                    <br/>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="categoryCode_span">套餐简码</span>
                        <input type="text" class="form-control" placeholder="唯一性。套餐简码,用于在登录、上传时识别套餐"
                               aria-describedby="categoryCode_span"
                               id="categoryCode" name="categoryCode">
                    </div>
                    <br/>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="categoryNote_span">备注</span>
                        <input type="text" class="form-control" placeholder="请在此填写本套餐的相关介绍。"
                               aria-describedby="categoryNote_span"
                               id="categoryNote" name="categoryNote">
                    </div>
                    <br/>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="makeGIF" value="true" onchange="changeMakeGIF();">生成GIF
                                </label>
                                <label>
                                    <input type="radio" name="makeGIF" checked value="false"
                                           onchange="changeMakeGIF();">不生成GIF
                                </label>
                                <label>请再三确认是否生成GIF，设置不当会造成添加的套餐上传失败</label>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="form-group" hidden id="tran_div">
                        <div class="col-sm-12">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="GIFtran" value="true">GIF透明
                                </label>
                                <label>
                                    <input type="radio" name="GIFtran" checked value="false">GIF不透明
                                </label>
                            </div>
                        </div>
                    </div>
                    <br/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                <button type="button" class="btn btn-primary" onclick="checkForm();">下一步</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function changeMakeGIF() {

        var makeGIF = $("input[name='makeGIF']:checked").val();

        if (makeGIF == 'true') {
            $('#tran_div').show();
        } else {
            $('#tran_div').hide();
        }

    }

    function checkForm() {
        var forms = $("#addFrom");
        if (!judgeNull(forms)) {
            alert("有必填项为空！");
            return false;
        }

        var code = $("#categoryCode");

        var reg = /^[a-z0-9]+$/;

        if (!code.val().match(reg)) {

            alert("套餐简码只能是小写字母跟数字");
            code.val("");
            return false;
        }


        var makeGIF = $("input[name='makeGIF']:checked").val();

        var GIFtran;

        if (makeGIF == 'false') {
            GIFtran = false;
        } else {
            GIFtran = $("input[name='GIFtran']:checked").val();
        }

        $.post(
            "addCategory.do",
            {
                "name": $("#categoryName").val(),
                "code": code.val(),
                "note": $("#categoryNote").val(),
                "makeGIF": makeGIF,
                "gif_tran": GIFtran
            },
            function (res) {
                if (res.success) {
                    alert(res.message);
                } else {
                    alert(res.message);
                }

            });

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