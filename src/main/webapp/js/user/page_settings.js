/**
 * Created by kaxia on 2017/6/21.
 */

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

function show_click() {
    $("#show_form").ajaxSubmit(
        {
            type: 'post',
            url: "/IN_Photo/setting/test.do",

            //data:  //注意只要是写在表单里面的，都不需要加这个属性。在controller中可以根据@RequestParam String str获取到属性值。
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success: function (data) {
                //接受到的data还只是一个字符串，需要转成json对象
                if (data.success) {
                    alert(data.message);
                } else {
                    alert(data.message);
                }
            },
            error: function (data)//服务器响应失败处理函数
            {
                alert("出错");
            }
        });
}
