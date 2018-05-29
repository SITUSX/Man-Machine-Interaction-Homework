$().ready(function () {
        $("#login_btn").click(function () {
            if (checkCookie() == 'true') {
                console.log(checkCookie());
                swal({
                    title: "You have Login!",
                    text: "Please press ctrl+R/command+R to refresh the page!",
                    type: "error"
                });
                return
            }
            let user_name = $('#username').val();
            let password = $('#password').val();
            if (user_name.indexOf(' ') >= 0) {
                //Error name cannot
                swal({
                    title: "Oops...",
                    text: "Name cannot contain space",
                    type: "error"
                });
                return
            }
            if (password.indexOf(' ') >= 0) {
                swal({
                    title: "Oops...",
                    text: "Password cannot contain space",
                    type: "error"
                });
                return
            }
            login(user_name, password, function () {
                login_suc();
                // window.history.go(-1);
                swal({
                    title: "Yeah!",
                    text: "Login Success! Welcome, " + getCookie('username'),
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


