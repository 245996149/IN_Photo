/*查询7天内的点击数*/
function getClick_7() {

    var category_id = $("#category_id").val();

    $.post(
        "getShareData.do",
        {
            "category_id": category_id,
            "type": 1
        },
        function (res) {
            var labelsArr = new Array(7);
            var dataArr = new Array(7);
            for (var i = 0; i < res.length; i++) {
                labelsArr[i] = res[i].name;
                dataArr[i] = res[i].num;
            }

            var ctx = document.getElementById("click_7").getContext('2d');

            var click_7 = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labelsArr,
                    datasets: [{
                        label: '点击量',
                        data: dataArr,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255,99,132,1)',
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
function getShare_7() {

    var category_id = $("#category_id").val();

    $.post(
        "getShareData.do",
        {
            "category_id": category_id,
            "type": 2
        },
        function (res) {

            var labels_arr = new Array(7);
            var chats_data_arr = new Array(7);
            var moments_data_arr = new Array(7);
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
function getRecycleInfo() {

    var category_id = $("#category_id").val();

    $.post(
        "getRecycleInfo.do",
        {
            "category_id": category_id
        },
        function (res) {
            var data_arr = new Array(3);
            data_arr[0] = res.recycle_total;
            data_arr[1] = res.recycle_7;
            data_arr[2] = res.recycle_15;

            var ctx4 = document.getElementById("recycle_info").getContext('2d');
            var recycle_info = new Chart(ctx4, {
                type: 'polarArea',
                data: {
                    labels: ["该系统数据总数", "数据7天内过期数", "数据15天内过期数"],
                    datasets: [{
                        label: "回收站数据过期情况",
                        data: data_arr,
                        backgroundColor: ["rgb(255, 99, 132)", "rgb(75, 192, 192)", "rgb(255, 205, 86)",]
                    }]
                }
            });

        });

}

/*打开model*/
function open_modal(mediaName) {

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
function download(id) {

    window.location.href = "/IN_Photo/get/getMedia.do?id=" + id + "&type=1&download=true";

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
    location.href = "/IN_Photo/get/getMedias.do?media_id_list=" + JSON.stringify(_list);

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