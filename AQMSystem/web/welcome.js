if (checkCookie()) {
    window.location.href = 'home.html'
    // console.log(checkCookie());
} else {
    console.log(getCookie('status'));
    console.log(getCookie('username'));
    console.log(getCookie('password'));
}