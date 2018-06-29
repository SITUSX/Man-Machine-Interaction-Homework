$().ready(function () {
    $("#begin").datetimepicker({
        minView: 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language: 'zh-CN', // 语言
        autoclose: true, //  true:选择时间后窗口自动关闭
        format: 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn: true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate: new Date(),  // 窗口可选时间从今天开始
        endDate: new Date()   // 窗口最大时间直至今天
    });
    $("#end").datetimepicker({
        minView: 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language: 'zh-CN', // 语言
        autoclose: true, //  true:选择时间后窗口自动关闭
        format: 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn: true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate: new Date(),  // 窗口可选时间从今天开始
        endDate: new Date()   // 窗口最大时间直至今天
    })
});

function judgeCollect(code) {
    if (checkCookie() == "true") {
        let username = getCookie('username');
        $.ajax({
            url: "/user/isCollected.form",
            data: {
                username: username,
                code: code
            },
            dataType: "json",
            success: function (result) {
                if (result)
                    document.getElementById("xing").checked = true;
                else
                    document.getElementById("xing").checked = false;
            },
            error: function () {
                swal({
                    title: "Oops...",
                    text: "Network Error!",
                    type: "error"
                });
            }
        });
    }
}

function search() {
    var stock = $("#stock").val();
    var stock_code = stock.substr(0, 6);
    var stock_name = stock.substr(6, stock.length);
    $.ajax({
        url: "/stock/getNewestInfo.form",
        data: {
            code: stock_code,
        },
        dataType: "json",
        success: function (stockNewestInfoVO) {
            $("#stock_name").text(stock_name);
            $("#stock_code").text(stock_code);
            $("#StockCode").text(stockNewestInfoVO.stock_code);
            $("#OpeningPrice").text(stockNewestInfoVO.open);
            $("#trade").text(stockNewestInfoVO.trade);
            $("#HighestPrice").text(stockNewestInfoVO.high);
            $("#LowestPrice").text(stockNewestInfoVO.low);
            $("#InDecrease").text(stockNewestInfoVO.stock_range);
            $("#Volume").text(stockNewestInfoVO.volume);
            judgeCollect(stock_code);
        },
        error: function () {
            swal({
                title: "Oops...",
                text: "This stock has no data!",
                type: "error"
            });
        }
    });
    draw();
}

