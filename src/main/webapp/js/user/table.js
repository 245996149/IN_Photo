/*查询7天内的点击数*/
function getClick_7() {

    var category_id = $("#category_id").val();

    $.post(
        "getSharData.do",
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
                        label: '# 点击量',
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
        "getSharData.do",
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
                        label: "My First Dataset",
                        data: data_arr,
                        backgroundColor: ["rgb(255, 99, 132)", "rgb(75, 192, 192)", "rgb(255, 205, 86)",]
                    }]
                }
            });

        });

}