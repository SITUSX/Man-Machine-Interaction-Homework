$().ready(function () {
    $("#mean_start").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });
    $("#mean_final").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });

    $("#momentum_start").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });
    $("#momentum_final").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });

    $("#reverse_start").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });
    $("#reverse_final").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });

    $("#minimum_start").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });
    $("#minimum_final").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });
});

var onloading = [false, false, false, false];
const strategyType = ["mean", "momentum", "reverse", "minimum"];
const Type = ["均值回归", "动量策略", "反向策略", "最小方差"];
const BlockName = {
    Motherboard: "BLOCK_MAIN",
    Gem: "BLOCK_GEM",
    Small: "BLOCK_SMALL",
    Location: "BLOCK_LOCATION",
    Industry: "BLOCK_INDUSTRY",
    Conception: "BLOCK_CONCEPT"};

function setTable(strategy, testResult) {
    $("#" + strategy + "AR").text(testResult.annualizedReturn.toFixed(4));
    $("#" + strategy + "BAR").text(testResult.benchmarkAnnualizedReturn.toFixed(4));
    $("#" + strategy + "A").text(testResult.alpha.toFixed(4));
    $("#" + strategy + "B").text(testResult.beta.toFixed(4));
    $("#" + strategy + "S").text(testResult.sharpe.toFixed(4));
    $("#" + strategy + "WR").text(testResult.winRate.toFixed(4));
    $("#" + strategy + "MD").text(testResult.maxDrawdown.toFixed(4));
}

//show loading and hide loading
function finishLoading(name) {
    echarts.init(document.getElementById(name)).hideLoading();
}
function load(name) {
    echarts.init(document.getElementById(name)).showLoading();
}


//draw return LineChart
function paintStrategyReturn(strategyReturn, benchmarkReturn, type) {
    let date = [];
    let data = [];
    let benchDate = [];
    let benchData = [];
    for (let key in strategyReturn) {
        date.push(key.substr(0, 10).replace('-', '/').replace('-', '/'));
        data.push(strategyReturn[key])
    }
    for (let key in benchmarkReturn) {
        benchDate.push(key.substr(0, 10).replace('-', '/').replace('-', '/'));
        benchData.push(benchmarkReturn[key])
    }

    if (type === "均值回归") {
        finishLoading('meanReturn');
        let meanOption = whiteBackgroundLinePaint(date, data, benchDate, benchData);
        echarts.init(document.getElementById('meanReturn')).setOption(meanOption);
    }

    else if (type ==="动量策略") {
        finishLoading('momReturn');
        let momOption = blackBackgroundLinePaint(date, data, benchDate, benchData);
        echarts.init(document.getElementById('momReturn')).setOption(momOption);
    }

    else if (type ==="反向策略") {
        finishLoading('revReturn');
        let revOption = whiteBackgroundLinePaint(date, data, benchDate, benchData);
        echarts.init(document.getElementById('revReturn')).setOption(revOption);
    }

    else if (type ==="最小方差") {
        finishLoading('minReturn');
        let minOption = blackBackgroundLinePaint(date, data, benchDate, benchData);
        echarts.init(document.getElementById('minReturn')).setOption(minOption);
    }
}

function whiteBackgroundLinePaint(date, data, benchDate, benchData) {
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
            data: ["基准", "策略"]
        },
        xAxis: [
            {
                type: 'category',
                data: benchDate
            },
            {
                type: 'category',
                data: date
            }
        ],
        yAxis: {},
        animation: true,
        series: [
            {
                name: "基准",
                type: 'line',
                smooth: true,
                data: benchData
            },
            {
                name: "策略",
                type: 'line',
                smooth: true,
                data: data
            }]
    };
    return option;
}
function blackBackgroundLinePaint(date, data, benchDate, benchData) {
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
            inactiveColor: '#777',
            textStyle: {
                color: '#fff'
            },
            data: ["基准", "策略"]
        },
        xAxis: [
            {
                type: 'category',
                data: benchDate,
                axisLine: { lineStyle: { color: '#8392A5' } }
            },
            {
                type: 'category',
                data: date,
                axisLine: { lineStyle: { color: '#8392A5' } }
            }
        ],
        yAxis: {
            axisLine: { lineStyle: { color: '#8392A5' } }
        },
        animation: true,
        series: [
            {
                name: "基准",
                type: 'line',
                smooth: true,
                data: benchData
            },
            {
                itemStyle:{
                    normal :{
                        lineStyle:{
                            color : '#8cd3ec'
                        }
                    }
                },
                name: "策略",
                type: 'line',
                smooth: true,
                data: data
            }]
    };
    return option;
}

