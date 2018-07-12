$().ready(function () {
    $("#start").datetimepicker({
        minView: 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language: 'zh-CN', // 语言
        autoclose: true, //  true:选择时间后窗口自动关闭
        format: 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn: true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate: new Date(),  // 窗口可选时间从今天开始
        endDate: new Date()   // 窗口最大时间直至今天
    });
    $("#final").datetimepicker({
        minView: 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language: 'zh-CN', // 语言
        autoclose: true, //  true:选择时间后窗口自动关闭
        format: 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn: true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate: new Date(),  // 窗口可选时间从今天开始
        endDate: new Date()   // 窗口最大时间直至今天
    })
})

//show two stocks details
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
                stockList = stockList.map((each)=>each['code'] + ' ' + each['name']);
                ReactDOM.render(<SearchList in_id="first_stock_name" init_key="000001 平安银行"
                                            s_list={stockList.slice()}/>, document.getElementById('list_root_one'));
                ReactDOM.render(<SearchList in_id="second_stock_name" init_key="000002 万 科A"
                                            s_list={stockList.slice()}/>, document.getElementById('list_root_two'));
            }

            // for (var i = 0; i < stockList.length; i++) {
            //     var stock = stockList[i];
            //     $("#flist").append('<option value="' + stock.code + " " + stock.name + '"/>');
            //     $("#slist").append('<option value="' + stock.code + " " + stock.name + '"/>');
            // }
        }
    })
}

function showResult() {
    let result = document.getElementById("compare_result");
    result.style.display = 'block';
    let result_graph = document.getElementById("compare_graph");
    result_graph.style.display = 'block';
}

function radarChartPaint(fMinPrice, fMaxPrice, fChange, fVariance, fname, sMinPrice, sMaxPrice, sChange, sVariance, sname) {
    var radarChart = echarts.init(document.getElementById('proportion'));

    var option = {
        title: {},
        tooltip: {},
        legend: {
            data: [fname, sname]
        },
        radar: {
            // shape: 'circle',
            indicator: [
                {name: 'MIN PRICE', max: 50},
                {name: 'MAX PRICE', max: 50},
                {name: 'CHANGE', max: 0.1},
                {name: 'VARIANCE of LOG RETURN', max: 0.00025}
            ]
        },
        series: [{
            type: 'radar',
            // areaStyle: {normal: {}},
            data: [
                {
                    value: [fMinPrice, fMaxPrice, fChange, fVariance],
                    name: fname
                },
                {
                    value: [sMinPrice, sMaxPrice, sChange, sVariance],
                    name: sname
                }
            ]
        }]
    };

    radarChart.setOption(option);
}

