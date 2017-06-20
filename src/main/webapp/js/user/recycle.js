
/*查询回收站媒体情况*/
function getRecycleInfo() {

    var category_id = $("#category_id").val();

    $.post(
        "getRecycleInfoTotal.do",
        function (res) {
            var data_arr = new Array(3);
            data_arr[0] = res.recycle_total;
            data_arr[1] = res.recycle_7;
            data_arr[2] = res.recycle_15;

            var ctx4 = document.getElementById("recycle_info").getContext('2d');
            var recycle_info = new Chart(ctx4, {
                type: 'polarArea',
                data: {
                    labels: ["数据总数", "数据7天内过期数", "数据15天内过期数"],
                    datasets: [{
                        label: "回收站数据过期情况",
                        data: data_arr,
                        backgroundColor: ["rgb(255, 99, 132)", "rgb(75, 192, 192)", "rgb(255, 205, 86)",]
                    }]
                }
            });

        });

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

