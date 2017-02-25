/**
 * Created by cxd on 2016/10/18.
 */

//检查密码
function pwdtest() {
    var password = $("#password").val();
    var numasc = 0;
    var charasc = 0;
    if (0 == password.length) {
        $("#resultForPassword").html("密码不能为空");
    } else if (password.length < 6 || password.length > 12) {
        if (password.length > 12) {
            $("#resultForPassword").html("密码最多12个字符");
        }
        else {
            $("#resultForPassword").html("密码至少6个字符");
        }
    } else {
        for (var i = 0; i < password.length; i++) {
            var asciiNumber = password.substr(i, 1).charCodeAt();
            if (asciiNumber >= 48 && asciiNumber <= 57) {
                numasc += 1;
            }
            if ((asciiNumber >= 65 && asciiNumber <= 90) || (asciiNumber >= 97 && asciiNumber <= 122)) {
                charasc += 1;
            }
        }
        if (0 == numasc) {
            $("#resultForPassword").html("密码必须含有数字");
        } else if (0 == charasc) {
            $("#resultForPassword").html("密码必须含有字母");
        }
        else {
            $("#resultForPassword").html("");
        }
    }
}
function yanzheng() {
    var password1 = $("#password1").val();
    var password = $("#password").val();
    if (password.trim() == password1.trim()) {
        $("#resultForPassword1").html("");
    }
    else {
        $("#resultForPassword1").html("两次输入密码不一致！");
    }
}

//检查昵称
function checknick(){
    var nickname=$("#nickname").val();
    if (/[\u4E00-\u9FA5]/i.test(nickname)) {
        $("#resultForNick").html("用户名不能为中文！");
    }
   else if(nickname.trim()==""){
        $("#resultForNick").html("用户名不能为空！");
    }
    else {
        $.ajax({
            type: "POST",
            url: "/checknickname?nickname=" + nickname.trim() + "",
            dataType: "html",
            success: function (msg) {
                if (msg.trim() == "1") {
                    $("#resultForNick").html("");
                }
                else {
                    $("#resultForNick").html("该用户名已注册！");
                }
            }
        });
    }
}

//开始注册
function submitSt(){
    var resultForNick=$("#resultForNick").html();
    var resultForPassword=$("#resultForPassword").html();
    var resultForPassword1=$("#resultForPassword1").html();
    if(resultForNick.trim()=="" && resultForPassword.trim()=="" && resultForPassword1.trim()==""){
        var nickname=$("#nickname").val();
        var password=$("#password").val();
        var password1=$("#password1").val();
        var nicknamecn=$("#nicknamecn").val();
        if(password.trim()==""||password1.trim()==""||nickname.trim()==""||nicknamecn==""){
            alert("请正确填写信息！");
        }
        else {
            $.ajax({
                type: "POST",
                url: "/register?nickname=" + nickname.trim() + "&password=" + password.trim() + "&nicknamecn="+nicknamecn.trim()+"",
                dataType: "html",
                success: function (msg) {
                    if (msg.trim() == "1") {
                        alert("注册成功！");
                        window.location.href="login.html";
                    }
                    else {
                        alert("注册失败！");
                    }
                }
            });
        }
    }
    else{
        alert("请正确填写信息！");
    }
}
