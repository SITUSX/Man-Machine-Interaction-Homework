function setCookie(name, value, expires) {
    document.cookie = name + "=" + escape(value)+
        ((expires) ? "; expires=" + expires : "");
}

function getCookie(name) {
    var cookieString = decodeURI(document.cookie);
    // console.log(name);
    var cookieArray = cookieString.split("; ");
    for(let i = 0; i < cookieArray.length; i++){
        var cookieNum = cookieArray[i].split("=");
        var cookieName = cookieNum[0];
        var cookieValue = cookieNum[1];
        // console.log(cookieName);
        if(cookieName == ''+name){
            // console.log('suc')
            return cookieValue;
        }
    }
    return null;
}

function checkCookie() {
    return getCookie('status');
}

function hideCookie() {
    setCookie('status', false, 5);
}


function login(user_name, password, callback) {
    $.ajax({
        type: 'POST',
        url: '/user/login.form',
        async: false,
        data: {
            username: user_name,
            password: password
        },
        success: function (result) {
            if(result=="success"){
                if (callback) {
                    callback();
                }
                return;
            }
            else if (result=="user_not_exists") {
                swal({
                    title: "Oops...",
                    text: "用户不存在!",
                    type: "error"
                });
                return;
            } else if(result=="password_error"){
                swal({
                    title: "Oops...",
                    text: "密码错误!",
                    type: "error"
                });
                return;
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.readyState);
            console.log(textStatus);
        },
    });
}