//draw return BarChart
function renderBrushed(params) {
    var brushed = [];
    var brushComponent = params.batch[0];

    for (var sIdx = 0; sIdx < brushComponent.selected.length; sIdx++) {
        var rawIndices = brushComponent.selected[sIdx].dataIndex;
        brushed.push('[Series ' + sIdx + '] ' + rawIndices.join(', '));
    }
}
function paintStrategyReturnBar(positiveReturnDistribute, negativeReturnDistrubute, type) {
    let positiveDays = [];
    let positiveData = [];
    let negativeDays = [];
    let negativeData = [];

    for (let key in positiveReturnDistribute) {
        positiveData.push(key);
        positiveDays.push(positiveReturnDistribute[key]);
    }
    for (let key in negativeReturnDistrubute) {
        negativeData.push(key);
        negativeDays.push(negativeReturnDistrubute[key]);
    }

    if (type ==="均值回归") {
        finishLoading('meanBarReturn');
        let meanOption = whiteBackgroundBarPaint(positiveData, positiveDays, negativeDays);
        echarts.init(document.getElementById('meanBarReturn')).setOption(meanOption);
    }
    else if(type ==="动量策略"){
        finishLoading('momBarReturn');
        let momOption = blackBackgroundBarPaint(positiveData, positiveDays, negativeDays);
        echarts.init(document.getElementById('momBarReturn')).setOption(momOption);
    }
    else if(type ==="反向策略"){
        finishLoading('revBarReturn');
        let revOption = whiteBackgroundBarPaint(positiveData, positiveDays, negativeDays);
        echarts.init(document.getElementById('revBarReturn')).setOption(revOption);
    }
    else if(type === "最小方差"){
        finishLoading('minBarReturn');
        let minOption = blackBackgroundBarPaint(positiveData, positiveDays, negativeDays);
        echarts.init(document.getElementById('minBarReturn')).setOption(minOption);
    }
}

function whiteBackgroundBarPaint(positiveData, positiveDays, negativeDays) {
    let option = {
        tooltip : {
            trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['Positive Revenue Times', 'Negative Revenue Times']
        },
        grid: {
            left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
        },
        xAxis : [
            {
                type : 'value'
            }
        ],
            yAxis : [
        {
            type : 'category',
            axisTick : {show: false},
            data : positiveData
        }
    ],
        series : [
        {
            name:'Positive Revenue Times',
            type:'bar',
            data:positiveDays
        },
        {
            name:'Negative Revenue Times',
            type:'bar',
            stack: '总量',
            data:negativeDays
        }
    ]
    };
    return option;
}
function blackBackgroundBarPaint(positiveData, positiveDays, negativeDays) {
    let option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            inactiveColor: '#777',
            textStyle: {
                color: '#fff'
            },
            data:['Positive Revenue Times', 'Negative Revenue Times']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                axisLine: { lineStyle: { color: '#8392A5' } },
                type : 'value'
            }
        ],
        yAxis : [
            {
                axisLine: { lineStyle: { color: '#8392A5' } },
                type : 'category',
                axisTick : {show: false},
                data : positiveData
            }
        ],
        series : [
            {
                name:'Positive Revenue Times',
                type:'bar',
                data: positiveDays
            },
            {
                itemStyle:{
                    normal :{
                        color : '#8cd3ec'
                    }
                },
                name:'Negative Revenue Times',
                type:'bar',
                stack: '总量',
                data: negativeDays
            }
        ]
    };
    return option;
}

