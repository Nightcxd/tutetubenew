/**
 * Created by cxd on 2016/11/22.
 */
function login(){
    var pwd=$("#password").val().trim();
    var user=$("#username").val().trim();
    if(user==""||pwd==""){
        alert("用户名或密码不能为空");
    }
    else {
        $.ajax({
            type: "POST",
            url: "/login?nickname="+user+"&password="+pwd+"",
            dataType: "html",
            success: function (msg) {
                if(msg.trim()=="true"){
                    alert("登录成功！");
                    $.ajax({
                        type: "POST",
                        url: "/addcookies?nickname=" + user + "&password=" + pwd + "",
                        dataType: "html",
                        success: function (msg) {
                            window.location.href = "index.html";
                        }
                    });
                }
                else {
                    alert(msg);
                }
            }
        });
    }
}