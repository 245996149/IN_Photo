var t1;
var t2;

/*查询7天内的点击数*/
function getClickData(beginDate, endDate) {

    var category_id = $("#category_id").val();

    $.post(
        "getShareData.do",
        {
            "category_id": category_id,
            "type": 1,
            "begin_date": beginDate,
            "end_date": endDate
        },
        function (res) {
            var labelsArr = new Array(res.length);
            var click_data = new Array(res.length);
            var upload_data = new Array(res.length);
            for (var i = 0; i < res.length; i++) {
                labelsArr[i] = res[i].name;
                click_data[i] = res[i].click_num;
                upload_data[i] = res[i].upload_num;
            }

            var ctx = document.getElementById("click_7").getContext('2d');

            var click_7 = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labelsArr,
                    datasets: [{
                        label: '点击量',
                        data: click_data,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255,99,132,1)',
                        borderWidth: 1
                    }, {
                        label: '上传量',
                        data: upload_data,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192,1)',
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

        }
    );

}

/*查询7天内的分享数*/
function getShareDate(beginDate, endDate) {

    var category_id = $("#category_id").val();

    $.post(
        "getShareData.do",
        {
            "category_id": category_id,
            "type": 2,
            "begin_date": beginDate,
            "end_date": endDate
        },
        function (res) {

            var labels_arr = new Array(res.length);
            var chats_data_arr = new Array(res.length);
            var moments_data_arr = new Array(res.length);
            for (var i = 0; i < res.length; i++) {
                labels_arr[i] = res[i].name;
                chats_data_arr[i] = res[i].chats_num;
                moments_data_arr[i] = res[i].moments_num;
            }

            var ctx2 = document.getElementById("share_7").getContext('2d');
            var share_7 = new Chart(ctx2, {
                type: 'line',
                data: {
                    labels: labels_arr,
                    datasets: [{
                        label: "分享到朋友圈",
                        data: moments_data_arr,
                        fill: false,
                        borderColor: "rgb(75, 192, 192)",
                        lineTension: 0.1
                    }, {
                        label: "分享给好友",
                        data: chats_data_arr,
                        fill: false,
                        borderColor: "rgb(255, 99, 132)",
                        lineTension: 0.1
                    }]
                }, options: {}
            });

        }
    );

}

/*查询套餐系统的使用情况*/
function getSystemInfo() {

    var category_id = $("#category_id").val();

    $.post(
        "getSystemInfo.do",
        {
            "category_id": category_id
        },
        function (res) {
            var data_arr = new Array(2);
            data_arr[0] = res.use;
            data_arr[1] = res.remaining;

            var ctx3 = document.getElementById("system_info").getContext('2d');
            var system_info = new Chart(ctx3, {
                type: 'doughnut',
                data: {
                    labels: ["已使用", "剩余"],
                    datasets: [{
                        label: "My First Dataset",
                        data: data_arr,
                        backgroundColor: ["rgb(255, 99, 132)", "rgb(54, 162, 235)"]
                    }]
                }
            });

        });

}

/*查询回收站媒体情况*/
// function getRecycleInfo() {
//
//     var category_id = $("#category_id").val();
//
//     $.post(
//         "getRecycleInfo.do",
//         {
//             "category_id": category_id
//         },
//         function (res) {
//             var data_arr = new Array(3);
//             data_arr[0] = res.recycle_total;
//             data_arr[1] = res.recycle_7;
//             data_arr[2] = res.recycle_15;
//
//             var ctx4 = document.getElementById("recycle_info").getContext('2d');
//             var recycle_info = new Chart(ctx4, {
//                 type: 'polarArea',
//                 data: {
//                     labels: ["该系统数据总数", "数据7天内过期数", "数据15天内过期数"],
//                     datasets: [{
//                         label: "回收站数据过期情况",
//                         data: data_arr,
//                         backgroundColor: ["rgb(255, 99, 132)", "rgb(75, 192, 192)", "rgb(255, 205, 86)"]
//                     }]
//                 }
//             });
//
//         });
//
// }

/*打开model*/
function open_modal(mediaName) {

    var lazy = $("img[name='lazy']");

    lazy.each(function () {
        $(this).attr('src', $(this).attr('lz-src'));
    });

    var ol = $("#myCarousel ol li");
    var carousel = $("#carousel-object div");

    for (var i = 0; i < ol.length; i++) {

        if (mediaName == $(ol[i]).attr("data-media-name")) {

            $(ol[i]).addClass("active");

            for (var i = 0; i < carousel.length; i++) {

                if (mediaName == $(carousel[i]).attr("data-media-name")) {

                    $(carousel[i]).addClass("active");

                } else {

                    $(carousel[i]).removeClass("active");

                }

            }

        } else {

            $(ol[i]).removeClass("active");

        }

    }

    $('#myModal').modal('show');

}

/*判断顶部checkbox逻辑*/
function DoCheck() {
    var media_data_checkbox = document.getElementsByName("media_data_checkbox");
    if (document.getElementById("media_data_all_checkbox").checked == true) {
        for (var i = 0; i < media_data_checkbox.length; i++) {
            media_data_checkbox[i].checked = true;
        }
        $("#media_data_operation").show();
    } else {
        for (var i = 0; i < media_data_checkbox.length; i++) {
            media_data_checkbox[i].checked = false;
        }
        $("#media_data_operation").hide();
    }
}