//analyse stock data
function strategy(index, stock_pool, forming_period, holding_period, begin, end) {
    let codePool = null;
    let blockName = null;

    let type = Type[index];

    if (type === "均值回归") {
        load('meanReturn');
        load('meanBarReturn');
    }
    else if(type === "动量策略"){
        load('momReturn');
        load('momBarReturn');
    }
    else if(type === "反向策略"){
        load('revReturn');
        load('revBarReturn');
    }
    else if(type === "最小方差"){
        load('minReturn');
        load('minBarReturn');
    }

    switch (stock_pool){
        case "all":
            $.ajax({
                url: "/strategy/allCodePool.form",
                data:{
                    type: type,
                    begin: begin,
                    end: end,
                    forming_period: forming_period,
                    holding_period: holding_period
                },
                dataType:"json",
                success: function (testResult) {
                    setTable(strategyType[index], testResult);
                    onloading[index] = false;

                    let strategyReturn = testResult.strategyReturn;
                    let benchmarkReturn = testResult.benchmarkReturn;
                    let positiveReturnDistribute = testResult.positiveReturnDistribute;
                    let negativeReturnDistrubute = testResult.negativeReturnDistrubute;

                    paintStrategyReturn(strategyReturn, benchmarkReturn,type);
                    paintStrategyReturnBar(positiveReturnDistribute, negativeReturnDistrubute, type);

                },
                error: function () {
                    swal({
                        title: "网络错误",
                        text: "请重试!",
                        type: "error"
                    });
                }
            });
            return;
        case "plate":
            blockName = $("#" + strategyType[index] + "_plate option:selected").val();
            $.ajax({
                url: "/strategy/block.form",
                data:{
                    type: type,
                    blockName: BlockName[blockName],
                    begin: begin,
                    end: end,
                    forming_period: forming_period,
                    holding_period: holding_period
                },
                dataType:"json",
                success: function (testResult) {
                    setTable(strategyType[index], testResult);
                    onloading[index] = false;

                    let strategyReturn = testResult.strategyReturn;
                    let benchmarkReturn = testResult.benchmarkReturn;
                    let positiveReturnDistribute = testResult.positiveReturnDistribute;
                    let negativeReturnDistrubute = testResult.negativeReturnDistrubute;

                    paintStrategyReturn(strategyReturn, benchmarkReturn,type);
                    paintStrategyReturnBar(positiveReturnDistribute, negativeReturnDistrubute, type);

                },
                error: function () {
                    swal({
                        title: "网络错误",
                        text: "请重试!",
                        type: "error"
                    });
                }
            });
            return;
        case "optional":
            let minVolume = $("#" + strategyType[index] + "_min_volume").val();
            let minStockRange = $("#" + strategyType[index] +"_min_range").val();
            let section1 = document.getElementById(strategyType[index]+"_zero_price").checked;
            let section2 = document.getElementById(strategyType[index]+"_twenty_price").checked;
            let section3 = document.getElementById(strategyType[index]+"_fourty_price").checked;
            let section4 = document.getElementById(strategyType[index]+"_sixty_price").checked;
            let section5 = document.getElementById(strategyType[index]+"_eighty_price").checked;

            $.ajax({
                url: "/strategy/optionalCodePool.form",
                data:{
                    type: type,
                    minVolume: minVolume,
                    minStockRange: minStockRange,
                    section1: section1,
                    section2: section2,
                    section3: section3,
                    section4: section4,
                    section5: section5,
                    begin: begin,
                    end: end,
                    forming_period: forming_period,
                    holding_period: holding_period
                },
                dataType:"json",
                success:function (testResult) {
                    setTable(strategyType[index], testResult);
                    onloading[index] = false;

                    $("#" + strategyType[index] + "_stock_num").text(testResult.stockNumber);
                    let strategyReturn = testResult.strategyReturn;
                    let benchmarkReturn = testResult.benchmarkReturn;
                    let positiveReturnDistribute = testResult.positiveReturnDistribute;
                    let negativeReturnDistrubute = testResult.negativeReturnDistrubute;

                    paintStrategyReturn(strategyReturn, benchmarkReturn,type);
                    paintStrategyReturnBar(positiveReturnDistribute, negativeReturnDistrubute, type);
                },
                error: function () {
                    swal({
                        title: "网络错误",
                        text: "请重试!",
                        type: "error"
                    });
                    onloading[index] = false;
                }
            });
            return;
    }
}

function mean() {
    let stock_pool = $("input[name='mean_stock_pool']:checked").val();
    let forming_period = $("input[name='forming_period']:checked").val();
    let holding_period = $("#mean_holding").val();
    let begin = formatDate($("#mean_start").val());
    let end = formatDate($("#mean_final").val());

    strategy(0, stock_pool, forming_period, holding_period, begin, end);
}

function momentum() {
    let stock_pool = $("input[name='momentum_stock_pool']:checked").val();
    forming_range(1, stock_pool);
}

function reverse() {
    let stock_pool = $("input[name='reverse_stock_pool']:checked").val();
    forming_range(2, stock_pool);
}

function minimum() {
    let stock_pool = $("input[name='minimum_stock_pool']:checked").val();
    forming_range(3, stock_pool);
}

function forming_range(index, stock_pool) {
    let strategy_type = strategyType[index];

    let forming_period = $("#" + strategy_type + "_forming").val();
    let holding_period = $("#" + strategy_type + "_holding").val();
    let begin = formatDate($("#" + strategy_type + "_start").val());
    let end = formatDate($("#" + strategy_type + "_final").val());

    strategy(index, stock_pool, forming_period, holding_period, begin, end);
}

//处理Date
function formatDate(date) {
    for (let x = 0; x < date.length; x++) {
        if (date[x] === "/") {
            date[x] = "-";
        }
    }
    return date;
}

$().ready(function () {
    //均值回归
    $("#mean_check").click(function () {
        if(onloading[0]){
            swal({
                title: "加载中...",
                text: "Mean当前数据未加载完!",
                type: "warning"
            });
            return;
        }
        onloading[0] = true;

        mean();
    });

    //动量策略
    $("#momentum_check").click(function () {
        if(onloading[1]){
            swal({
                title: "加载中...",
                text: "Momentum当前数据未加载完!",
                type: "warning"
            });return;
        }
        onloading[1] = true;

        momentum();
    });

    //
    $("#reverse_check").click(function () {
        if(onloading[2]){
            swal({
                title: "加载中...",
                text: "Reverse当前数据未加载完!",
                type: "warning"
            });
            return;
        }
        onloading[2] = true;

        reverse();
    });

    //
    $("#minimum_check").click(function () {
        if(onloading[3]){
            swal({
                title: "加载中...",
                text: "Minimum当前数据未加载完!",
                type: "warning"
            });
            return;
        }
        onloading[3] = true;

        minimum();
    });

});