function graph_painter(fname, sname, firstCloseList, secondCloseList, firstLogList, secondLogList) {
    //draw close graph
    let closeChart = echarts.init(document.getElementById('close'));

    let fdates = [];
    let fdata = [];
    let sdates = [];
    let sdata = [];

    // firstCloseList.map(function (each) {
    //     fdates.push(each[0]);
    //     fdata.push(each[1]);
    //     return each;
    // });
    // secondCloseList.map(function (each) {
    //     sdates.push(each[0]);
    //     sdata.push(each[1]);
    //     return each;
    // });
    // for(var item = 0; item < firstCloseList.length; item++) {
    //     let each = firstCloseList[item];
    // }


    for (var key in firstCloseList) {
        fdates.push(key.substr(0, 10).replace('-', '/').replace('-', '/'));
        fdata.push(firstCloseList[key])
    }


    for (key in secondCloseList) {
        sdates.push(key.substr(0, 10).replace('-', '/').replace('-', '/'));
        sdata.push(secondCloseList[key])
    }


    let option = {
        title: {
            text: ""
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
        legend: {
            data: [fname, sname]
        },
        xAxis: [
            {
                type: 'category',
                data: fdates
            },
            {
                type: 'category',
                data: sdates
            }
        ],
        yAxis: {},
        animation: true,
        series: [
            {
                name: fname,
                type: 'line',
                smooth: true,
                data: fdata
            },
            {
                name: sname,
                type: 'line',
                smooth: true,
                data: sdata
            }]
    };
    closeChart.setOption(option);


    //draw log return value graph

    let logChart = echarts.init(document.getElementById('log'));

    let fLogdates = [];
    let fLogdata = [];
    let sLogdates = [];
    let sLogdata = [];

    for (key in firstLogList) {
        fLogdates.push(key.substr(0, 10).replace('-', '/').replace('-', '/'));
        fLogdata.push(firstLogList[key]);
    }


    for (key in secondLogList) {
        sLogdates.push(key.substr(0, 10).replace('-', '/').replace('-', '/'));
        sLogdata.push(secondLogList[key]);
    }

    console.log(fLogdates);


    let log_option = {
        title: {
            text: ""
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
        legend: {
            data: [fname, sname]
        },
        xAxis: [
            {
                type: 'category',
                data: fLogdates
            },
            {
                type: 'category',
                data: sLogdates
            }
        ],
        animation: true,
        yAxis: {},
        series: [
            {
                name: fname,
                type: 'line',
                smooth: true,
                data: fLogdata
            },
            {
                name: sname,
                type: 'line',
                smooth: true,
                data: sLogdata
            }]
    };
    logChart.setOption(log_option);
}

function compare() {
    let x;
    let fstock = $("#first_stock_name").val();
    let fcode = fstock.substr(0, 6);
    let fname = fstock.substr(7, fstock.length);

    let sstock = $("#second_stock_name").val();
    let scode = sstock.substr(0, 6);
    let sname = sstock.substr(7, sstock.length);


    let start = $("#start").val();
    for (x = 0; x < start.length; x++) {
        if (start[x] == "/") {
            start[x] = "-";
        }
    }
    let final = $("#final").val();
    for (x = 0; x < final.length; x++) {
        if (final[x] == "/") {
            final[x] = "-";
        }
    }
    let startArray = start.split("-");
    let finalArray = final.split("-");
    if(parseInt(startArray[0]) > parseInt(finalArray[0])
    ||(parseInt(startArray[0]) === parseInt(finalArray[0]) && parseInt(startArray[1]) > parseInt(finalArray[1]))
    ||(parseInt(startArray[0]) === parseInt(finalArray[0]) && parseInt(startArray[1]) === parseInt(finalArray[1]) && parseInt(startArray[2]) > parseInt(finalArray[2])))
        swal({
            title: "日期错误!",
            text: "开始日期必须早于结束日期!",
            type: "error"
        });


    $("#fname").text(fname);
    $("#sname").text(sname);
    $("#first_stock").text(fname);
    $("#second_stock").text(sname);

    let firstCloseList;
    let secondCloseList;
    let firstLogList;
    let secondLogList;

    let fMinPrice;
    let fMaxPrice;
    let fChange;
    let fVariance;
    let sMinPrice;
    let sMaxPrice;
    let sChange;
    let sVariance;
    $.ajax({
        async: false,
        url: "/stock/getStockDetails.form",
        data: {
            code: fcode,
            begin: start,
            end: final
        },
        dataType: "json",
        success: function (stockDetails) {
            fMinPrice = stockDetails.minPrice;
            $("#fmin").text(fMinPrice);
            fMaxPrice = stockDetails.maxPrice;
            $("#fmax").text(fMaxPrice);
            fChange = stockDetails.change;
            $("#fchange").text(fChange.toFixed(4));
            fVariance = stockDetails.varianceOfLogReturn;
            $("#flog").text(fVariance.toFixed(7));

            firstCloseList = stockDetails.close;
            firstLogList = stockDetails.logReturn;
        },
        error: function () {
            swal({
                title: "警告",
                text: "这只股票 -- " + fname + "没有数据!",
                type: "warning"
            });
        }
    });
    $.ajax({
        async: false,
        url: "/stock/getStockDetails.form",
        data: {
            code: scode,
            begin: start,
            end: final
        },
        dataType: "json",
        success: function (stockDetails) {
            sMinPrice = stockDetails.minPrice;
            $("#smin").text(sMinPrice);
            sMaxPrice = stockDetails.maxPrice;
            $("#smax").text(sMaxPrice);
            sChange = stockDetails.change;
            $("#schange").text(sChange.toFixed(4));
            sVariance = stockDetails.varianceOfLogReturn;
            $("#slog").text(sVariance.toFixed(7));

            secondCloseList = stockDetails.close;
            secondLogList = stockDetails.logReturn;
        },
        error: function () {
            swal({
                title: "警告",
                text: "这只股票 -- " + sname + " 没有数据!",
                type: "warning"
            });
        }
    });

    graph_painter(fname, sname, firstCloseList, secondCloseList, firstLogList, secondLogList);
    radarChartPaint(fMinPrice, fMaxPrice, fChange, fVariance, fname, sMinPrice, sMaxPrice, sChange, sVariance, sname);

}

$().ready(function () {
    $("#compare").click(compare);
});




