function selectType(type, type_name) {
    $("#login_type").val(type);
    $("#inputText").attr("name", type);
    $("#inputButton").html(type_name + "<span class='caret'></span>");
}

function check_admin() {

    $("#error_message").hide();

    var input_text = $("#input_text").val();
    var password = $("#inputPassword").val();
    var login_type = $("#login_type").val();
    var error_message = $("#error_message");
    var remLogin = document.getElementById("remLogin").checked;

    if (input_text == "" || password == "" || input_text == null || password == null) {
        error_message.text("有必填项为空");
        error_message.show();
        return false;
    }

    var reg;
    var error_msg_text;

    if (login_type == 0) {
        // 以字母开头，长度在6~18之间，只能包含字母、数字和下划线 用户名正则表达式
        reg = /^[a-zA-Z]\w{5,17}$/;
        error_msg_text = "用户名格式有误，请确认用户名是否正确";
    } else if (login_type == 1) {
        // 手机号正则表达式
        reg = /^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/;
        error_msg_text = "手机号格式有误，请确认用户名是否正确";
    } else if (login_type == 2) {
        // 邮箱正则表达式
        reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        error_msg_text = "邮箱格式有误，请确认用户名是否正确";
    }

    if (!input_text.match(reg)) {
        error_message.text(error_msg_text);
        error_message.show();
        return false;
    }

    var passReg = /^[a-zA-Z]\w{7,17}$/;

    if (!password.match(passReg)) {
        error_message.text("密码错误，请重新填写");
        error_message.show();
        return false;
    }

    $.post(
        "checkAdmin.do",
        {
            "input_text": input_text,
            "password": password,
            "login_type": login_type,
            "remLogin": remLogin
        },
        function (res) {
            if (res.success) {
                error_message.text("登陆成功");
                error_message.show();

                //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
                var curWwwPath = window.document.location.href;
                //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                var pathName = window.document.location.pathname;
                var pos = curWwwPath.indexOf(pathName);
                //获取主机地址，如： http://localhost:8083
                var localhostPaht = curWwwPath.substring(0, pos);
                //获取带"/"的项目名，如：/uimcardprj
                var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

                var url = localhostPaht + projectName + "/admin/index.do";

                location.href = url;

            } else {
                error_message.text(res.message);
                error_message.show();
                setTimeout(function () {
                    $("#error_message").hide();
                }, 5000);
                return false;
            }
        }
    );

}

function check_user() {

    $("#error_message").hide();

    var input_text = $("#input_text").val();
    var password = $("#inputPassword").val();
    var login_type = $("#login_type").val();
    var error_message = $("#error_message");
    var remLogin = document.getElementById("remLogin").checked;

    if (input_text == "" || password == "" || input_text == null || password == null) {
        error_message.text("有必填项为空");
        error_message.show();
        return false;
    }

    var reg;
    var error_msg_text;

    if (login_type == 0) {
        // 以字母开头，长度在6~18之间，只能包含字母、数字和下划线 用户名正则表达式
        reg = /^[a-zA-Z]\w{5,17}$/;
        error_msg_text = "用户名格式有误，请确认用户名是否正确";
    } else if (login_type == 1) {
        // 手机号正则表达式
        reg = /^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/;
        error_msg_text = "手机号格式有误，请确认用户名是否正确";
    } else if (login_type == 2) {
        // 邮箱正则表达式
        reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        error_msg_text = "邮箱格式有误，请确认用户名是否正确";
    }

    if (!input_text.match(reg)) {
        error_message.text(error_msg_text);
        error_message.show();
        return false;
    }

    var passReg = /^[a-zA-Z]\w{7,17}$/;

    if (!password.match(passReg)) {
        error_message.text("密码错误，请重新填写");
        error_message.show();
        return false;
    }

    $.post(
        "checkUser.do",
        {
            "input_text": input_text,
            "password": password,
            "login_type": login_type,
            "remLogin": remLogin
        },
        function (res) {
            if (res.success) {
                error_message.text("登陆成功");
                error_message.show();

                //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
                var curWwwPath = window.document.location.href;
                //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                var pathName = window.document.location.pathname;
                var pos = curWwwPath.indexOf(pathName);
                //获取主机地址，如： http://localhost:8083
                var localhostPaht = curWwwPath.substring(0, pos);
                //获取带"/"的项目名，如：/uimcardprj
                var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);

                var url = localhostPaht + projectName + "/user/index.do";

                location.href = url;

            } else {
                error_message.text(res.message);
                error_message.show();
                setTimeout(function () {
                    $("#error_message").hide();
                }, 5000);
                return false;
            }
        }
    );


}