/**
 * Created by cxd on 2017/1/27.
 */
function checkNewPwd(v){
    var password= $("#"+v+"").val()
    var numasc = 0;
    var charasc = 0;
    if (0 == password.length) {
        $("#"+v+"").siblings(".resultforpwd").html("密码不能为空");
    }
    else if (password.length < 6 || password.length > 12) {
        if (password.length> 12) {
            $("#"+v+"").siblings(".resultforpwd").html("密码最多12个字符");
            $("#"+v+"").val("");
        }
        else {
            $("#"+v+"").siblings(".resultforpwd").html("密码至少6个字符");
            $("#"+v+"").val("");
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
            $("#"+v+"").siblings(".resultforpwd").html("密码必须含有数字");
            $("#"+v+"").val("");
        } else if (0 == charasc) {
            $("#"+v+"").siblings(".resultforpwd").html("密码必须含有字母");
            $("#"+v+"").val("");
        }
        else {
            $("#"+v+"").siblings(".resultforpwd").html("");
        }
    }
}