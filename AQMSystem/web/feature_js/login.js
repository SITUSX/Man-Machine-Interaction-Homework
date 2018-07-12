$().ready(function () {
        $("#login_btn").click(function () {
            if (checkCookie() == 'true') {
                console.log(checkCookie());
                swal({
                    title: "你已经登录!",
                    text: "请刷新页面!",
                    type: "warning"
                });
                return
            }
            let user_name = $('#username').val();
            let password = $('#password').val();
            if (user_name.indexOf(' ') >= 0) {
                //Error name cannot
                swal({
                    title: "格式错误",
                    text: "姓名不能包含空格！",
                    type: "error"
                });
                return
            }
            if (password.indexOf(' ') >= 0) {
                swal({
                    title: "格式错误",
                    text: "密码不能包含空格！",
                    type: "error"
                });
                return
            }
            login(user_name, password, function () {
                login_suc();
                // window.history.go(-1);
                swal({
                    title: "Yeah!",
                    text: "登录成功! 欢迎， " + getCookie('username'),
                    type: "success"
                },
                function() {
                    parent.document.getElementsByClassName('mfp-close')[0].click();
                    parent.window.location.reload(true);
                })
            })
        })
    }
);

function login_suc() {
    setCookie('status', true, 5);
    setCookie('username', $('#username').val(), 365);
    setCookie('password', $('#password').val(), 365);
    console.log(getCookie('username'));
    console.log(getCookie('password'));
}


