function addTableLine(stockList) {
    for(let i = 0; i < stockList.length; i++){
        let stockNewestInfo = stockList[i];
        $("#user_collect").append(
            '<tr><th></th>' +
            '<th class="text-center">' + stockNewestInfo.stock_code +'</th> ' +
            '<th class="text-center">' + stockNewestInfo.stock_name +'</th> ' +
            '<th class="text-center">' + stockNewestInfo.stock_range +'</th> ' +
            '<th class="text-center">' + stockNewestInfo.open +'</th> ' +
            '<th class="text-center">' + stockNewestInfo.trade +'</th> ' +
            '<th class="text-center" style="-webkit-text-fill-color: red">' + stockNewestInfo.high +'</th> ' +
            '<th class="text-center" style="-webkit-text-fill-color: green">' + stockNewestInfo.low +'</th> ' +
            '<th class="text-center">' + stockNewestInfo.volume +'</th> ' +
            '</tr>');
    }
}

$().ready(function () {
    if(checkCookie() === "true"){
        let username = getCookie("username");
        showWelcome(username);
        predictFavouriteStocks(username);
        $.ajax({
            url: '/user/getCollectStock.form',
            data: {
                username: username
            },
            dataType: 'json',
            success: function (stockList) {
                addTableLine(stockList);
                predictFavouriteStocks(username);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);
            }
        })
    } else {
        login();
    }

    $("#logout").click(function (){
        swal({
                title: "Are you sure to Logout?",
                text: "You have to Login again!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, Logout!",
                closeOnConfirm: true
            },
            function(){
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

function predictFavouriteStocks(userName) {
    $.ajax({
        url: "/user/predictFavouriteStocks.form",
        data: {
            username: userName
        },
        dataType: "json",
        success: function (favouriteStocks) {
            for (let i = 0; i < favouriteStocks.length; i++) {
                let stockNewestInfo = favouriteStocks[i];
                $("#code_"+[i]).text(stockNewestInfo.stock_code);
                $("#name_"+[i]).text(stockNewestInfo.stock_name);
                $("#change_"+[i]).text(stockNewestInfo.stock_range.toFixed(2));
                $("#open_"+[i]).text(stockNewestInfo.open.toFixed(2));
                $("#high_"+[i]).text(stockNewestInfo.high.toFixed(2));
                $("#low_"+[i]).text(stockNewestInfo.low.toFixed(2));
                $("#trade_"+[i]).text(stockNewestInfo.trade);
                $("#volume_"+[i]).text(stockNewestInfo.volume);
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        }
    })
}
