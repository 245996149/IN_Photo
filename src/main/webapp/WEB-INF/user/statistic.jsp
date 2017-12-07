<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>INPHOTO管理系统</title>
    <link rel="icon" href="${pageContext.request.contextPath}/images/logo.png">

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body style="padding-top: 70px;">

<jsp:include page="/WEB-INF/user/menu.jsp"/>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">数据查询</h3>
            </div>
            <div class="panel-body">
                <div class="row" style="text-align: center;">
                    <div id="find_date_div">
                        <form class="form-inline" id="find_date_form">
                            <div class="form-group">
                                <label for="find__end_date">开始时间</label>
                                <input type="date" class="form-control" id="find__begin_date"
                                       onchange="settingFindDateInput();"/>
                            </div>
                            <div class="form-group">
                                <label for="find__end_date">结束时间</label>
                                <input type="date" class="form-control" id="find__end_date">
                            </div>
                            </br>
                            </br>
                            <label for="category_name">套餐</label>
                            <div class="input-group">
                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false">下拉选择<span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="#"
                                               onclick="selectCategory(0,'所有');">所有</a>
                                        </li>
                                        <c:forEach items="${allUserCategory}" var="auc">
                                            <c:forEach items="${category}" var="c">
                                                <c:if test="${auc.categoryId==c.categoryId}">
                                                    <li><a href="#"
                                                           onclick="selectCategory(${c.categoryId},'${c.categoryName}');">${c.categoryName}</a>
                                                    </li>
                                                    <%--<option value="${cl.categoryId}">${cl.categoryName}</option>--%>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <input type="text" id="category_name" placeholder="选择要查询的系统" readonly
                                       class="form-control" value="所有"
                                       aria-label="Text input with segmented button dropdown">
                            </div>
                            <button type="button" class="btn btn-info" onclick="a();">查询
                            </button>
                        </form>
                    </div>
                    <div id="find_error_div">检测到您的浏览器不支持H5特性，所以禁用了批量下载功能，请使用<strong>Chrome内核</strong>的浏览器
                        <br/><strong>Chrome内核的浏览器:</strong>Google Chrome、360极速浏览器、QQ浏览器、UC浏览器等
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="row">
        <div id="basic_data_div" style="height:auto;min-height: 500px;" class="col-md-12"></div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/js/html2canvas.js"></script>
<script src="${pageContext.request.contextPath}/js/user/statistic.js"></script>
<script type="text/javascript">
    function checkDatesupport() {
        var i = document.createElement('input');
        i.setAttribute('type', 'date');
        //浏览器不支持date类型
        if (i.type == 'date') {
            return true;
        }
        return false;
    }

    window.onload = function () {
        if (checkDatesupport()) {
            $("#find_date_div").show();
            $("#find_error_div").hide();
        } else {
            $("#find_date_div").hide();
            $("#find_error_div").show();
        }

        var date = new Date();
        var day = date.getDate();
        var month = date.getMonth() + 1;

        if (date.getMonth() < 9) {
            month = "0" + (date.getMonth() + 1);
        }

        if (date.getDate() < 10) {
            day = "0" + date.getDate();
        }

        var today = date.getFullYear() + "-" + month + "-" + day;
        /*加载数据表单*/
//        getBasicData(getNewDay(today, -6), today);
    };

    function a() {
        var begin_date = $("#find__begin_date");

        var end_date = $("#find__end_date");

        $.post(
            "getBasicData.do",
            {
                "begin_date": begin_date.val(),
                "end_date": getNewDay(end_date.val(), 1)
            },
            function (res) {
                alert(res);
            }
        );
    }

    function getBasicData(beginDate, endDate, category_id) {
        $.post(
            "getBasicData.do",
            {
                "category_id": category_id,
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
                basicDataChart.setOption({
//                    grid: {
//                        left: '3%',
//                        right: '4%',
//                        bottom: '3%',
//                        containLabel: true
//                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    legend: {
                        data: ['点击量', '上传量']
                    },
                    xAxis: {
                        data: labelsArr
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '点击量',
                        type: 'line',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        areaStyle: {normal: {}},
                        lineStyle: {normal: {type: "solid", smooth: true}},
                        data: click_data
                    },
                        {
                            // 根据名字对应到相应的系列
                            name: '上传量',
                            type: 'line',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            },
                            areaStyle: {normal: {}},
                            lineStyle: {normal: {type: "solid", smooth: true}},
                            data: upload_data
                        }]
                });
            })
    }

    //日期加上天数得到新的日期
    //dateTemp 需要参加计算的日期，days要添加的天数，返回新的日期，日期格式：YYYY-MM-DD
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

    // 基于准备好的dom，初始化echarts实例
    var basicDataChart = echarts.init(document.getElementById('basic_data_div'));

    //    var resizeWorldMapContainer = function () {
    //        basicDataChart.style.width = window.innerWidth + 'px';
    //        basicDataChart.style.height = window.innerHeight + 'px';
    //    };

    //    resizeWorldMapContainer();

    window.onresize = function () {
        //重置容器高宽
//        resizeWorldMapContainer();
        basicDataChart.resize();
    };

    // 指定图表的配置项和数据
    var basicDataOption = {
        title: [{
            text: '2017年11月30日-2017年12月30日\n数据报告',
            x: '50%',
            y: '0%',
            textAlign: 'center',
            textStyle: {
                color: '#ff5252'
            }
        }, {
            text: '上传量、点击量',
            left: '37.5%',
            top: '10%',
//            textAlign: 'center',
            textStyle: {
                color: '#ff5252'
            }
        }],
        grid: [{
            top: 100,
            width: '75%',
            bottom: 0,
            left: 10,
            containLabel: true
        }],
        tooltip: {
            trigger: 'axis'
        },
        legend: [{
            x: '37.5%',
            top: 70,
            align: 'right',
            data: ['销量', '啊啊啊']
        }],
        xAxis: {
            data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'line',
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            tooltip: {
                trigger: 'axis'
            },
            smooth: true,
            lineStyle: {normal: {type: "solid"}},
            data: [5, 20, 36, 10, 10, 20]
        },
            {
                name: '啊啊啊',
                type: 'line',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                smooth: true,
                lineStyle: {normal: {type: "solid"}},
                data: [15, 10, 26, 15, 10, 30]
            },
            {
                type: 'pie',
                center: ['87.5%', '33%'],
                radius: ['50%', '60%'],
                label: {
                    normal: {
                        position: 'center'
                    }
                },
                data: [{
                    value: 335,
                    name: '用户来源分析',
                    itemStyle: {
                        normal: {
                            color: '#ffd285'
                        }
                    },
                    label: {
                        normal: {
                            formatter: '{d} %',
                            textStyle: {
                                color: '#ffd285',
                                fontSize: 20

                            }
                        }
                    }
                }, {
                    value: 180,
                    name: '占位',
                    tooltip: {
                        show: false
                    },
                    itemStyle: {
                        normal: {
                            color: '#87CEFA'
                        }
                    },
                    label: {
                        normal: {
                            textStyle: {
                                color: '#ffd285'
                            },
                            formatter: '\n手机号注册'
                        }
                    }
                }]
            },
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                center: ['87.5%', '83%'],
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 274, name: '联盟广告'},
                    {value: 235, name: '视频广告'},
                    {value: 400, name: '搜索引擎'}
                ].sort(function (a, b) {
                    return a.value - b.value;
                }),
                roseType: 'radius',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },

                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                    return Math.random() * 200;
                }
            }]
    };

    // 使用刚指定的配置项和数据显示图表。
    basicDataChart.setOption(basicDataOption);
</script>
</body>
</html>