/*判断其他checkbox逻辑*/
function checkAllCheck() {
    var media_data_checkbox = document.getElementsByName("media_data_checkbox");
    for (var i = 0; i < media_data_checkbox.length - 1; i++) {
        if (media_data_checkbox[i].checked != media_data_checkbox[i + 1].checked) {
            document.getElementById("media_data_all_checkbox").checked = false;
            $("#media_data_operation").show();
            return;
        }
    }
    if (media_data_checkbox[0].checked == true) {
        document.getElementById("media_data_all_checkbox").checked = true;
        $("#media_data_operation").show();
    } else {
        document.getElementById("media_data_all_checkbox").checked = false;
        $("#media_data_operation").hide();
    }
}

/*modal中的下载按钮的下载逻辑*/
function modal_download() {

    var carousel_obj = $("#carousel-object .active img");

    download(carousel_obj.attr("alt"));

}

/*页面中的下载按钮的下载逻辑*/
function download(mediaKey) {

    window.location.href = "http://file.in-photo.cn/" + mediaKey;

}

/*批量下载按钮的下载逻辑*/
function downloadImgZip() {

    if (!confirm("批量下载需要打包，打包时间由媒体数据量决定，请耐心等候")) {
        return;
    }

    var media_data_checkbox = document.getElementsByName("media_data_checkbox");
    var _list = [];
    var _list_num = 0;
    for (var i = 0; i < media_data_checkbox.length; i++) {
        if (media_data_checkbox[i].checked == true) {
            _list[_list_num] = media_data_checkbox[i].value;
            _list_num++;
        }
    }
    // location.href = "/IN_Photo/get/getMedias.do?media_id_list=" + JSON.stringify(_list);
    $.post(
        "/IN_Photo/get/getMedias.do",
        {
            "media_id_list": JSON.stringify(_list)
        },
        function (res) {
            if (res.success) {
                t2 = setInterval(function () {
                    checkZipCreateStatesOpen(res.message)
                }, 3000);
            }
        }
    )
}

/*modal中的删除媒体*/
function modal_delete() {

    var carousel_obj = $("#carousel-object .active img");

    delete_media(carousel_obj.attr("alt"));
}

/*页面中的删除媒体*/
function delete_media(id) {

    if (!confirm("请确认将文件移入到回收站中，回收站中有30天的缓存时间，超过30天，系统将会彻底清理文件")) {
        return;
    }

    $.post(
        "deleteMediaData.do",
        {"media_id": id},
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

function delete_batch() {

    if (!confirm("请确认将这些文件移入到回收站中，回收站中有30天的缓存时间，超过30天，系统将会彻底清理文件")) {
        return;
    }

    var media_data_checkbox = document.getElementsByName("media_data_checkbox");
    var _list = [];
    var _list_num = 0;
    for (var i = 0; i < media_data_checkbox.length; i++) {
        if (media_data_checkbox[i].checked == true) {
            _list[_list_num] = media_data_checkbox[i].value;
            _list_num++;
        }
    }

    $.post(
        "deleteMediaDataList.do",
        {"media_id_list": JSON.stringify(_list)},
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

function settingDownloadInput() {

    var begin_date = $("#download_begin_date");

    var end_date = $("#download_end_date");

    settingEndDateInput(begin_date, end_date);

}

function settingFindDateInput() {

    var begin_date = $("#find__begin_date");

    var end_date = $("#find__end_date");

    settingEndDateInput(begin_date, end_date);

}

function settingEndDateInput(begin_date_input, end_date_input) {
    end_date_input.attr("min", begin_date_input.val());
}


function getNewDay(dateTemp, days) {
    var dateTemp = dateTemp.split("-");
    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式
    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);
    var rDate = new Date(millSeconds);
    var year = rDate.getFullYear();
    var month = rDate.getMonth() + 1;
    if (month < 10) month = "0" + month;
    var date = rDate.getDate();
    if (date < 10) date = "0" + date;
    return (year + "-" + month + "-" + date);
}

function findDate() {
    var begin_date = $("#find__begin_date");
    var end_date = $("#find__end_date");

    getClickData(begin_date.val(), end_date.val());
    getShareDate(begin_date.val(), end_date.val());
}

function createZipForDate() {

    var begin_date = $("#download_begin_date");

    var end_date = $("#download_end_date");

    if (begin_date.val() == null && end_date.val() == null) {
        alert("开始时间/结束时间必填一个");
        return false;
    }

    var category_id = $("#category_id").val();
    var user_id = $("#user_id").val();

    if (category_id == null || user_id == null) {
        alert("获取用户/套餐参数失败");
        return false;
    }

    if (!confirm("批量下载需要打包，打包时间较长，请勿刷新页面！")) {
        return false;
    }

    $.post(
        "/IN_Photo/get/createMediasForDateZip.do",
        {
            "user_id": user_id,
            "category_id": category_id,
            "begin_date": begin_date.val(),
            "end_date": getNewDay(end_date.val(), 1)
        },
        function (res) {
            if (!res.success) {
                $("#zip_create_states_message").html(res.message);
            } else {
                $("#zip_create_states_message").html("准备压缩中。。。");
                t1 = setInterval(function () {
                    checkZipCreateStates(res.message)
                }, 3000);
            }
        }
    );
}

function checkZipCreateStates(code) {
    $.post(
        "/IN_Photo/get/checkCreateZipStates.do",
        {
            "code": code,
            "date": new Date()
        },
        function (res) {
            if (!res.success) {
                $("#zip_create_states_message").html(res.message);
            } else {
                clearInterval(t1);
                $("#zip_create_states_message").html("打包完成！" +
                    "<a type=\"button\" class=\"btn btn-success\" href='" + res.message + "'>下载</a>");
            }
        }
    );
}

function checkZipCreateStatesOpen(code) {
    $.post(
        "/IN_Photo/get/checkCreateZipStates.do",
        {
            "code": code,
            "date": new Date()
        },
        function (res) {
            if (res.success) {
                clearInterval(t2);
                location.href = res.message;
            }
        }
    );
}