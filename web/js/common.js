var talkData = {
    webPathPre: "http://" + window.location.host,
    "interface": {
        touristGetMessageDetailV2: "/V2/Treehole/Message/touristGetMessageDetailV4.action",
        touristSupportV2: "/V2/Treehole/Evaluate/touristSupportV2.action",
        touristRemoveSupportV2: "/V2/Treehole/Evaluate/touristRemoveSupportV2.action",
        touristCommentV2: "/V2/Treehole/Comment/touristCommentV2.action",
        touristReplyV2: "/V2/Treehole/Comment/touristReplyV2.action",
        touristMobileShowCommentsByEncryId: "/V2/Treehole/Comment/touristMobileShowCommentsByEncryId.action",
        touristGetTopics: "/V2/Treehole/Topic/touristGetTopics.action",
        touristGetMessageByTopicId: "/V2/Treehole/Message/touristGetMessageV3ByTopicId.action",
        touristGetTopicById: "/V2/Treehole/Topic/touristGetTopicById.action",
        preShareWx: "/Extra/Message/preShareWx.action",
        touristLike: "/V2/Treehole/Like/touristLike.action",
        touristRate: "/V2/Treehole/Score/touristRate.action",
        touristLikeNum: "/V2/Treehole/Like/touristLikeNum.action",
        getRecommendMessage: "/V4/Treehole/Topic/getRecommendMessage.action"
    },
    messageId: null,
    hasMore: !1,
    commentOldestTime: null,
    canGetNextPageNow: !0,
    toastTimeout: null
};
!function (a) {
    a.toast = function (b, c, d) {
        function e() {
            var a = window.navigator.appVersion;
            return a.toLowerCase().indexOf("android 2.2") > -1 || a.toLowerCase().indexOf("android 2.3") > -1
        }

        var f = a("#toast");
        if ("undefined" == typeof f || 0 == f.length) {
            var g = '<div id="toast" class="display-none"><p>' + b + "</p></div>";
            document.body.insertAdjacentHTML("beforeEnd", g), f = document.getElementById("toast")
        }
        f.getElementsByTagName("p")[0].innerHTML = b, e() && (a(f).addClass("low-android"), f.style.top = window.scrollY + window.innerHeight / 2 + "px"), a.show(f), a.addClass(f, "toast-animation"), talkData.toastTimeout && clearTimeout(talkData.toastTimeout), talkData.toastTimeout = setTimeout(function () {
            a.removeClass(f, "toast-animation"), a.hide(f), setTimeout(function () {
                "function" == typeof c && c()
            }, 500)
        }, d || 1500)
    }, a.showTipDialog = function (b) {
        var c = a("#tip-dialog");
        if ("undefined" == typeof c || 0 == c.length) {
            var d = '<div id="tip-dialog" class="display-none"><div class="mask"></div><div class="main"><p class="title">提示</p><div class="msg">' + b + '</div><div class="confirm-btn-box clearfix "><a class="cancel-btn" href="javascript:void(0);">确定</a></div></div></div>';
            document.body.insertAdjacentHTML("beforeEnd", d), c = a("#tip-dialog"), a.addEvent(a("#tip-dialog .cancel-btn"), "click", function () {
                a.hide(a("#tip-dialog"))
            })
        }
        b && (a(".msg", c).innerHTML = b), a.show(c);
        var e = a(".main", c), f = e.clientHeight;
        e.style.marginTop = "-" + f / 2 + "px"
    }, a.showDownloadDialog = function (b) {
        var c = a("#download-dialog");
        if ("undefined" == typeof c || 0 == c.length) {
            var d = '<div id="download-dialog" class="display-none"><div class="mask"></div><div class="main"><p class="title">提示</p><div class="msg">' + b + '</div><div class="confirm-btn-box clearfix "><a class="cancel-btn float-left-box" href="javascript:void(0);">取消</a><a class="confirm-btn float-left-box download-link" href="javascript:void(0);">免费下载</a></div></div></div>';
            document.body.insertAdjacentHTML("beforeEnd", d), c = a("#download-dialog"), a.addEvent(a("#download-dialog .cancel-btn"), "click", function () {
                a.hideDownloadDialog()
            }), a("#download-dialog .download-link").href = "http://www.super.cn/jump.php"
        }
        b && (a(".msg", c).innerHTML = b), a.show(c);
        var e = a(".main", c), f = e.clientHeight;
        e.style.marginTop = "-" + f / 2 + "px"
    }, a.hideDownloadDialog = function () {
        var b = a("#download-dialog");
        a.hide(b)
    }, a.getIssueTimeDesc = function (b) {
        var c = (new Date).getTime(), d = (c - b) / 1e3, e = "";
        if (Math.floor(d / 86400) > 0) {
            var f = Math.floor(d / 86400);
            if (365 > f) {
                var g = new Date(b);
                e = 1 >= f ? "昨天  " + a.formatDate(g, "HH:mm") : a.formatDate(g, "MM-dd HH:mm")
            } else {
                var g = new Date(b);
                e = a.formatDate(g, "yyyy-MM-dd HH:mm")
            }
        } else if (Math.floor(d / 3600) > 0) {
            var h = Math.floor(d / 3600);
            if (6 >= h)e = Math.floor(d / 3600) + "小时前"; else {
                var g = new Date(b), i = (new Date).getHours(), j = a.formatDate(g, "HH");
                h >= i ? e = "昨天  " + a.formatDate(g, "HH:mm") : j >= 0 && 6 >= j ? e = "凌晨  " + a.formatDate(g, "HH:mm") : j > 6 && 11 >= j ? e = "上午  " + a.formatDate(g, "HH:mm") : j > 11 && 13 >= j ? e = "中午  " + a.formatDate(g, "HH:mm") : j > 13 && 18 >= j ? e = "下午  " + a.formatDate(g, "HH:mm") : j > 18 && 24 > j && (e = "晚上  " + a.formatDate(g, "HH:mm"))
            }
        } else e = Math.floor(d / 60) > 0 ? Math.floor(d / 60) + "分钟前" : "刚刚";
        return e
    }, a.getReadCountDesc = function (a) {
        var b;
        return b = a > 1e8 ? Math.floor(a / 1e8) + "亿" : a > 1e4 ? Math.floor(a / 1e4) + "万" : a
    }
}(superJs), $.ready(function () {
    function a(a) {
        var b, c, d = $(".download-link");
        if (d)if ("undefined" == typeof d.length)d.href = a; else if (d.length > 0)for (b = 0, c = d.length; c > b; b++)d[b].href = a
    }

    a("http://www.super.cn/jump.php"), ("undefined" == typeof $("#banner-nav .intro").length && $("#banner-nav .intro") || $("#banner-nav .intro").length && $("#banner-nav .intro").length > 0) && $.addEvent($("#banner-nav .intro"), "click", function () {
        $.show($("#dialog-box"))
    }), $.addEvent($("body"), "click", function (a) {
        var a = a || window.event, b = a.target || a.srcElement;
        "A" != b.nodeName && "IMG" != b.nodeName || "-1" == b.className.indexOf("download-link") || setTimeout(function () {
            $.showTipDialog("<p>请用外部浏览器访问页面才能下载哦~</p>"), $.hide($("#download-dialog"))
        }, 2e3)
    })
});