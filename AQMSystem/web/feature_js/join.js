$().ready(function () {
        $("#join_btn").click(function () {
                var user_name = $('#username').val();
                var password = $('#password').val();
                var password_cfm = $('#password_confirm').val();
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
                if (password != password_cfm) {
                    swal({
                        title: "Oops...",
                        text: "Password is different!",
                        type: "error"
                    });
                    return
                }
                if (user_name == getCookie('username')){
                    swal({
                        title: "Oops...",
                        text: "This user is already Exists!",
                        type: "error"
                    });
                    return
                }

                $.ajax({
                    type: 'POST',
                    url: '/user/signup.form',
                    data: {
                        username: user_name,
                        password: password
                    },
                    success: function (result) {
                        if(result=="success"){
                            login_suc();
                            swal({
                                title: "Yeah!",
                                text: "Signup Success! Welcome, " + getCookie('username'),
                                type: "success"
                            });
                            window.history.go(-1);
                            return;
                        }else if(result=="user_exists"){
                            swal({
                                title: "Oops...",
                                text: "This user is already Exists!",
                                type: "error"
                            });
                            return;
                        }
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(XMLHttpRequest.status);
                        alert(XMLHttpRequest.readyState);
                        alert(textStatus);
                    },
                });
            }
        )
    }
);

function login_suc() {
    setCookie('status', true, 5);
    setCookie('username', $('#username').val(), 365);
    setCookie('password', $('#password').val(), 365);
}

