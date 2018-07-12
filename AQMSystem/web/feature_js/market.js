const BlockName = [
    "BLOCK_MAIN",
    "BLOCK_SMALL",
    "BLOCK_GEM",
    "BLOCK_INDUSTRY",
    "BLOCK_CONCEPT",
    "BLOCK_LOCATION"];
const BlockType = ["主板", "中小板", "创业板", "行业板块", "概念板块", "地域板块"];

$().ready(function () {
    $("#date_pick").datetimepicker({
        minView : 2, //  选择时间时，最小可以选择到那层；默认是‘hour’也可用0表示
        language : 'zh-CN', // 语言
        autoclose : true, //  true:选择时间后窗口自动关闭
        format : 'yyyy-mm-dd', // 文本框时间格式，设置为0,最后时间格式为2017-03-23 17:00:00
        todayBtn : true, // 如果此值为true 或 "linked"，则在日期时间选择器组件的底部显示一个 "Today" 按钮用以选择当前日期。
        // startDate : new Date() ,  // 窗口可选时间从今天开始
        endDate : new Date()   // 窗口最大时间直至今天
    });
});

let onloading = false;

function setBlock(blockName, index, date) {
    $.ajax({
        async: false,
        url: "/stock/getMarketInfo.form",
        data:{
            date: date,
            blockName: blockName
        },
        dataType: "json",
        success: function (marketInfo) {
            $("#maxLimit" + index).text(marketInfo.maxLimit);
            $("#minLimit" + index).text(marketInfo.minLimit);
            $("#BN" + index).text(BlockType[index-1]);
            $("#DV" + index).text(marketInfo.totalVolume);
            $("#SL" + index).text(marketInfo.totalSurgedLimit);
            $("#DL" + index).text(marketInfo.totalDeclineLimit);
            $("#OI" + index).text(marketInfo.fivePercentOffsetIncrease);
            $("#OD" + index).text(marketInfo.fivePercentOffsetDecrease);
            $("#CI" + index).text(marketInfo.fivePercentChangeIncrease);
            $("#CD" + index).text(marketInfo.fivePercentChangeDecrease);
        }
    })
}

function setIncreaseTable() {
    let param = "stock_range";
    let number  = 10;
    $.ajax({
        url:"/stock/getNewestInfoListByOrder.form",
        data:{
            param: param,
            number: number
        },
        dataType:"json",
        success: function (newestStockList) {
            for (let i = 0; i < 10; i++) {
                let stockNewestInfo = newestStockList[i];
                    $("#increaseTable").append(
                        '<tr>' +
                        '<th class="text-center">' + stockNewestInfo.stock_code + '</th> ' +
                        '<th class="text-center">' + stockNewestInfo.stock_name + '</th> ' +
                        '<th class="text-center" style="-webkit-text-fill-color: red">' + "+ " + stockNewestInfo.stock_range.toFixed(2) + '</th> ' +
                        '</tr>');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    })
}

function setDecreaseTable() {
    let param = "stock_range_desc";
    let number = 10;
    $.ajax({
        url:"/stock/getNewestInfoListByOrder.form",
        data:{
            param: param,
            number:number
        },
        dataType:"json",
        success: function (newestStockList) {
            for (let i = 0; i < 10; i++) {
                let stockNewestInfo = newestStockList[i];
                $("#decreaseTable").append(
                    '<tr>' +
                    '<th class="text-center">' + stockNewestInfo.stock_code + '</th> ' +
                    '<th class="text-center">' + stockNewestInfo.stock_name + '</th> ' +
                    '<th class="text-center" style="-webkit-text-fill-color: green">' + stockNewestInfo.stock_range.toFixed(2) + '</th> ' +
                    '</tr>');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    })
}
function setVolumeTable() {
    let param = "volume";
    let number = 10;
    $.ajax({
        url:"/stock/getNewestInfoListByOrder.form",
        data:{
            param: param,
            number:number
        },
        dataType:"json",
        success: function (newestStockList) {
            for (let i = 0; i < 10; i++) {
                let stockNewestInfo = newestStockList[i];
                $("#volumeTable").append(
                    '<tr>' +
                    '<th class="text-center">' + stockNewestInfo.stock_code + '</th> ' +
                    '<th class="text-center">' + stockNewestInfo.stock_name + '</th> ' +
                    '<th class="text-center" style="-webkit-text-fill-color: red">' + stockNewestInfo.volume + '</th> ' +
                    '</tr>');
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    })
}
//Init
$().ready(function () {
    $("#search").click(function () {
        if(onloading){
            swal({
                title: "加载中...",
                text: "当前数据未加载完!",
                type: "warning"
            });
            return;
        }

        let date = $("#date_pick").val();
        for (let x = 0; x < date.length; x++) {
            if (date[x] == "/") {
                date[x] = "-";
            }
        }

        onloading = true;

        $.ajax({
            url: "/stock/getMarketInfoByDate.form",
            data:{
                date: date,
            },
            dataType: "json",
            success: function (marketInfo) {
                $("#maxLimit0").text(marketInfo.maxLimit);
                $("#minLimit0").text(marketInfo.minLimit);
                $("#DV0").text(marketInfo.totalVolume);
                $("#SL0").text(marketInfo.totalSurgedLimit);
                $("#DL0").text(marketInfo.totalDeclineLimit);
                $("#OI0").text(marketInfo.fivePercentOffsetIncrease);
                $("#OD0").text(marketInfo.fivePercentOffsetDecrease);
                $("#CI0").text(marketInfo.fivePercentChangeIncrease);
                $("#CD0").text(marketInfo.fivePercentChangeDecrease);
                onloading = false;
            },
            error: function () {
                swal({
                    title: "网络错误",
                    text: "请重试!",
                    type: "error"
                });
                return;
            }
        });

        let i;
        for(i = 1; i < 7; i++){
            let blockName = BlockName[i-1];
            setBlock(blockName, i, date);
        };
    });
});