const BlockName = [
    "BLOCK_MAIN",
    "BLOCK_SMALL",
    "BLOCK_GEM",
    "BLOCK_INDUSTRY",
    "BLOCK_CONCEPT",
    "BLOCK_LOCATION"];
const BlockType = ["主板", "中小板", "创业板", "行业板块", "概念板块", "地域板块"];

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
                title: "Oops...",
                text: "当前数据未加载完!",
                type: "error"
            });
            return;
        }

        let date = $("#date").val();
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
                    title: "Oops...",
                    text: "Network Error!",
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