function handlePaint(rawData) {
    //draw KLine
    // echarts.init(document.getElementById("KLine")).setOption(option);
    function calculateMA(dayCount, data) {
        var result = [];
        for (var i = 0, len = data.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += data[i - j][1];
            }
            result.push(sum / dayCount);
        }
        return result;
    }

    //date format
    var dates = rawData.map(function (item) {
        var oDate = new Date(item[0]),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay = oDate.getDate(),
            oTime = oYear + '-' + oMonth + '-' + oDay;
        return oTime;
    });

    var data = rawData.map(function (item) {
        return [+item[1], +item[2], +item[3], +item[4]];
    });


    var option = {
        legend: {
            data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30'],
            inactiveColor: '#777',
            textStyle: {
                color: '#000'
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false,
                type: 'cross',
                lineStyle: {
                    color: '#376df4',
                    width: 2,
                    opacity: 1
                }
            }
        },
        xAxis: {
            type: 'category',
            data: dates,
            axisLine: {lineStyle: {color: '#8392A5'}}
        },
        yAxis: {
            scale: true,
            axisLine: {lineStyle: {color: '#8392A5'}},
            splitLine: {show: false}
        },
        grid: {
            bottom: 80
        },
        dataZoom: [{
            textStyle: {
                color: '#8392A5'
            },
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            dataBackground: {
                areaStyle: {
                    color: '#8392A5'
                },
                lineStyle: {
                    opacity: 0.8,
                    color: '#8392A5'
                }
            },
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }, {
            type: 'inside'
        }],
        animation: true,
        series: [
            {
                type: 'candlestick',
                name: '日K',
                data: data,
                itemStyle: {
                    normal: {
                        color: '#FD1050',
                        color0: '#0CF49B',
                        borderColor: '#FD1050',
                        borderColor0: '#0CF49B'
                    }
                }
            },
            {
                name: 'MA5',
                type: 'line',
                data: calculateMA(5, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            },
            {
                name: 'MA10',
                type: 'line',
                data: calculateMA(10, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: calculateMA(20, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            },
            {
                name: 'MA30',
                type: 'line',
                data: calculateMA(30, data),
                smooth: true,
                showSymbol: false,
                lineStyle: {
                    normal: {
                        width: 1
                    }
                }
            }
        ]
    };

    echarts.init(document.getElementById("KLine")).setOption(option);
}

function draw() {
    //getStockDetails
    let x;
    var code = $("#stock").val().substr(0, 6);
    var begin = $("#begin").val();
    for (x = 0; x < begin.length; x++) {
        if (begin[x] == "/") {
            begin[x] = "-";
        }
    }

    var end = $("#end").val();
    for (x = 0; x < end.length; x++) {
        if (end[x] == "/") {
            end[x] = "-";
        }
    }

    /*
     Start request
     todo : start loading animation
     */
    $.ajax({
        async: false,
        url: "/stock/getStockDetails.form",
        data: {
            code: code,
            begin: begin,
            end: end
        },
        dataType: "json",
        success: function (stockDetails) {
            var rawData = stockDetails.bean_list;
            rawData = rawData.map(function (each) {
                return [each['date'], each['open'], each['close'], each['low'], each['high']]
            });
            // console.log(rawData);
            /*
             todo : remove the loading animation and show main view
             */
            handlePaint(rawData)
        },
        error: function (error) {
            alert(error);
            /*
             * Self-defined Dialog
             */
        }
    })
}

$().ready(function () {
    $("#paint_draw").click(draw);
    $("#search").click(search);

    $("#xing").click(function () {
        collect();
    });
    if (checkCookie() == "true") {
        let userName = getCookie("username");
        showWelcome(userName);
    } else {
        login();
    }
    $("#logout").click(function () {
        swal({
                title: "Are you sure to Logout?",
                text: "You have to Login again!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, Logout!",
                closeOnConfirm: true
            },
            function () {
                login();
                hideCookie();
            });
    })
});

// div 切换
function login() {
    let hideWelcome = document.getElementById("welcome");
    hideWelcome.style.display = 'none';
    let showLog = document.getElementById("log");
    showLog.style.display = 'inline-block';
}

function showWelcome(userName) {
    let hideLog = document.getElementById("log");
    hideLog.style.display = 'none';
    let showWelcome = document.getElementById("welcome");
    showWelcome.style.display = 'inline-block';

    $("#user").text(userName);
}

function collect() {
    if (checkCookie() == "false") {
        swal({
            title: "Guest",
            text: "Please Login!",
            type: "error"
        });
        return;
    } else {
        let username = getCookie('username');
        let code = $("#stock_code").text();
        if (document.getElementById("xing").checked) {
            $.ajax({
                url: "/user/addCollect.form",
                data: {
                    username: username,
                    code: code
                },
                dataType: "json",
                success: function (result) {
                    if (result)
                        swal({
                            title: "Add",
                            text: "Success!",
                            type: "success"
                        });
                    else
                        swal({
                            title: "Add",
                            text: "Fail!",
                            type: "error"
                        });
                },
                error: function () {
                    swal({
                        title: "Oops...",
                        text: "Network Error!",
                        type: "error"
                    });
                }
            });
        } else {
            swal({
                    title: "Are you sure?",
                    text: "Delete this stock from your collected stocks!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Yes, delete it!",
                    closeOnConfirm: false
                },
                function () {
                    $.ajax({
                        url: "/user/delCollect.form",
                        data: {
                            username: username,
                            code: code
                        },
                        dataType: "json",
                        success: function (result) {
                            if (result)
                                swal({
                                    title: "Delete",
                                    text: "Success!",
                                    type: "success"
                                });
                            else
                                swal({
                                    title: "Delete",
                                    text: "Fail!",
                                    type: "error"
                                });
                        }
                    });
                });

        }
    }
}

//getStockList and choose stock
function pre_load() {
    $.ajax({
        async: false,
        url: "/stock/getStockList.form",
        data: {
            keyword: ""
        },
        dataType: "json",
        success: function (stockList) {
            if (stockList.length > 0) {
                stockList = stockList.map((each) => each['code'] + ' ' + each['name']);
                ReactDOM.render(<SearchList in_id="stock" init_key="000001 平安银行"
                                            s_list={stockList.slice()}/>, document.getElementById('list_root_one'));
            }
        }
    })
}