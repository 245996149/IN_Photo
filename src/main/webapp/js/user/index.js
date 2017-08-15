function checkUserName() {

    var userName = $("#userName");

    if (userName.val() == null || userName.val() == "") {
        alert("用户名不能为空");
        userName.focus();
        return false;
    }

    var nameReg = /^[a-zA-Z]\w{5,17}$/;

    if (!userName.val().match(nameReg)) {
        alert("用户名只能为6-18位的字母开头，包含数字、字母、下划线的组合");
        userName.focus();
        userName.val("");
        return false;
    }
//        console.log(userName);
    $.post(
        "addUserName.do",
        {"user_name": userName.val()},
        function (res) {
            if (res.success) {
                alert(res.message);
                window.location.reload();
            } else {
                alert(res.message);
            }
        }
    )
}

var wait = 60;

function countDown(o) {
    if (wait == 0) {
        $("#getCode").removeAttr("disabled");
        $("#getCode").text("获取验证码");
        wait = 60;
    } else {
        $("#getCode").attr("disabled", true);
        $("#getCode").text("重新发送" + wait);
        wait--;
        setTimeout(function () {
            countDown(o)
        }, 1000);
    }
}

function checkPhone() {
    var input = $("#phone").val();

    // 判断userName的值符合手机号的正则表达式
    var phoneReg = /^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}$/;

    if (!input.match(phoneReg)) {

        $("#getCode").attr("disabled", true);
        return false;

    }

    $("#getCode").removeAttr("disabled");

}

function sendCode() {
    $("#phone_code").removeAttr("disabled");

    var phone = $("#phone").val();

    $.post(
        "sendPhoneCode.do",
        {"phone": phone},
        function (res) {
            if (!res.success) {
                alert(res.message);
            } else {
                countDown(this);
            }
        }
    );
}

function addUserPhone() {
    var phone = $("#phone").val();
    var code = $("#phone_code").val();

    $.post(
        "checkPhoneCode.do",
        {
            "phone": phone,
            "code": code
        },
        function (res) {
            if (res.success) {
                alert(res.message);
                window.location.reload();

            } else {
                alert(res.message);
                return false;
            }
        }
    )
}