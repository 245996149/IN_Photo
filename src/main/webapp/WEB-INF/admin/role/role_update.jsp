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
                <h3 class="panel-title">更新角色信息</h3>
            </div>
            <div class="panel-body">
                <form method="post" id="updateForm"
                      action="${pageContext.request.contextPath}/admin/roleManage/updateRole.do">
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="id_span">角色id</span>
                        <input type="text" class="form-control" name="roleId" value="${role.roleId}" readonly>
                    </div>
                    <br/>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon" id="name_span">角色名称</span>
                        <input type="text" class="form-control" name="name" value="${role.name}">
                    </div>
                    <br/>
                    <label>设置权限：</label>
                    <div class="form-group" id="makeGif">
                        <div class="col-sm-12">
                            <div class="checkbox">
                                <c:forEach items="${moduleList}" var="m">
                                    <label class="checkbox-inline">
                                        <input type="checkbox" name="moduleIds" value="${m.moduleId}"
                                        <c:forEach items="${role.moduleInfoSet}" var="rm">
                                               <c:if test="${rm.moduleId==m.moduleId}">checked</c:if>
                                        </c:forEach>
                                               onchange="checkCheckbox();"> ${m.name}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12" style="text-align: center">
                            <div class="btn-group" role="group" aria-label="...">
                                <button type="button" class="btn btn-danger" onclick="window.history.back();">返回
                                </button>
                                <button type="button" onclick="checkForm();" class="btn btn-success">确认</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">

    function checkCheckbox() {
        var a = $("input[name='moduleIds']:checked").length;
        if (a == 0) {
            alert("最少选择一个");
            return false;
        } else {
            return true;
        }
    }

    function checkForm() {
        var forms = $("#updateForm");
        if (!judgeNull(forms)) {
            alert("有必填项为空！");
            return false;
        }

        if (!checkCheckbox()) {
            return false;
        }else {
            forms.submit();
        }


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