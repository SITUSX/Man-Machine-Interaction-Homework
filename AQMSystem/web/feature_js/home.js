$().ready(function () {
    showLogin();
    let username = "";
    if (checkCookie() == 'true') {
        username = getCookie("username");
        let password = getCookie("password");
        login(username, password, function () {
            showWelcome(username);
        });
    }

    let index;
    $("#sh").click(function () {
        analyseMarket("SSE");
        index = "SSE";
    });
    $("#sz").click(function () {
        analyseMarket("SZ");
        index = "SZ";
    });
    $("#hs300").click(function () {
        analyseMarket("CSI_300");
        index = "CSI_300";
    });
    $("#zxb").click(function () {
        analyseMarket("SME");
        index = "SME";
    });
    $("#cyb").click(function () {
        analyseMarket("GEI");
        index = "GEI";
    });

    $("#draw").click(function () {
        draw(index);
    });
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
                showLogin();
                hideCookie();
                console.log(checkCookie())
            }
        );
    });

});

// div 切换
function showLogin() {
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

function draw(index) {
    if (index == "SSE") {
        analyseMarket("SSE");
    }
    else if (index == "SZ") {
        analyseMarket("SZ");
    }
    else if (index == "CSI_300") {
        analyseMarket("CSI_300");
    }
    else if (index == "SME") {
        analyseMarket("SME");
    }
    else {
        analyseMarket("GEI");
    }

}

function splitData(rawData) {
    var categoryData = [];
    var values = [];
    var volumns = [];
    for (var i = 0; i < rawData.length; i++) {
        categoryData.push(rawData[i].splice(0, 1)[0]);
        values.push(rawData[i]);
        volumns.push(rawData[i][4]);
    }
    return {
        categoryData: categoryData,
        values: values,
        volumns: volumns
    };
}

function calculateMA(dayCount, values) {

    var result = [];
    for (var i = 0, len = values.length; i < len; i++) {
        if (i < dayCount) {
            result.push('-');
            continue;
        }
        var sum = 0;
        for (var j = 0; j < dayCount; j++) {
            sum += values[i - j][1];
        }
        result.push(+(sum / dayCount).toFixed(3));
    }
    return result;
}

// market Kline
function analyseMarket(indexName) {
    let begin = $("#start").val();
    for (let x = 0; x < begin.length; x++) {
        if (begin[x] === "/") {
            begin[x] = "-";
        }
    }
    let end = $("#final").val();
    for (let x = 0; x < end.length; x++) {
        if (end[x] === "/") {
            end[x] = "-";
        }
    }

    if (indexName === "SSE") {
        $("#name").text("上证指数");
    }
    else if (indexName === "SZ") {
        $("#name").text("深圳成指");
    }
    else if (indexName === "CSI_300") {
        $("#name").text("沪深300");
    }
    else if (indexName === "SME") {
        $("#name").text("中小板指");
    }
    else {
        $("#name").text("创业板指");
    }


    $.ajax({
        url: "/block/getPlateIndex.form",
        data: {
            indexName: indexName,
            begin: begin,
            end: end
        },
        dataType: "json",
        success: function (plateIndex) {
            let rawData = plateIndex;
            rawData = rawData.map(function (each) {
                return [each['date'], each['open'], each['close'], each['low'], each['high'], each['volume']]
            });
            paintMarketKLine(rawData);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    })
}

function paintMarketKLine(rawData) {

    let myChart = echarts.init(document.getElementById("market"));

    var dates = rawData.map(function (item) {
        var oDate = new Date(item[0]),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay = oDate.getDate(),
            oTime = oYear + '-' + oMonth + '-' + oDay;
        return oTime;
    });

    var values = rawData.map(function (item) {
        return [+item[1], +item[2], +item[3], +item[4]];
    });

    var volumes = rawData.map(function (item) {
        return item[5];
    });

    myChart.setOption(option = {
        backgroundColor: '#eee',
        animation: false,
        legend: {
            bottom: 10,
            left: 'center',
            data: ['Dow-Jones index', 'MA5', 'MA10', 'MA20', 'MA30']
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            },
            backgroundColor: 'rgba(245, 245, 245, 0.8)',
            borderWidth: 1,
            borderColor: '#ccc',
            padding: 10,
            textStyle: {
                color: '#000'
            },
            position: function (pos, params, el, elRect, size) {
                var obj = {top: 10};
                obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
                return obj;
            },
            extraCssText: 'width: 170px'
        },
        axisPointer: {
            link: {xAxisIndex: 'all'},
            label: {
                backgroundColor: '#777'
            }
        },
        toolbox: {
            feature: {
                dataZoom: {
                    yAxisIndex: false
                },
                brush: {
                    type: ['lineX', 'clear']
                }
            }
        },
        brush: {
            xAxisIndex: 'all',
            brushLink: 'all',
            outOfBrush: {
                colorAlpha: 0.1
            }
        },
        grid: [
            {
                left: '10%',
                right: '8%',
                height: '50%'
            },
            {
                left: '10%',
                right: '8%',
                top: '63%',
                height: '16%'
            }
        ],
        xAxis: [
            {
                type: 'category',
                data: dates,
                scale: true,
                boundaryGap: false,
                axisLine: {onZero: false},
                splitLine: {show: false},
                splitNumber: 20,
                min: 'dataMin',
                max: 'dataMax',
                axisPointer: {
                    z: 100
                }
            },
            {
                type: 'category',
                gridIndex: 1,
                data: dates,
                scale: true,
                boundaryGap: false,
                axisLine: {onZero: false},
                axisTick: {show: false},
                splitLine: {show: false},
                axisLabel: {show: false},
                splitNumber: 20,
                min: 'dataMin',
                max: 'dataMax',
                axisPointer: {
                    label: {
                        formatter: function (params) {
                            var seriesValue = (params.seriesData[0] || {}).value;
                            return params.value
                                + (seriesValue != null
                                        ? '\n' + echarts.format.addCommas(seriesValue)
                                        : ''
                                );
                        }
                    }
                }
            }
        ],
        yAxis: [
            {
                scale: true,
                splitArea: {
                    show: true
                }
            },
            {
                scale: true,
                gridIndex: 1,
                splitNumber: 2,
                axisLabel: {show: false},
                axisLine: {show: false},
                axisTick: {show: false},
                splitLine: {show: false}
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                xAxisIndex: [0, 1],
                start: 0,
                end: 100
            },
            {
                show: true,
                xAxisIndex: [0, 1],
                type: 'slider',
                top: '85%',
                start: 0,
                end: 100
            }
        ],
        series: [
            {
                name: 'Dow-Jones index',
                type: 'candlestick',
                data: values,
                itemStyle: {
                    normal: {
                        borderColor: null,
                        borderColor0: null
                    }
                },
                tooltip: {
                    formatter: function (param) {
                        param = param[0];
                        return [
                            'Date: ' + param.name + '<hr size=1 style="margin: 3px 0">',
                            'Open: ' + param.data[0] + '<br/>',
                            'Close: ' + param.data[1] + '<br/>',
                            'Lowest: ' + param.data[2] + '<br/>',
                            'Highest: ' + param.data[3] + '<br/>'
                        ].join('');
                    }
                }
            },
            {
                name: 'MA5',
                type: 'line',
                data: calculateMA(5, values),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA10',
                type: 'line',
                data: calculateMA(10, values),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: calculateMA(20, values),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA30',
                type: 'line',
                data: calculateMA(30, values),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'Volume',
                type: 'bar',
                xAxisIndex: 1,
                yAxisIndex: 1,
                data: volumes
            }
        ]
    }, true);

}

//pie chart : area and industry stock
function pieChartPaint() {

    let industryChart = echarts.init(document.getElementById("industryChart"));
    let areaChart = echarts.init(document.getElementById("areaChart"));
    let industry;
    let location;
    $.ajax({
        async: false,
        url: "/block/getBlockNum.form",
        dataType: "json",
        data: {},
        success: function (blockNum) {
            industry = blockNum.industry;
            location = blockNum.location;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    });

    let industryName = [];
    let industryNum = [];
    let locationName = [];
    let locationNum = [];

    for (let i in industry) {
        industryName.push(i);
        industryNum.push(industry[i])
    }

    for (let i in location) {
        locationName.push(i);
        locationNum.push(location[i])
    }

    let industryData = [];
    for (let i = 0; i < industryName.length; i++) {
        industryData.push({
            value: industryNum[i],
            name: industryName[i]
        })
    }
    let industryOption = {
        title: {
            text: ' Industry Plate Distribution'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x: 'center',
            y: 'bottom',
            data: industryName,
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: '行业分布',
                type: 'pie',
                radius: [90, 170],
                data: industryData
            }
        ]
    };
    industryChart.setOption(industryOption);

    let seriesData = [];
    for (let i = 0; i < locationName.length; i++) {
        seriesData.push({
            value: locationNum[i],
            name: locationName[i]
        })
    }
    let locationOption = {
        title: {
            text: ' Location Plate Distribution'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            x: 'center',
            y: 'bottom',
            data: locationName,
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                magicType: {
                    show: true,
                    type: ['pie', 'funnel']
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: '地区分布',
                type: 'pie',
                radius: [90, 170],
                data: seriesData
            }
        ]
    };
    areaChart.setOption(locationOption);

}