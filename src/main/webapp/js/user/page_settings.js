/**
 * Created by kaxia on 2017/6/21.
 */

/*设置在进行view操作时为1，code操作是为2*/
var type = 0;

/*判断文件大小*/
var isIE = /msie/i.test(navigator.userAgent) && !window.opera;

function fileChange(target) {
    var fileSize = 0;
    var filetypes = [".jpg", ".png"];
    var filepath = target.value;
    var filemaxsize = 200;//2M
    if (filepath) {
        var isnext = false;
        var fileend = filepath.substring(filepath.indexOf("."));
        if (filetypes && filetypes.length > 0) {
            for (var i = 0; i < filetypes.length; i++) {
                if (filetypes[i] == fileend) {
                    isnext = true;
                    break;
                }
            }
        }
        if (!isnext) {
            alert("不接受此文件类型！");
            target.value = "";
            return false;
        }
    } else {
        return false;
    }
    if (isIE && !target.files) {
        var filePath = target.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        if (!fileSystem.FileExists(filePath)) {
            alert("附件不存在，请重新输入！");
            return false;
        }
        var file = fileSystem.GetFile(filePath);
        fileSize = file.Size;
    } else {
        fileSize = target.files[0].size;
    }

    var size = fileSize / 1024;
    if (size > filemaxsize) {
        alert("附件大小不能大于" + 200 + "Kb！");
        target.value = "";
        return false;
    }
    if (size <= 0) {
        alert("附件大小不能为0M！");
        target.value = "";
        return false;
    }
}


/*提交view预览请求*/
function show_click() {

    var forms = $("#show_form");
    if (!judgeNull(forms)) {
        alert("有必填项为空！");
        return false;
    }

    var page_title = $("#show_page_title");
    var picture_top = $("#show_pic_top");
    var picture_bottom = $("#show_pic_bottom");
    var picture_left = $("#show_pic_left");
    var picture_right = $("#show_pic_right");

    var str_reg = /^.{1,20}$/;
    var num_reg = /^(\d{1,2}(\.\d{1,2})?|100)$/;

    if (!page_title.val().match(str_reg)) {
        alert("页面标题只能输入1-20位字符！");
        page_title.val("");
        return false;
    }

    if (!picture_top.val().match(num_reg) || !picture_bottom.val().match(num_reg)
        || !picture_left.val().match(num_reg) || !picture_right.val().match(num_reg)) {
        alert("百分比只支持100以内、小数点后2位的小数，例如：12.34");
        picture_top.val("");
        picture_bottom.val("");
        picture_right.val("");
        picture_left.val("");
        return false;
    }

    forms.ajaxSubmit(
        {
            type: 'post',
            url: "/IN_Photo/setting/perView.do",

            //data:  //注意只要是写在表单里面的，都不需要加这个属性。在controller中可以根据@RequestParam String str获取到属性值。
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (data) {
                //接受到的data还只是一个字符串，需要转成json对象
                if (data.success) {

                    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                    var pathName = window.document.location.pathname;
                    //获取带"/"的项目名，如：/uimcardprj
                    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

                    $("#modal_img").attr("src", projectName + "/get/getQR.do?url=" + data.url);
                    $("#myModal").modal(
                        {backdrop: 'static'}
                    );
                    type = 1;
                } else {
                    alert(data.message);
                    type = 0;
                }
            },
            error: function (data)//服务器响应失败处理函数
            {
                alert("出错");
                type = 0;
            }
        });
}

