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
    <title>INPHOTO管理员系统</title>
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
                <h3 class="panel-title">修改套餐信息</h3>
            </div>
            <div class="panel-body">
                <form method="post" action="${pageContext.request.contextPath}/admin/categoryManage/updateCategory.do">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="id_span">套餐编号</span>
                        <input type="text" class="form-control" readonly value="${category.categoryId}" name="categoryId">
                    </div>
                    <br/>
                    <div class=" input-group input-group-lg">
                        <span class="input-group-addon" id="code_span">套餐简码</span>
                        <input type="text" class="form-control" readonly
                               value="${category.categoryCode}" name="categoryCode">
                    </div>
                    <br/>
                    <div class=" input-group input-group-lg">
                        <span class="input-group-addon" id="name_span">套餐名称</span>
                        <input type="text" class="form-control" readonly
                               value="${category.categoryName}" name="categoryName">
                    </div>
                    <br/>
                    <div class=" input-group input-group-lg">
                        <span class="input-group-addon" id="note_span">备注</span>
                        <input type="text" class="form-control"
                               value="${category.categoryNote}" name="categoryNote">
                    </div>
                    <br/>
                    <div class="form-group" id="makeGif">
                        <div class="col-sm-12">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="madeGif" readonly
                                           <c:if test="${category.madeGif==1}">checked</c:if> value="1">生成GIF
                                </label>
                                <label>
                                    <input type="radio" name="madeGif" readonly
                                           <c:if test="${category.madeGif==0}">checked</c:if> value="0">不生成GIF
                                </label>
                                <label>此项请谨慎修改！修改不当会造成上传失败</label>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="form-group" id="tran_div">
                        <div class="col-sm-12">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="gifTransparency" readonly
                                           <c:if test="${category.gifTransparency==1}">checked</c:if> value="1">GIF透明
                                </label>
                                <label>
                                    <input type="radio" name="gifTransparency" readonly
                                           <c:if test="${category.gifTransparency==0}">checked</c:if> value="0">GIF不透明
                                </label>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12" style="text-align: center">
                            <div class="btn-group" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger" onclick="window.history.back();">返回
                                </button>
                                <button type="submit" class="btn btn-success">确认</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">


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