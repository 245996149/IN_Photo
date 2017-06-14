<%--
  Created by IntelliJ IDEA.
  User: kaxia
  Date: 2017/6/14
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/Chart.js"></script>
</head>
<body>
<div class="row">
    <div class="col-md-3">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">七日点击量</h3>
            </div>
            <div class="panel-body">
                <canvas id="myChart" width="400" height="200"></canvas>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    //页面加载时调用函数**************************************************
    $(function () {
        getCharts1();
    });

    function getCharts1() {

        $.post(
            "getCharsData.do",
            function (res) {
                alert(res);
                var labelsArr = new Array(7);
                var dataArr = new Array(7);
                for (var i = 0; i < res.length; i++) {
                    labelsArr[i] = res[i].name;
                    dataArr[i] = res[i].num;
                }



                var ctx = document.getElementById("myChart").getContext('2d');
                var myChart = new Chart(ctx, {
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


</script>

</body>
</html>