/*提交确认请求*/
function validate_request() {

    var category_id = $("#category_id").val();
    var url;

    if (!confirm("请确认更新数据，更新后老数据无法还原，请谨慎操作。")) {
        return false;
    }

    if (type == 1) {//确认view请求
        url = "changeViewPreToNormal.do";
    } else if (type == 2) {//确认code请求
        url = "changeCodePreToNormal.do";
    } else {
        return false
    }

    $.post(
        url,
        {"category_id": category_id},
        function (res) {

            if (res.success) {
                alert(res.message);
                $("#modal_img").attr("src", "#");
                $("#myModal").modal('hide');
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

function code_click() {

    var forms = $("#code_form");
    if (!judgeNull(forms)) {
        alert("有必填项为空！");
        return false;
    }

    var page_title = $("#code_page_title");

    var input_top = $("#code_input_top");
    var input_bottom = $("#code_input_bottom");
    var input_left = $("#code_input_left");
    var input_right = $("#code_input_right");

    var input_bg_color = $("#code_input_bg_color");
    var input_border_color = $("#code_input_border_color");
    var input_text_color = $("#code_input_text_color");

    var button_top = $("#code_button_top");
    var button_bottom = $("#code_button_bottom");
    var button_left = $("#code_button_left");
    var button_right = $("#code_button_right");

    var str_reg = /^.{1,20}$/;
    var num_reg = /^(\d{1,2}(\.\d{1,2})?|100)$/;
    var color_reg = /^([0-9a-fA-F]{6}|[0-9a-fA-F]{3})$/;

    if (!page_title.val().match(str_reg)) {
        alert("页面标题只能输入1-20位字符！");
        page_title.val("");
        return false;
    }

    if (!input_top.val().match(num_reg) || !input_bottom.val().match(num_reg)
        || !input_left.val().match(num_reg) || !input_right.val().match(num_reg)) {
        alert("百分比只支持100以内、小数点后2位的小数，例如：12.34");
        input_top.val("");
        input_bottom.val("");
        input_right.val("");
        input_left.val("");
        return false;
    }

    if (!button_top.val().match(num_reg) || !button_bottom.val().match(num_reg)
        || !button_left.val().match(num_reg) || !button_right.val().match(num_reg)) {
        alert("百分比只支持100以内、小数点后2位的小数，例如：12.34");
        button_top.val("");
        button_bottom.val("");
        button_right.val("");
        button_left.val("");
        return false;
    }

    if (!input_bg_color.val().match(color_reg) || !input_border_color.val().match(color_reg)
        || !input_text_color.val().match(color_reg)) {

        alert("颜色只支持3位、6位颜色值(不含#)，例如#1234af、#FFF");
        input_bg_color.val("");
        input_border_color.val("");
        input_text_color.val("");
        return false;

    }

    forms.ajaxSubmit(
        {
            type: 'post',
            url: "/IN_Photo/setting/perCode.do",

            //data:  //注意只要是写在表单里面的，都不需要加这个属性。在controller中可以根据@RequestParam String str获取到属性值。
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (data) {
                //接受到的data还只是一个字符串，需要转成json对象
                if (data.success) {

                    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                    var pathName = window.document.location.pathname;
                    //获取带"/"的项目名，如：/uimcardprj
                    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

                    $("#modal_img").attr("src", projectName + "/get/getQR.do?url=" + data.url);
                    $("#myModal").modal(
                        {backdrop: 'static'}
                    );
                    type = 2;
                } else {
                    alert(data.message);
                    type = 0;
                }
            },
            error: function (data)//服务器响应失败处理函数
            {
                alert("出错");
                type = 0;
            }
        });

}


function validate_share() {

    if (!confirm("请确认更新数据，更新后老数据无法还原，请谨慎操作。")) {
        return false;
    }

    var forms = $("#share_form");
    if (!judgeNull(forms)) {
        alert("有必填项为空！");
        return false;
    }

    forms.ajaxSubmit(
        {
            type: 'post',
            url: "/IN_Photo/setting/setShareInfo.do",

            //data:  //注意只要是写在表单里面的，都不需要加这个属性。在controller中可以根据@RequestParam String str获取到属性值。
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (data) {
                //接受到的data还只是一个字符串，需要转成json对象
                if (data.success) {
                    alert(data.message);
                    type = 0;
                } else {
                    alert(data.message);
                    type = 0;
                }
            },
            error: function (data)//服务器响应失败处理函数
            {
                alert("出错");
                type = 0;
            }
        });

}
